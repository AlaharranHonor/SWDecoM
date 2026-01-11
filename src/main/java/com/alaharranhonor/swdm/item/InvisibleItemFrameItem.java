package com.alaharranhonor.swdm.item;

import com.alaharranhonor.swdm.entity.InvisibleItemFrame;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class InvisibleItemFrameItem extends Item {

    public InvisibleItemFrameItem(Item.Properties props) {
        super(props);
    }

    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos blockpos = ctx.getClickedPos();
        Direction direction = ctx.getClickedFace();
        BlockPos placePos = blockpos.relative(direction);
        Player player = ctx.getPlayer();
        ItemStack itemstack = ctx.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemstack, placePos)) {
            return InteractionResult.FAIL;
        } else {
            Level level = ctx.getLevel();
            InvisibleItemFrame frameEntity = new InvisibleItemFrame(level, placePos, direction);
            CustomData customdata = itemstack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!customdata.isEmpty()) {
                EntityType.updateCustomEntityTag(level, player, frameEntity, customdata);
            }

            if (frameEntity.survives()) {
                if (!level.isClientSide) {
                    frameEntity.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                    level.addFreshEntity(frameEntity);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean mayPlace(Player player, Direction dir, ItemStack stack, BlockPos pos) {
        return !player.level().isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, dir, stack);
    }
}
