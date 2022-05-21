package com.alaharranhonor.swdm.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class SWDMBlockstateProperties {
    public static final EnumProperty<Tileable> TILEABLE = EnumProperty.create("tile", Tileable.class);
    public static final EnumProperty<SWDMBlockstateProperties.TwoWay> TWO_WAY = EnumProperty.create("two_way", SWDMBlockstateProperties.TwoWay.class);

    public enum Tileable implements StringRepresentable {
        SINGLE,
        BOTTOM,
        MIDDLE,
        UPPER;

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return this == SINGLE ? "single" : this == BOTTOM ? "bottom" : this == MIDDLE ? "middle" : "upper";
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
}
