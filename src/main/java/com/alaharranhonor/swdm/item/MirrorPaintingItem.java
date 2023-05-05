package com.alaharranhonor.swdm.item;

import com.alaharranhonor.swdm.entity.MirrorMotive;
import com.alaharranhonor.swdm.entity.MirrorPainting;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.google.common.collect.Lists;
import com.sun.jdi.Mirror;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class MirrorPaintingItem extends Item {

    public MirrorPaintingItem(Item.Properties props) {
        super(props);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Direction direction = pContext.getClickedFace();
        BlockPos placedPos = blockpos.relative(direction);
        Player player = pContext.getPlayer();
        ItemStack itemstack = pContext.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemstack, placedPos)) {
            return InteractionResult.FAIL;
        } else {
            Level level = pContext.getLevel();
            MirrorPainting painting = new MirrorPainting(level, placedPos, direction);

            // TODO add nbt tag to say its a mirror painting

            CompoundTag compoundtag = itemstack.getTag();
            if (compoundtag != null) {
                EntityType.updateCustomEntityTag(level, player, painting, compoundtag);
            }

            if (painting.survives()) {
                if (!level.isClientSide) {
                    painting.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                    level.addFreshEntity(painting);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    private static void setRandomMotive(Painting painting, Direction direction) {
        List<MirrorMotive> list = Lists.newArrayList();
        int largestArea = 0;
        for(Motive motive : ForgeRegistries.PAINTING_TYPES.getValues()) {
            if (!(motive instanceof MirrorMotive)) continue;

            painting.motive = motive;
            //painting.setDirection(direction);
            if (painting.survives()) {
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

            painting.motive = list.get(painting.level.random.nextInt(list.size()));
        }
    }

    protected boolean mayPlace(Player pPlayer, Direction pDirection, ItemStack pHangingEntityStack, BlockPos pPos) {
        return !pDirection.getAxis().isVertical() && pPlayer.mayUseItemAt(pPos, pDirection, pHangingEntityStack);
    }
}
