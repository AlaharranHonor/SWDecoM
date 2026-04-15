package com.alaharranhonor.swdm.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DirectionUtil {

    public static Direction getNearest(Vec3 ois, @NotNull Direction... dirs) {
        return getNearest((float) ois.x, (float) ois.y, (float) ois.z, dirs);
    }

    public static Direction getNearest(float x, float y, float z, @NotNull Direction... dirs) {
        Direction target = dirs[0];
        float f = Float.MIN_VALUE;

        for (Direction test : dirs) {
            float f1 = x * (float) test.getNormal().getX() + y * (float) test.getNormal().getY() + z * (float) test.getNormal().getZ();
            if (f1 > f) {
                f = f1;
                target = test;
            }
        }

        return target;
    }

}
