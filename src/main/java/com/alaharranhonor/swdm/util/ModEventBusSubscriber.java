package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @Mod.EventBusSubscriber(modid = SWDM.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusHandler {
        @SubscribeEvent
        public static void RegisterBlockColors(ColorHandlerEvent.Block event) {
            BlockColors colors = event.getBlockColors();
            colors.register((state, reader, pos, color) -> {
                        return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
                    }, BlockInit.SWDM_ACACIA_LEAVES_STAIRS.get(), BlockInit.SWDM_JUNGLE_LEAVES_STAIRS.get(), BlockInit.SWDM_DARK_OAK_LEAVES_STAIRS.get(), BlockInit.SWDM_OAK_LEAVES_STAIRS.get(),
                    BlockInit.SWDM_ACACIA_LEAVES_SLAB.get(), BlockInit.SWDM_JUNGLE_LEAVES_SLAB.get(), BlockInit.SWDM_DARK_OAK_LEAVES_SLAB.get(), BlockInit.SWDM_OAK_LEAVES_SLAB.get(),
                    BlockInit.SWDM_ACACIA_LEAVES_WALL.get(), BlockInit.SWDM_JUNGLE_LEAVES_WALL.get(), BlockInit.SWDM_DARK_OAK_LEAVES_WALL.get(), BlockInit.SWDM_OAK_LEAVES_WALL.get());
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getEvergreenColor();
            }, BlockInit.SWDM_SPRUCE_LEAVES_STAIRS.get(), BlockInit.SWDM_SPRUCE_LEAVES_SLAB.get(), BlockInit.SWDM_SPRUCE_LEAVES_WALL.get());
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getBirchColor();
            }, BlockInit.SWDM_BIRCH_LEAVES_STAIRS.get(), BlockInit.SWDM_BIRCH_LEAVES_SLAB.get(), BlockInit.SWDM_BIRCH_LEAVES_WALL.get());
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();
            colors.register((stack, color) -> {
                        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                        return event.getBlockColors().getColor(blockstate, (IBlockDisplayReader) null, (BlockPos) null, color);
                    }, BlockInit.SWDM_ACACIA_LEAVES_STAIRS.get(), BlockInit.SWDM_JUNGLE_LEAVES_STAIRS.get(), BlockInit.SWDM_DARK_OAK_LEAVES_STAIRS.get(), BlockInit.SWDM_OAK_LEAVES_STAIRS.get(),
                    BlockInit.SWDM_ACACIA_LEAVES_SLAB.get(), BlockInit.SWDM_JUNGLE_LEAVES_SLAB.get(), BlockInit.SWDM_DARK_OAK_LEAVES_SLAB.get(), BlockInit.SWDM_OAK_LEAVES_SLAB.get(),
                    BlockInit.SWDM_ACACIA_LEAVES_WALL.get(), BlockInit.SWDM_JUNGLE_LEAVES_WALL.get(), BlockInit.SWDM_DARK_OAK_LEAVES_WALL.get(), BlockInit.SWDM_OAK_LEAVES_WALL.get(),
                    BlockInit.SWDM_SPRUCE_LEAVES_STAIRS.get(), BlockInit.SWDM_SPRUCE_LEAVES_SLAB.get(), BlockInit.SWDM_SPRUCE_LEAVES_WALL.get(),
                    BlockInit.SWDM_BIRCH_LEAVES_STAIRS.get(), BlockInit.SWDM_BIRCH_LEAVES_SLAB.get(), BlockInit.SWDM_BIRCH_LEAVES_WALL.get());
        }
    }
}
