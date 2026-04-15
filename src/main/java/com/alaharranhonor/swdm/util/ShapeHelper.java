package com.alaharranhonor.swdm.util;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShapeHelper {

    public static int clipShapes(Player player, BlockPos pos, VoxelShape[] shapes) {
        double blockReach = player.blockInteractionRange();
        Vec3 lookVector = player.getLookAngle().multiply(blockReach, blockReach, blockReach);

        Map<Integer, VoxelShape> shapesMap = IntStream.range(0, shapes.length)
            .boxed()
            .collect(Collectors.toMap(
                i -> i,
                i -> shapes[i]
            ));

        return shapesMap.entrySet().stream()
            .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
            .map(pair -> Pair.of(pair.left(), pair.right().clip(player.getEyePosition(), player.getEyePosition().add(lookVector), pos)))
            .filter(pair -> pair.right() != null)
            .sorted(Comparator.comparingDouble(pair -> pair.right().getLocation().distanceToSqr(player.getEyePosition())))
            .findFirst()
            .map(Pair::key)
            .orElse(-1);
    }

    public static Map<Direction, VoxelShape> createDirectionalMap(VoxelShape northShape) {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);

        shapes.put(Direction.NORTH, northShape);
        shapes.put(Direction.EAST, rotateShapeClockwise(northShape, 1));
        shapes.put(Direction.SOUTH, rotateShapeClockwise(northShape, 2));
        shapes.put(Direction.WEST, rotateShapeClockwise(northShape, 3));
        shapes.put(Direction.UP, northShape);
        shapes.put(Direction.DOWN, northShape);

        return shapes;
    }

    /**
     * Rotates a shape clockwise by 90° * times
     * @param shape Original shape
     * @param times Number of 90° rotations (1 = 90°, 2 = 180°, 3 = 270°)
     */
    private static VoxelShape rotateShapeClockwise(VoxelShape shape, int times) {
        VoxelShape result = shape;
        for (int i = 0; i < times; i++) {
            result = rotate90Clockwise(result);
        }
        return result;
    }

    private static VoxelShape rotate90Clockwise(VoxelShape shape) {
        Set<VoxelShape> shapes = new HashSet<>();
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // 90° clockwise rotation: (x, z) -> (1-z, x)
            double newMinX = 1.0 - maxZ;
            double newMinZ = minX;
            double newMaxX = 1.0 - minZ;
            double newMaxZ = maxX;

            shapes.add(Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ));
        });

        return Shapes.or(Shapes.empty(), shapes.toArray(new VoxelShape[0]));
    }
}
