package com.alaharranhonor.swdm.entity;

import com.alaharranhonor.swdm.registry.ItemSetup;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class MirrorPainting extends Painting {

    public MirrorPainting(EntityType<Painting> type, Level level) {
        super(type, level);
    }

    public MirrorPainting(Level level, BlockPos pos, Direction direction) {
        super(EntityType.PAINTING, level);
        this.pos = pos;

        List<MirrorMotive> list = Lists.newArrayList();
        int largestArea = 0;
        for(Motive motive : ForgeRegistries.PAINTING_TYPES.getValues()) {
            if (!(motive instanceof MirrorMotive)) continue;

            this.motive = motive;
            this.setDirection(direction);
            if (this.survives()) {
                list.add((MirrorMotive) motive);
                int area = motive.getWidth() * motive.getHeight();
                if (area > largestArea) {
                    largestArea = area;
                }
            }
        }

        if (!list.isEmpty()) {
            Iterator<MirrorMotive> ite = list.iterator();

            while(ite.hasNext()) {
                MirrorMotive next = ite.next();
                if (next.getWidth() * next.getHeight() < largestArea) {
                    ite.remove();
                }
            }

            this.motive = list.get(this.random.nextInt(list.size()));
        }

        this.setDirection(direction);
    }

    @Override
    public void dropItem(@Nullable Entity breaker) {
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
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
}
