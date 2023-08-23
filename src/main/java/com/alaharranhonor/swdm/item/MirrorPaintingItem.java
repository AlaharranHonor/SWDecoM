package com.alaharranhonor.swdm.item;

import com.alaharranhonor.swdm.entity.MirrorPainting;
import com.alaharranhonor.swdm.entity.MirrorVariant;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.ForgeRegistries;

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
        List<MirrorVariant> list = Lists.newArrayList();
        int largestArea = 0;
        for(PaintingVariant motive : ForgeRegistries.PAINTING_VARIANTS.getValues()) {
            if (!(motive instanceof MirrorVariant)) continue;

            painting.setVariant(ForgeRegistries.PAINTING_VARIANTS.getHolder(motive).get());
            //painting.setDirection(direction);
            if (painting.survives()) {
                list.add((MirrorVariant) motive);
                int area = motive.getWidth() * motive.getHeight();
                if (area > largestArea) {
                    largestArea = area;
                }
            }
        }

        if (!list.isEmpty()) {
            Iterator<MirrorVariant> ite = list.iterator();

            while(ite.hasNext()) {
                MirrorVariant next = ite.next();
                if (next.getWidth() * next.getHeight() < largestArea) {
                    ite.remove();
                }
            }
            painting.setVariant(ForgeRegistries.PAINTING_VARIANTS.getHolder(list.get(painting.level().random.nextInt(list.size()))).get());
        }
    }

    protected boolean mayPlace(Player pPlayer, Direction pDirection, ItemStack pHangingEntityStack, BlockPos pPos) {
        return !pDirection.getAxis().isVertical() && pPlayer.mayUseItemAt(pPos, pDirection, pHangingEntityStack);
    }
}
