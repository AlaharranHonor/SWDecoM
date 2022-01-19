package com.alaharranhonor.swdm.blocks;

import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IStringSerializable;

public class SWDMBlockstateProperties extends BlockStateProperties {
    public static final EnumProperty<Tileable> TILEABLE = EnumProperty.create("tile", Tileable.class);
    public enum Tileable implements IStringSerializable {
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
}