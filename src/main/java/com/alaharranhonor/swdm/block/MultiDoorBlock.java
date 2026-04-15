package com.alaharranhonor.swdm.block;

import com.alaharranhonor.swdm.block.entity.MultiDoorBlockEntity;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.util.ShapeHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class MultiDoorBlock extends BaseEntityBlock {

    public static final BooleanProperty MAIN = BooleanProperty.create("main");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    public static final Map<Direction, VoxelShape> SHAPES = ShapeHelper.createDirectionalMap(Block.box(0, 0, 14, 16, 16, 16));

    public MultiDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        float playerRotation = (((context.getRotation() + 180) % 360) + 180) % 360;
        if (context.getHorizontalDirection() == Direction.SOUTH && playerRotation > 180) {
            playerRotation -= 360;
        }
        float rotationOff = playerRotation - context.getHorizontalDirection().toYRot();
        return this.defaultBlockState()
            .setValue(MAIN, true)
            .setValue(FACING, context.getHorizontalDirection().getOpposite())
            .setValue(HINGE, rotationOff >= 0 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(this.getEffectiveFacingDirection(state));
    }

    public Direction getEffectiveFacingDirection(BlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(level, pos, state);
        level.getBlockEntity(getMainPos(level, pos, state), BlockSetup.MULTI_DOOR.get()).ifPresent(door -> door.saveToItem(itemstack, level.registryAccess()));
        return itemstack;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide() || !state.getValue(MAIN)) return;

        MultiDoorData doorData = level.getBlockEntity(pos, BlockSetup.MULTI_DOOR.get()).map(MultiDoorBlockEntity::getDoorData).orElseThrow();

        int width = doorData.width();
        int height = doorData.height();
        Direction hingeOffset = getHingeOffset(state);

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (w == 0 && h == 0) continue; // Skip the main block
                BlockPos targetPos = pos.relative(hingeOffset, w).above(h);
                BlockState targetState = state.setValue(MAIN, false);
                level.setBlock(targetPos, targetState, Block.UPDATE_ALL_IMMEDIATE);
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockPos mainPos = getMainPos(level, pos, state);
        MultiDoorData doorData = level.getBlockEntity(mainPos, BlockSetup.MULTI_DOOR.get()).map(MultiDoorBlockEntity::getDoorData).orElse(null);
        boolean shouldDestroy = super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
        if (doorData != null && shouldDestroy) {
            int width = doorData.width();
            int height = doorData.height();
            Direction hingeOffset = getHingeOffset(state);

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    BlockPos targetPos = mainPos.relative(hingeOffset, w).above(h);
                    BlockState targetState = level.getBlockState(targetPos);
                    if (!targetPos.equals(pos) && targetState.is(state.getBlock())) {
                        if (targetState.getValue(MAIN)) {
                            level.destroyBlock(targetPos, true, player);
                        } else {
                            level.removeBlock(targetPos, false);
                        }
                    }
                }
            }
        }
        return shouldDestroy;
    }

    public BlockPos getMainPos(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.getValue(MAIN)) {
            return pos;
        }

        Direction hingeDirection = this.getHingeDirection(state);
        BlockPos.MutableBlockPos currentPos = pos.mutable();

        // Go down until the block is not us
        while (level.getBlockState(currentPos.below()).is(state.getBlock())) {
            currentPos.move(Direction.DOWN);
        }

        // Go in the hinge direction until MAIN is true, or a non-door block is found
        while (level.getBlockState(currentPos).is(state.getBlock())) {
            if (level.getBlockState(currentPos).getValue(MAIN)) {
                return currentPos;
            }
            currentPos.move(hingeDirection);
        }

        return pos; // In case no MAIN block is found, return the original position
    }

    public Direction getHingeDirection(BlockState state) {
        Direction facing = state.getValue(FACING);
        return state.getValue(HINGE) == DoorHingeSide.RIGHT ? facing.getClockWise() : facing.getCounterClockWise();
    }

    public Direction getHingeOffset(BlockState state) {
        return this.getHingeDirection(state).getOpposite();
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(MAIN) ? new MultiDoorBlockEntity(pos, state) : null;
    }

    public boolean canPlace(Level level, BlockPos mainPos, BlockState state, MultiDoorData doorData, List<BlockPos> ignore) {
        int width = doorData.width();
        int height = doorData.height();
        Direction hingeOffset = this.getHingeOffset(state);
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                BlockPos targetPos = mainPos.relative(hingeOffset, w).above(h);
                if (!ignore.contains(targetPos) && !level.getBlockState(targetPos).canBeReplaced()) {
                    return false;
                }
            }
        }

        return true;
    }

    public enum DoorType {
        SLIDING(Component.translatable("item.swdm.multi_door.slide"), () -> new ItemStack(BlockSetup.SLIDING_DOOR)),
        SWINGING(Component.translatable("item.swdm.multi_door.swing"), () -> new ItemStack(BlockSetup.SWINGING_DOOR)),
        STATIC(Component.translatable("item.swdm.multi_door.static"), () -> new ItemStack(BlockSetup.STATIC_DOOR));

        public static final StreamCodec<ByteBuf, DoorType> STREAM_CODEC = ByteBufCodecs.idMapper(i -> DoorType.values()[i], DoorType::ordinal);

        private final Component key;
        private final Supplier<ItemStack> item;
        DoorType(Component key, Supplier<ItemStack> item) {
            this.key = key;
            this.item = item;
        }
        public Component getKey() {
            return this.key;
        }

        public ItemStack createItem() {
            return this.item.get();
        }
    }

    // Used for Swinging and Sliding doors
    public enum DoorOpenState implements StringRepresentable {
        CLOSED("closed"),
        HALF_OPEN("half_open"),
        OPEN("open");

        private final String name;

        DoorOpenState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
