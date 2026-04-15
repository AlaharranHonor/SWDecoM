package com.alaharranhonor.swdm.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class StaticDoorBlock extends MultiDoorBlock {

    public static final MapCodec<StaticDoorBlock> CODEC = simpleCodec(StaticDoorBlock::new);

    public static final BooleanProperty MAIN = MultiDoorBlock.MAIN;
    public static final DirectionProperty FACING = MultiDoorBlock.FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = MultiDoorBlock.HINGE;

    public StaticDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MAIN, FACING, HINGE);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
