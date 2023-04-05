package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.TwoWayBlock;
import com.alaharranhonor.swdm.gentypes.*;
import com.alaharranhonor.swdm.util.ResourceLocationUtil;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SetSetup {

    public static final List<String> STONE_TYPES = List.of("mossy", "cracked", "mossy_more");
    public static final List<String> LMD_TYPES = List.of("light", "medium", "dark");
    public static final List<String> CUSTOM_COLORS = List.of("sage", "golden");
    public static final List<String> NATURAL_TONES = List.of("tuscan", "peach", "thistle", "dark_brown", "brown", "mahogany", "muted_brown", "vivid_red", "orange", "golden", "pale", "yellow", "white", "pearl", "dusted_gray", "light_gray", "slate", "blue_gray", "gray", "royal_gray", "black");
    public static final List<String> DYE_COLORS = Arrays.stream(DyeColor.values()).map(DyeColor::getSerializedName).collect(Collectors.toList());

    public static final List<String> WOODS = List.of(WoodType.OAK.name(), WoodType.BIRCH.name(), WoodType.SPRUCE.name(), WoodType.JUNGLE.name(), WoodType.ACACIA.name(), WoodType.DARK_OAK.name());
    public static final List<String> STEMS = List.of(WoodType.CRIMSON.name(), WoodType.WARPED.name());

    public static final List<String> MODDED_WOODS = List.of("whitewash", "bamboo", "thatch");

    public static final List<GenSet> SETS = new ArrayList<>();

    public static void init() {
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.DIORITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.GRANITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.ANDESITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES, STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.DARK_PRISMARINE).withBase(BlockGen::new).sets(DYE_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA).withBase(BlockGen::new).sets(CUSTOM_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS, "bricks_glass").withBase(BlockGen::new).sets(DYE_COLORS).types(stoneTypes()).renderType(RenderType.cutout()).build());
        SETS.add(GenSet.builder(() -> Blocks.BRICKS, "bricks_cottage").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.SANDSTONE, "sandcotta").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "paver").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "bricks_paver").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).build());

        SETS.add(GenSet.builder(() -> Blocks.OAK_PLANKS, "siding").withBase(BlockGen::new).sets(LMD_TYPES, DYE_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_metal").withBase(BlockGen::new).sets(DYE_COLORS).types(sswtTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_tile").withBase(BlockGen::new).sets(DYE_COLORS).types(sswtTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_shingle").withBase(BlockGen::new).sets(DYE_COLORS).types(sswtTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS, "screen").withBase(BlockGen::new).sets(LMD_TYPES).types(sswtTypes()).renderType(RenderType.cutout()).build());


        SETS.add(GenSet.builder(() -> Blocks.DIRT).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.COARSE_DIRT).types(stoneTypes()).build());

        // TODO
        //SETS.add(GenSet.builder(() -> Blocks.GRASS_BLOCK).types(stoneTypes()).textures(grassyTextureSet()).renderType(RenderType.cutoutMipped())
        //    .blockColors((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.get(0.5, 1.0))
        //    .itemColors((pStack, pTintIndex) -> GrassColor.get(0.5, 1.0))
        //    .build());

        SETS.add(GenSet.builder(() -> Blocks.MYCELIUM).types(stoneTypes()).textures(grassyTextureSet()).build());
        SETS.add(GenSet.builder(() -> Blocks.PODZOL).types(stoneTypes()).textures(grassyTextureSet()).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS).types(stoneTypes()).renderType(RenderType.cutout()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.SMOOTH_STONE, "smooth_stone_borderless").withBase(BlockGen::new).types(stoneTypes()).build());

        SETS.add(GenSet.builder(() -> Blocks.WHITE_CARPET, "fiber_carpet").sets(LMD_TYPES, NATURAL_TONES).types(HorizontalCarpetGen::new).renderType(RenderType.cutout()).build());
        SETS.add(GenSet.builder(() -> Blocks.SAND, "fiber_carpet").sets(LMD_TYPES, List.of("sand", "red_sand")).types(HorizontalCarpetGen::new).renderType(RenderType.cutout()).build());

        SETS.add(GenSet.builder(() -> Blocks.CHAIN, "chain_coated").sets(DYE_COLORS).types(TwoWayGen::new).renderType(RenderType.cutout()).build());
        SETS.add(GenSet.builder(() -> Blocks.CHAIN, "pylon").sets(DYE_COLORS).types(b -> new TwoWayGen(b) {
            @Override
            protected TwoWayBlock generate() {
                return new TwoWayBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).lightLevel(s -> 15));
            }
        }).renderType(RenderType.cutout()).build());

        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getSerializedName();
            Block concrete = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_concrete"));
            Block terracotta = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_terracotta"));
            Block wool = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_wool"));
            Block stained_glass = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_stained_glass"));
            SETS.add(GenSet.builder(() -> concrete).types(stoneTypes()).build());
            SETS.add(GenSet.builder(() -> terracotta).types(stoneTypes()).build());
            SETS.add(GenSet.builder(() -> wool).types(sswtTypes()).build());
            SETS.add(GenSet.builder(() -> stained_glass).types(sswtTypes()).renderType(RenderType.translucent()).build());
            SETS.add(GenSet.builder(() -> wool, "wool_pastel_" + colorName).withBase(BlockGen::new).types(sswtTypes()).build());
            SETS.add(GenSet.builder(() -> wool, "wool_tinted_" + colorName).withBase(BlockGen::new).types(sswtTypes()).build());
        }

        WOODS.forEach(name -> {
            Block planks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_planks"));
            Block log = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_log"));
            Block strippedLog = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("stripped_" + name + "_log"));
            Block trapdoor = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_trapdoor"));
            SETS.add(GenSet.builder(() -> planks).types(woodenPlanksTypes()).build());
            SETS.add(GenSet.builder(() -> log, name + "_log").types(woodenTypes()).build());
            SETS.add(GenSet.builder(() -> strippedLog, "stripped_" + name + "_log").types(woodenTypes()).build());

            SETS.add(GenSet.builder(() -> planks, name).types(LadderGen::new).textures(ladders()).renderType(RenderType.cutout()).build());
            SETS.add(GenSet.builder(() -> trapdoor).types(ShutterGen::new).renderType(RenderType.cutout()).build());
        });

        STEMS.forEach(name -> {
            Block planks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_planks"));
            Block stem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_stem"));
            Block strippedStem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("stripped_" + name + "_stem"));
            Block trapdoor = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_trapdoor"));
            SETS.add(GenSet.builder(() -> planks).types(woodenPlanksTypes()).build());
            SETS.add(GenSet.builder(() -> stem).types(woodenTypes()).build());
            SETS.add(GenSet.builder(() -> strippedStem).types(woodenTypes()).build());

            SETS.add(GenSet.builder(() -> planks, name).types(LadderGen::new).textures(ladders()).renderType(RenderType.cutout()).build());
            SETS.add(GenSet.builder(() -> trapdoor).types(ShutterGen::new).build());
        });


        MODDED_WOODS.forEach(name -> {
            RegistryObject<Block> planks = BlockSetup.BLOCKS_BY_NAME.get(SWDM.res(name + "_planks"));
            RegistryObject<Block> log = BlockSetup.BLOCKS_BY_NAME.get(SWDM.res(name + "_log"));
            RegistryObject<Block> strippedLog = BlockSetup.BLOCKS_BY_NAME.get(SWDM.res(name + "_stripped_log"));
            SETS.add(GenSet.builder(planks).types(woodenTypes()).build());
            SETS.add(GenSet.builder(log).types(woodenTypes()).build());
            SETS.add(GenSet.builder(strippedLog).types(woodenTypes()).build());

            SETS.add(GenSet.builder(planks, name).types(LadderGen::new).textures(ladders()).renderType(RenderType.cutout()).build());
            SETS.add(GenSet.builder(planks, name + "_trapdoor").withBase(b -> cast(new TrapDoorGen(b))).types(ShutterGen::new).renderType(RenderType.cutout()).build());
        });

        for (GenSet set : SETS) {
            set.register(BlockSetup.BLOCKS, ItemSetup.ITEMS);
        }
    }

    private static List<Function<Supplier<Block>, GenType<?>>> sswtTypes() {
        return List.of(StairGen::new, SlabGen::new, HalfWallGen::new, TrapDoorGen::new);
    }

    private static List<Function<Supplier<Block>, GenType<?>>> stoneTypes() {
        return List.of(StairGen::new, SlabGen::new, HalfWallGen::new, TrapDoorGen::new, b -> new ButtonGen(b, true), b -> new PressurePlateGen(b, PressurePlateBlock.Sensitivity.MOBS));
    }

    private static List<Function<Supplier<Block>, GenType<?>>> woodenTypes() {
        return List.of(StairGen::new, SlabGen::new, HalfWallGen::new, TrapDoorGen::new, b -> new ButtonGen(b, false), b -> new PressurePlateGen(b, PressurePlateBlock.Sensitivity.EVERYTHING), FenceGateGen::new);
    }

    private static List<Function<Supplier<Block>, GenType<?>>> woodenPlanksTypes() {
        return List.of(HalfWallGen::new, TrapDoorGen::new);
    }

    private static Consumer<TextureSet.Builder> grassyTextureSet() {
        return textures -> {
            textures.with("top", base -> TextureSet.Builder.block(ResourceLocationUtil.suffix(base, "_top")));
            textures.with("side", base -> TextureSet.Builder.block(ResourceLocationUtil.suffix(base, "_side")));
            textures.with("bottom", base -> new ResourceLocation("block/dirt"));
        };
    }

    private static Consumer<TextureSet.Builder> ladders() {
        return textures -> {
            textures.with("ladder", base -> TextureSet.Builder.block(ResourceLocationUtil.suffix(base, "_ladder")));
        };
    }

    private static <T, R extends Object> T cast(R o) {
        return (T) o;
    }
}
