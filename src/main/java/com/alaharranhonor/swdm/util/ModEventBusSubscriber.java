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
                    },
                    BlockInit.ACACIA_LEAVES_STAIRS.get(), BlockInit.JUNGLE_LEAVES_STAIRS.get(), BlockInit.DARK_OAK_LEAVES_STAIRS.get(), BlockInit.OAK_LEAVES_STAIRS.get(),
                    BlockInit.ACACIA_LEAVES_SLAB.get(), BlockInit.JUNGLE_LEAVES_SLAB.get(), BlockInit.DARK_OAK_LEAVES_SLAB.get(), BlockInit.OAK_LEAVES_SLAB.get(),
                    BlockInit.ACACIA_LEAVES_WALL.get(), BlockInit.JUNGLE_LEAVES_WALL.get(), BlockInit.DARK_OAK_LEAVES_WALL.get(), BlockInit.OAK_LEAVES_WALL.get(),
                    BlockInit.ACACIA_LEAVES_TRAPDOOR.get(), BlockInit.JUNGLE_LEAVES_TRAPDOOR.get(), BlockInit.DARK_OAK_LEAVES_TRAPDOOR.get(), BlockInit.OAK_LEAVES_TRAPDOOR.get());
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getEvergreenColor();
            },
                    BlockInit.SPRUCE_LEAVES_STAIRS.get(), BlockInit.SPRUCE_LEAVES_SLAB.get(), BlockInit.SPRUCE_LEAVES_WALL.get(), BlockInit.SPRUCE_LEAVES_TRAPDOOR.get());
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getBirchColor();
            },
                    BlockInit.BIRCH_LEAVES_STAIRS.get(), BlockInit.BIRCH_LEAVES_SLAB.get(), BlockInit.BIRCH_LEAVES_WALL.get(), BlockInit.BIRCH_LEAVES_TRAPDOOR.get());
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();
            colors.register((stack, color) -> {
                        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                        return event.getBlockColors().getColor(blockstate, (IBlockDisplayReader) null, (BlockPos) null, color);
                    },
                    BlockInit.ACACIA_LEAVES_STAIRS.get(), BlockInit.JUNGLE_LEAVES_STAIRS.get(), BlockInit.DARK_OAK_LEAVES_STAIRS.get(), BlockInit.OAK_LEAVES_STAIRS.get(),
                    BlockInit.ACACIA_LEAVES_SLAB.get(), BlockInit.JUNGLE_LEAVES_SLAB.get(), BlockInit.DARK_OAK_LEAVES_SLAB.get(), BlockInit.OAK_LEAVES_SLAB.get(),
                    BlockInit.ACACIA_LEAVES_WALL.get(), BlockInit.JUNGLE_LEAVES_WALL.get(), BlockInit.DARK_OAK_LEAVES_WALL.get(), BlockInit.OAK_LEAVES_WALL.get(),
                    BlockInit.ACACIA_LEAVES_TRAPDOOR.get(), BlockInit.JUNGLE_LEAVES_TRAPDOOR.get(), BlockInit.DARK_OAK_LEAVES_TRAPDOOR.get(), BlockInit.OAK_LEAVES_TRAPDOOR.get(),
                    BlockInit.SPRUCE_LEAVES_STAIRS.get(), BlockInit.SPRUCE_LEAVES_SLAB.get(), BlockInit.SPRUCE_LEAVES_WALL.get(), BlockInit.SPRUCE_LEAVES_TRAPDOOR.get(),
                    BlockInit.BIRCH_LEAVES_STAIRS.get(), BlockInit.BIRCH_LEAVES_SLAB.get(), BlockInit.BIRCH_LEAVES_WALL.get(), BlockInit.BIRCH_LEAVES_TRAPDOOR.get());
        }
    }
}
