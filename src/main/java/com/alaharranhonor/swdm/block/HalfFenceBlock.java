package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.registry.SWDMTags;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class HalfFenceBlock extends FenceBlock {

    public static final EnumProperty<SWDMBlockstateProperties.WallType> WALL_TYPE = SWDMBlockstateProperties.WALL;

    //private final Map<BlockState, VoxelShape> shapeByIndex;
    //private final Map<BlockState, VoxelShape> collisionShapeByIndex;

    public HalfFenceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WALL_TYPE, SWDMBlockstateProperties.WallType.FULL));
        //this.shapeByIndex = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        //this.collisionShapeByIndex = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WALL_TYPE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getItemInHand(pHand).is(SWDMTags.STATE_CYCLER)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        pLevel.setBlock(pPos, pState.cycle(WALL_TYPE), 3);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected VoxelShape[] makeShapes(float pNodeWidth, float pExtensionWidth, float pNodeHeight, float pExtensionBottom, float pExtensionHeight) {
        return super.makeShapes(pNodeWidth, pExtensionWidth, pNodeHeight, pExtensionBottom, pExtensionHeight);
    }

    //@Override
    protected Map<BlockState, VoxelShape> makeShapes(float x0, float z0, float postHeight, float bottom, float lowWallHeight, float tallWallHeight) {
        float f = 8.0F - x0;
        float f1 = 8.0F + x0;
        float f2 = 8.0F - z0;
        float f3 = 8.0F + z0;
        VoxelShape postShape =     Block.box(f,    0.0D,     f,    f1,    postHeight,     f1);
        VoxelShape northLowWall =  Block.box(f2,   bottom,   0.0D, f3,    lowWallHeight,  f3);
        VoxelShape southLowWall =  Block.box(f2,   bottom,   f2,   f3,    lowWallHeight,  16.0D);
        VoxelShape westLowWall =   Block.box(0.0D, bottom,   f2,   f3,    lowWallHeight,  f3);
        VoxelShape eastLowWall =   Block.box(f2,   bottom,   f2,   16.0D, lowWallHeight,  f3);
        VoxelShape northTallWall = Block.box(f2,   bottom,   0.0D, f3,    tallWallHeight, f3);
        VoxelShape southTallWall = Block.box(f2,   bottom,   f2,   f3,    tallWallHeight, 16.0D);
        VoxelShape westTallWall =  Block.box(0.0D, bottom,   f2,   f3,    tallWallHeight, f3);
        VoxelShape eastTallWall =  Block.box(f2,   bottom,   f2,   16.0D, tallWallHeight, f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for(Boolean isPost : WallBlock.UP.getPossibleValues()) {
            for(WallSide east : WallBlock.EAST_WALL.getPossibleValues()) {
                for(WallSide north : WallBlock.NORTH_WALL.getPossibleValues()) {
                    for(WallSide west : WallBlock.WEST_WALL.getPossibleValues()) {
                        for(WallSide south : WallBlock.SOUTH_WALL.getPossibleValues()) {
                            for (SWDMBlockstateProperties.WallType wallType : WALL_TYPE.getPossibleValues()) {
                                VoxelShape wallShape = Shapes.empty();
                                //wallShape = WallBlock.applyWallShape(wallShape, east, eastLowWall, eastTallWall);
                                //wallShape = WallBlock.applyWallShape(wallShape, west, westLowWall, westTallWall);
                                //wallShape = WallBlock.applyWallShape(wallShape, north, northLowWall, northTallWall);
                                //wallShape = WallBlock.applyWallShape(wallShape, south, southLowWall, southTallWall);
                                if (isPost) {
                                    wallShape = Shapes.or(wallShape, postShape);
                                }

                                BlockState blockstate = this.defaultBlockState().setValue(WallBlock.UP, isPost).setValue(WallBlock.EAST_WALL, east).setValue(WallBlock.WEST_WALL, west).setValue(WallBlock.NORTH_WALL, north).setValue(WallBlock.SOUTH_WALL, south).setValue(WALL_TYPE, wallType);
                                builder.put(blockstate.setValue(WallBlock.WATERLOGGED, Boolean.valueOf(false)), wallShape);
                                builder.put(blockstate.setValue(WallBlock.WATERLOGGED, Boolean.valueOf(true)), wallShape);
                            }
                        }
                    }
                }
            }
        }

        return builder.build();
    }
}
