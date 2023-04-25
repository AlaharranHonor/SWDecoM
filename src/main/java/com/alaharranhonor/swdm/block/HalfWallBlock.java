package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.registry.TagSetup;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

public class HalfWallBlock extends WallBlock {

    private final Supplier<HalfWallBlock> clone;
    private final Supplier<HalfWallBlock> next;

    public HalfWallBlock(Properties properties, Supplier<HalfWallBlock> clone, Supplier<HalfWallBlock> next) {
        super(properties);
        this.clone = clone;
        this.next = next;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        pLevel.setBlock(pPos, this.nextState(pState), 3);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public BlockState nextState(BlockState state) {
        BlockState nextState = this.next.get().defaultBlockState();
        nextState = nextState.setValue(UP, state.getValue(UP));
        nextState = nextState.setValue(NORTH_WALL, state.getValue(NORTH_WALL));
        nextState = nextState.setValue(EAST_WALL, state.getValue(EAST_WALL));
        nextState = nextState.setValue(WEST_WALL, state.getValue(WEST_WALL));
        nextState = nextState.setValue(SOUTH_WALL, state.getValue(SOUTH_WALL));
        nextState = nextState.setValue(WATERLOGGED, state.getValue(WATERLOGGED));
        return nextState;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(this.clone.get());
    }

    @Override
    protected Map<BlockState, VoxelShape> makeShapes(float x0, float z0, float postHeight, float bottom, float lowWallHeight, float tallWallHeight) {
        float f = 8.0F - x0;
        float f1 = 8.0F + x0;
        float f2 = 8.0F - z0;
        float f3 = 8.0F + z0;
        VoxelShape postShape = Block.box(f, 0.0D, f, f1, postHeight, f1);
        VoxelShape northLowWall = Block.box(f2, bottom, 0.0D, f3, lowWallHeight, f3);
        VoxelShape southLowWall = Block.box(f2, bottom, f2, f3, lowWallHeight, 16.0D);
        VoxelShape westLowWall = Block.box(0.0D, bottom, f2, f3, lowWallHeight, f3);
        VoxelShape eastLowWall = Block.box(f2, bottom, f2, 16.0D, lowWallHeight, f3);
        VoxelShape northTallWall = Block.box(f2, bottom, 0.0D, f3, tallWallHeight, f3);
        VoxelShape southTallWall = Block.box(f2, bottom, f2, f3, tallWallHeight, 16.0D);
        VoxelShape westTallWall = Block.box(0.0D, bottom, f2, f3, tallWallHeight, f3);
        VoxelShape eastTallWall = Block.box(f2, bottom, f2, 16.0D, tallWallHeight, f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean isPost : WallBlock.UP.getPossibleValues()) {
            for (WallSide east : WallBlock.EAST_WALL.getPossibleValues()) {
                for (WallSide north : WallBlock.NORTH_WALL.getPossibleValues()) {
                    for (WallSide west : WallBlock.WEST_WALL.getPossibleValues()) {
                        for (WallSide south : WallBlock.SOUTH_WALL.getPossibleValues()) {
                            VoxelShape wallShape = Shapes.empty();
                            wallShape = WallBlock.applyWallShape(wallShape, east, eastLowWall, eastTallWall);
                            wallShape = WallBlock.applyWallShape(wallShape, west, westLowWall, westTallWall);
                            wallShape = WallBlock.applyWallShape(wallShape, north, northLowWall, northTallWall);
                            wallShape = WallBlock.applyWallShape(wallShape, south, southLowWall, southTallWall);
                            if (isPost) {
                                wallShape = Shapes.or(wallShape, postShape);
                            }

                            BlockState blockstate = this.defaultBlockState().setValue(WallBlock.UP, isPost).setValue(WallBlock.EAST_WALL, east).setValue(WallBlock.WEST_WALL, west).setValue(WallBlock.NORTH_WALL, north).setValue(WallBlock.SOUTH_WALL, south);
                            builder.put(blockstate.setValue(WallBlock.WATERLOGGED, Boolean.valueOf(false)), wallShape);
                            builder.put(blockstate.setValue(WallBlock.WATERLOGGED, Boolean.valueOf(true)), wallShape);
                        }
                    }
                }
            }
        }

        return builder.build();
    }
}
