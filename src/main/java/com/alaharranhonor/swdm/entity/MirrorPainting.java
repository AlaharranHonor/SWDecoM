package com.alaharranhonor.swdm.entity;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.ItemSetup;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MirrorPainting extends Painting {

    public MirrorPainting(EntityType<Painting> type, Level level) {
        super(type, level);
    }

    public MirrorPainting(Level level, BlockPos pos) {
        super(EntityType.PAINTING, level);
        this.pos = pos;
    }

    @Override
    public void dropItem(@Nullable Entity breaker) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (breaker instanceof Player) {
                Player player = (Player)breaker;
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            this.spawnAtLocation(ItemSetup.MIRROR_PAINTING.get());
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ItemSetup.MIRROR_PAINTING.get());
    }

    public static Optional<MirrorPainting> createMirrorPainting(Level level, BlockPos pos, Direction direction) {
        MirrorPainting painting = new MirrorPainting(level, pos);
        List<Holder<PaintingVariant>> list = new ArrayList<>();
        level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach(list::add);
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            painting.setDirection(direction);
            list.removeIf(variant -> {
                if (!variant.getKey().location().getNamespace().equals(ModRef.ID)) {
                    return true;
                }

                painting.setVariant(variant);
                return !painting.survives();
            });
            if (list.isEmpty()) {
                return Optional.empty();
            } else {
                int maxArea = list.stream().mapToInt(MirrorPainting::variantArea).max().orElse(0);
                list.removeIf(variant -> variantArea(variant) < maxArea);
                Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, painting.random);
                if (optional.isEmpty()) {
                    return Optional.empty();
                } else {
                    painting.setVariant(optional.get());
                    painting.setDirection(direction);
                    return Optional.of(painting);
                }
            }
        }
    }

    private static int variantArea(Holder<PaintingVariant> variant) {
        return variant.value().area();
    }
}
