package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.ClockBlock;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.entity.ClockBE;
import com.alaharranhonor.swdm.util.init.BlockInit;
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
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
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
            //CLOCK = (ClockBlock) new ClockBlock(Block.Properties.of(Material.WOOD).strength(1).noOcclusion()).setRegistryName(SWDM.MOD_ID, "clock");
            //event.getRegistry().register(CLOCK);
        }
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        if (ModList.get().isLoaded("geckolib3")) {
            //event.getRegistry().register(new BlockItem(CLOCK, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(CLOCK.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void onBlockEntityRegistry(RegistryEvent.Register<BlockEntityType<?>> event) {
        if (ModList.get().isLoaded("geckolib3")) {
            //CLOCK_TE = BlockEntityType.Builder.of(ClockBE::new, CLOCK).build(null);
            //event.getRegistry().register(CLOCK_TE.setRegistryName(SWDM.MOD_ID, "clock"));
        }
    }


    @Mod.EventBusSubscriber(modid = SWDM.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusHandler {
        @SubscribeEvent
        public static void RegisterBlockColors(ColorHandlerEvent.Block event) {
            BlockColors colors = event.getBlockColors();
            List<RegistryObject<StairBlock>> stairs = BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get("leaves");
            List<RegistryObject<SlabBlock>> slabs = BlockInit.SSW_SET_SLABS.get("wv-whitewash").get("leaves");
            List<RegistryObject<HalfWallBlock>> walls = BlockInit.SSW_SET_WALLS.get("wv-whitewash").get("leaves");

            // Rest vanilla wood types.
            // oak, acacia, jungle, dark_oak
            colors.register((state, reader, pos, color) -> {
                        return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
                    },
                    stairs.get(3).get(), stairs.get(4).get(), stairs.get(5).get(), stairs.get(0).get(),
                    slabs.get(3).get(), slabs.get(4).get(), slabs.get(5).get(), slabs.get(0).get(),
                    walls.get(3).get(), walls.get(4).get(), walls.get(5).get(), walls.get(0).get()
                    //BlockInit.LEAVES_TRAPDOORS.get(3).get(), BlockInit.LEAVES_TRAPDOORS.get(4).get(), BlockInit.LEAVES_TRAPDOORS.get(5).get(), BlockInit.LEAVES_TRAPDOORS.get(0).get()
            );

            // Spruce wood type
            colors.register((state, reader, pos, color) -> {
                return FoliageColor.getEvergreenColor();
            }, stairs.get(1).get(), slabs.get(1).get(), walls.get(1).get()//, BlockInit.LEAVES_TRAPDOORS.get(1).get()
            );

            // Birch wood type.
            colors.register((state, reader, pos, color) -> {
                return FoliageColor.getBirchColor();
            }, stairs.get(2).get(), slabs.get(2).get(),walls.get(2).get()//, BlockInit.LEAVES_TRAPDOORS.get(2).get()
            );


            colors.register((p_228064_0_, p_228064_1_, p_228064_2_, p_228064_3_) -> {
                return p_228064_1_ != null && p_228064_2_ != null ? BiomeColors.getAverageGrassColor(p_228064_1_, p_228064_2_) : GrassColor.get(0.5D, 1.0D);
            }, BlockInit.GRASS_SLAB.get());
        }

        @SubscribeEvent
        public static void RegisterItemColors(ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();

            Stream<RegistryObject<? extends Block>> stream = Stream.empty();
            stream = Stream.concat(stream, BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get("leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSW_SET_WALLS.get("wv-whitewash").get("leaves").stream());
            stream = Stream.concat(stream, BlockInit.SSW_SET_SLABS.get("wv-whitewash").get("leaves").stream());
            //stream = Stream.concat(stream, BlockInit.LEAVES_TRAPDOORS.stream());
            stream = Stream.concat(stream, Stream.of(BlockInit.GRASS_SLAB));

            Stream<Block> blocks = stream.map(RegistryObject::get);

            colors.register((stack, color) -> {
                        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                        return event.getBlockColors().getColor(blockstate, null, (BlockPos) null, color);
                    }, blocks.toArray(Block[]::new)
                    );
        }
    }
}
