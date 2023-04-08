package com.alaharranhonor.swdm.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class SWDMBlockstateProperties {
    public static final EnumProperty<Tileable> TILEABLE = EnumProperty.create("tile", Tileable.class);
    public static final EnumProperty<TwoWay> TWO_WAY = EnumProperty.create("two_way", TwoWay.class);
    public static final EnumProperty<WallType> WALL = EnumProperty.create("wall", WallType.class);

    public enum Tileable implements StringRepresentable {
        SINGLE,
        UP,
        MIDDLE,
        DOWN;

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return this == SINGLE ? "single" : this == DOWN ? "down" : this == MIDDLE ? "middle" : "up";
        }
    }

    public enum TwoWay implements StringRepresentable {
        SINGLE(0),
        LEFT(1),
        MIDDLE(2),
        RIGHT(3);

        private int id;

        TwoWay(int id) {
            this.id = id;
        }

        public String toString() {
            return this.getSerializedName();
        }

        public String getSerializedName() {
            return this == SINGLE ? "single" : this == LEFT ? "left" : this == MIDDLE ? "middle" : "right";
        }

        public int getId() {
            return this.id;
        }
    }

    public enum WallType implements StringRepresentable {
        FULL, LOWER, UPPER;

        @Override
        public String getSerializedName() {
            return this == UPPER ? "upper" : this == LOWER ? "lower" : "full";
        }
    }
}
