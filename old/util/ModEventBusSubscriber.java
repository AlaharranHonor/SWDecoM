package com.alaharranhonor.swdm.old.util;

import com.alaharranhonor.swdm.old.SWDM;
import com.alaharranhonor.swdm.old.block.ClockBlock;
import com.alaharranhonor.swdm.old.block.HalfWallBlock;
import com.alaharranhonor.swdm.old.block.entity.ClockBE;
import com.alaharranhonor.swdm.old.util.init.BlockInit;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {
    public static ClockBlock CLOCK; // Optional Geckolib extra.
    public static BlockEntityType<ClockBE> CLOCK_TE; // Optional Geckolib extra.


    @SubscribeEvent
    public static void onBlockInitialization(RegistryEvent.Register<Block> event) {
        if (ModList.get().isLoaded("geckolib3")) {
            //CLOCK = (ClockBlock) new ClockBlock(Block.Properties.of(Material.WOOD).strength(1).noOcclusion()).setRegistryName(com.alaharranhonor.swdm.SWDM.MOD_ID, "clock");
            //event.getRegistry().register(CLOCK);
        }
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        if (ModList.get().isLoaded("geckolib3")) {
            //event.getRegistry().register(new BlockItem(CLOCK, new Item.Properties().tab(com.alaharranhonor.swdm.SWDM.SWDMTAB)).setRegistryName(CLOCK.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void onBlockEntityRegistry(RegistryEvent.Register<BlockEntityType<?>> event) {
        if (ModList.get().isLoaded("geckolib3")) {
            //CLOCK_TE = BlockEntityType.Builder.of(ClockBE::new, CLOCK).build(null);
            //event.getRegistry().register(CLOCK_TE.setRegistryName(com.alaharranhonor.swdm.SWDM.MOD_ID, "clock"));
        }
    }


    @Mod.EventBusSubscriber(modid = SWDM.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusHandler {
        @SubscribeEvent
        public static void RegisterBlockColors(ColorHandlerEvent.Block event) {
            BlockColors colors = event.getBlockColors();
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
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();

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
        }
    }
}
