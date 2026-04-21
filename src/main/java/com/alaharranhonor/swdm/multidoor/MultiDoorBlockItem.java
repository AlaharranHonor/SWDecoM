package com.alaharranhonor.swdm.multidoor;

import com.alaharranhonor.swdm.block.MultiDoorBlock;
import com.alaharranhonor.swdm.registry.DataComponentSetup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MultiDoorBlockItem extends BlockItem {

    public MultiDoorBlockItem(Block block, Properties properties) {
        super(block, properties.component(DataComponentSetup.MULTI_DOOR, MultiDoorData.EMPTY));
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        MultiDoorData doorData = context.getItemInHand().get(DataComponentSetup.MULTI_DOOR);
        if (doorData == null) return false;

        MultiDoorBlock block = (MultiDoorBlock) state.getBlock();
        return block.canPlace(context.getLevel(), context.getClickedPos(), state, doorData, List.of()) && super.canPlace(context, state);
    }

    // Make this public for rendering of the highlight.
    @Override
    public @Nullable BlockState getPlacementState(BlockPlaceContext context) {
        return super.getPlacementState(context);
    }

    @Override
    protected boolean mustSurvive() {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if (stack.has(DataComponents.HIDE_TOOLTIP) || stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP))
            return Optional.empty();

        MultiDoorData data = stack.get(DataComponentSetup.MULTI_DOOR);
        if (data == MultiDoorData.EMPTY) return Optional.empty();

        return Optional.ofNullable(data);

    }
}
