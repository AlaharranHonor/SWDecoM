package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
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
            SetSetup.SETS.forEach(set -> {
                if (set.getBlockColors() != null) {
                    set.genTypes.forEach(type -> type.registerBlockColors(colors, set.getBlockColors()));
                }
            });

            /*
            List<RegistryObject<StairBlock>> stairs = BlockInit.SSWT_SET_STAIRS.get("wv-extra", "leaves");
            List<RegistryObject<SlabBlock>> slabs = BlockInit.SSWT_SET_SLABS.get("wv-extra", "leaves");
            List<RegistryObject<HalfWallBlock>> walls = BlockInit.SSWT_SET_WALLS.get("wv-extra", "leaves");
            List<RegistryObject<TrapDoorBlock>> trapdoors = BlockInit.SSWT_SET_TRAPDOORS.get("wv-extra", "leaves");

            // Rest vanilla wood types.
            // oak, acacia, jungle, dark_oak
            colors.register((state, reader, pos, color) -> {
                        return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
                    },
                    stairs.get(3).get(), stairs.get(4).get(), stairs.get(5).get(), stairs.get(0).get(),
                    slabs.get(3).get(), slabs.get(4).get(), slabs.get(5).get(), slabs.get(0).get(),
                    walls.get(3).get(), walls.get(4).get(), walls.get(5).get(), walls.get(0).get(),
                    trapdoors.get(3).get(), trapdoors.get(4).get(), trapdoors.get(5).get(), trapdoors.get(0).get()
            );

            // Spruce wood type
            colors.register((state, reader, pos, color) -> {
                return FoliageColor.getEvergreenColor();
            }, stairs.get(1).get(), slabs.get(1).get(), walls.get(1).get(), trapdoors.get(1).get()
            );

            // Birch wood type.
            colors.register((state, reader, pos, color) -> {
                return FoliageColor.getBirchColor();
            }, stairs.get(2).get(), slabs.get(2).get(),walls.get(2).get(), trapdoors.get(2).get()
            );

            BlockInit.SSWT_SET_STAIRS.get("standalone", "grass_block").forEach(rb -> colors.register((state, level, pos, color) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D), rb.get()));
            BlockInit.SSWT_SET_SLABS.get("standalone", "grass_block").forEach(rb -> colors.register((state, level, pos, color) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D), rb.get()));
            BlockInit.SSWT_SET_WALLS.get("standalone", "grass_block").forEach(rb -> colors.register((state, level, pos, color) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D), rb.get()));
            BlockInit.SSWT_SET_TRAPDOORS.get("standalone", "grass_block").forEach(rb -> colors.register((state, level, pos, color) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D), rb.get()));
            */
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();
            SetSetup.SETS.forEach(set -> {
                if (set.getBlockColors() != null) {
                    set.genTypes.forEach(type -> type.registerItemColors(colors, set.getItemColors()));
                }
            });

            /*
            Stream<RegistryObject<? extends Block>> stream = Stream.empty();
            stream = Stream.concat(stream, BlockInit.SSWT_SET_STAIRS.get("wv-extra", "leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_SLABS.get("wv-extra", "leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_WALLS.get("wv-extra", "leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_TRAPDOORS.get("wv-extra", "leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_STAIRS.get("standalone", "grass_block").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_SLABS.get("standalone", "grass_block").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_WALLS.get("standalone", "grass_block").stream());
            stream = Stream.concat(stream, BlockInit.SSWT_SET_TRAPDOORS.get("standalone", "grass_block").stream());

            Stream<Block> blocks = stream.map(RegistryObject::get);

            colors.register((stack, color) -> {
                        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                        return event.getBlockColors().getColor(blockstate, null, (BlockPos) null, color);
                    }, blocks.toArray(Block[]::new)
                    );

             */
        }
    }
}
