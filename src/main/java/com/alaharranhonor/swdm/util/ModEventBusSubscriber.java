package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @Mod.EventBusSubscriber(modid = SWDM.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusHandler {
        @SubscribeEvent
        public static void RegisterBlockColors(ColorHandlerEvent.Block event) {
            BlockColors colors = event.getBlockColors();


            // Rest vanilla wood types.
            // oak, acacia, jungle, dark_oak
            colors.register((state, reader, pos, color) -> {
                        return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
                    },
                    BlockInit.LEAVES_STAIRS.get(3).get(), BlockInit.LEAVES_STAIRS.get(4).get(), BlockInit.LEAVES_STAIRS.get(5).get(), BlockInit.LEAVES_STAIRS.get(0).get(),
                    BlockInit.LEAVES_SLABS.get(3).get(), BlockInit.LEAVES_SLABS.get(4).get(), BlockInit.LEAVES_SLABS.get(5).get(), BlockInit.LEAVES_SLABS.get(0).get(),
                    BlockInit.LEAVES_WALLS.get(3).get(), BlockInit.LEAVES_WALLS.get(4).get(), BlockInit.LEAVES_WALLS.get(5).get(), BlockInit.LEAVES_WALLS.get(0).get(),
                    BlockInit.LEAVES_TRAPDOORS.get(3).get(), BlockInit.LEAVES_TRAPDOORS.get(4).get(), BlockInit.LEAVES_TRAPDOORS.get(5).get(), BlockInit.LEAVES_TRAPDOORS.get(0).get());

            // Spruce wood type
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getEvergreenColor();
            }, BlockInit.LEAVES_STAIRS.get(1).get(), BlockInit.LEAVES_SLABS.get(1).get(),BlockInit.LEAVES_WALLS.get(1).get(), BlockInit.LEAVES_TRAPDOORS.get(1).get());

            // Birch wood type.
            colors.register((state, reader, pos, color) -> {
                return FoliageColors.getBirchColor();
            }, BlockInit.LEAVES_STAIRS.get(2).get(), BlockInit.LEAVES_SLABS.get(2).get(),BlockInit.LEAVES_WALLS.get(2).get(), BlockInit.LEAVES_TRAPDOORS.get(2).get());
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();

            Stream<RegistryObject<Block>> stream = Stream.empty();
            stream = Stream.concat(stream, BlockInit.LEAVES_STAIRS.stream());
            stream = Stream.concat(stream, BlockInit.LEAVES_WALLS.stream());
            stream = Stream.concat(stream, BlockInit.LEAVES_SLABS.stream());
            stream = Stream.concat(stream, BlockInit.LEAVES_TRAPDOORS.stream());

            Stream<Block> blocks = stream.map(RegistryObject::get);

            colors.register((stack, color) -> {
                        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                        return event.getBlockColors().getColor(blockstate, (IBlockDisplayReader) null, (BlockPos) null, color);
                    }, blocks.toArray(Block[]::new)
                    );
        }
    }
}
