package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.workshop.DecoWorkshopMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class DecoWorkshopBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("container.deco_workshop");

    public DecoWorkshopBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        player.openMenu(state.getMenuProvider(level, pos));
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((id, inv, player) -> new DecoWorkshopMenu(id, inv, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }
}
