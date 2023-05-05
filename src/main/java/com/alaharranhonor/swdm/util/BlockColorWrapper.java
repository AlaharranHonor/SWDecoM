package com.alaharranhonor.swdm.util;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;

@FunctionalInterface
public interface BlockColorWrapper {

    BlockColor get();

    static BlockColorWrapper birch() {
        return () -> (state, reader, pos, color) -> FoliageColor.getBirchColor();
    }

    static BlockColorWrapper spruce() {
        return () -> (state, reader, pos, color) -> FoliageColor.getEvergreenColor();
    }

    static BlockColorWrapper biome() {
        return () -> (state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
    }
}
