package com.alaharranhonor.swdm.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;

public class SWDMBlockstateProperties {
    public static final EnumProperty<Tileable> TILEABLE = EnumProperty.create("tile", Tileable.class);
    public static final EnumProperty<TwoWay> TWO_WAY = EnumProperty.create("two_way", TwoWay.class);
    public static final EnumProperty<WallType> WALL = EnumProperty.create("wall", WallType.class);
    public static final EnumProperty<ShelfType> SHELF = EnumProperty.create("shelf", ShelfType.class);

    public enum Tileable implements StringRepresentable {
        SINGLE,
        UPPER,
        MIDDLE,
        LOWER;

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return this == SINGLE ? "single" : this == LOWER ? "lower" : this == MIDDLE ? "middle" : "upper";
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

    public enum ShelfType implements StringRepresentable {
        UPPER_FRONT(0), UPPER_MIDDLE(0), UPPER_BACK(0),
        MIDDLE_FRONT(1), MIDDLE_MIDDLE(1), MIDDLE_BACK(1),
        LOWER_FRONT(2), LOWER_MIDDLE(2), LOWER_BACK(2);

        public final int shapeIndex;

        ShelfType(int shapeIndex) {
            this.shapeIndex = shapeIndex;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
