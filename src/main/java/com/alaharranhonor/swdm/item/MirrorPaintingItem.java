package com.alaharranhonor.swdm.item;

import com.alaharranhonor.swdm.entity.MirrorPainting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

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
            MirrorPainting entity = new MirrorPainting(level, placedPos, direction);
            CompoundTag compoundtag = itemstack.getTag();
            if (compoundtag != null) {
                EntityType.updateCustomEntityTag(level, player, entity, compoundtag);
            }

            if (entity.survives()) {
                if (!level.isClientSide) {
                    entity.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                    level.addFreshEntity(entity);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean mayPlace(Player pPlayer, Direction pDirection, ItemStack pHangingEntityStack, BlockPos pPos) {
        return !pDirection.getAxis().isVertical() && pPlayer.mayUseItemAt(pPos, pDirection, pHangingEntityStack);
    }
}
