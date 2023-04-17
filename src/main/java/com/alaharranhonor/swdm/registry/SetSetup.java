package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.TwoWayBlock;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.gentypes.block.*;
import com.alaharranhonor.swdm.gentypes.item.StickGen;
import com.alaharranhonor.swdm.util.RL;
import com.alaharranhonor.swdm.util.RenderTypeWrapper;
import com.alaharranhonor.swdm.util.TextureSet;
import com.google.common.collect.Streams;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SetSetup {

    public static final List<String> STONE_TYPES = List.of("mossy", "cracked", "mossy_more");
    public static final List<String> FENCE_TYPES = List.of("lattice", "picket", "slat", "solid", "sturdy");

    public static final List<String> LMD_TYPES = List.of("light", "medium", "dark");
    public static final List<String> CUSTOM_COLORS = List.of("sage", "golden");
    public static final List<String> WARM_COOL = List.of("warm", "cool");
    public static final List<String> NATURAL_TONES = List.of("tuscan", "peach", "thistle", "dark_brown", "brown", "mahogany", "muted_brown", "vivid_red", "orange", "golden", "pale", "yellow", "white", "pearl", "dusted_gray", "light_gray", "slate", "blue_gray", "gray", "royal_gray", "black");
    public static final List<String> DYE_COLORS = Arrays.stream(DyeColor.values()).map(DyeColor::getSerializedName).collect(Collectors.toList());
    public static final List<String> ROOF_COLORS = Streams.concat(List.copyOf(DYE_COLORS).stream(), List.of("brown_legacy", "white_legacy", "lime_legacy").stream()).toList();
    
    public static final List<String> ARRAY_LETTERS = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    public static final List<String> ARRAY_NUMBERS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
    public static final List<String> ARRAY_SYMBOLS = List.of("ampersand", "apostrophe", "asterisk", "at", "backslash", "bar", "blank", "colon", "comma", "dash", "down_arrow", "equal", "exclamation", "left_arrow", "left_brace", "left_bracket", "left_parenthesis", "period", "plus", "question", "quotation", "right_arrow", "right_brace", "right_bracket", "right_parenthesis", "semicolon", "slash", "tilde", "underscore", "up_arrow");

    public static final List<String> WOODS = List.of(WoodType.OAK.name(), WoodType.BIRCH.name(), WoodType.SPRUCE.name(), WoodType.JUNGLE.name(), WoodType.ACACIA.name(), WoodType.DARK_OAK.name());
    public static final List<String> STEMS = List.of(WoodType.CRIMSON.name(), WoodType.WARPED.name());
    public static final List<String> VANILLA_WOODS = Streams.concat(WOODS.stream(), STEMS.stream()).toList();
    public static final List<String> MODDED_WOODS = List.of("whitewash", "bamboo", "thatch");
    public static final List<String> ALL_WOODS = Streams.concat(VANILLA_WOODS.stream(), MODDED_WOODS.stream()).toList();

    public static final int LEAVES_VARIANTS = 7;

    public static final List<GenSet> SETS = new ArrayList<>();

    public static void init() {
        SETS.add(GenSet.builder(() -> Blocks.STONE)/*.types(HalfWallGen::new)*/.blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(LMD_TYPES, WARM_COOL).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.COBBLESTONE).withBase(BlockGen::new).sets(LMD_TYPES, WARM_COOL).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.BRICKS, "brick").withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.BRICKS, "brick").withBase(BlockGen::new).sets(LMD_TYPES, STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.COBBLESTONE, "paver").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.COBBLESTONE, "bricks_paver").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.DIORITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.GRANITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.ANDESITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES, STONE_TYPES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.DARK_PRISMARINE).withBase(BlockGen::new).sets(DYE_COLORS).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA).withBase(BlockGen::new).sets(CUSTOM_COLORS).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS, "bricks_glass").withBase(BlockGen::new).sets(DYE_COLORS).types(sswtTypes()).renderType(RenderTypeWrapper::cutout).build());
        SETS.add(GenSet.builder(() -> Blocks.BRICKS, "bricks_cottage").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.SANDSTONE, "sandcotta").withBase(BlockGen::new).sets(NATURAL_TONES).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        SETS.add(GenSet.builder(() -> Blocks.SANDSTONE).sets(NATURAL_TONES).types(b -> new BlockGen(b) {
            @Override
            public void addBlockStates(BlockStates gen, TextureSet textures) {
                ResourceLocation basePath = this.generated.getRegistryName();
                gen.simpleBlock(this.generated, gen.models().cubeBottomTop(basePath.getPath(), RL.prefix(basePath, "block/"), textures.get("bottom", basePath), textures.get("top", basePath)));
            }
        }).blockTextures(sandstone()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        SETS.add(GenSet.builder(() -> Blocks.SAND).sets(NATURAL_TONES).types(SandGen::new).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());

        SETS.add(GenSet.builder(() -> Blocks.OAK_PLANKS, "siding").withBase(SidingGen::new).sets(LMD_TYPES, DYE_COLORS).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_metal").withBase(RoofGen.Metal::new).sets(ROOF_COLORS).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_tile").withBase(RoofGen.Tile::new).sets(ROOF_COLORS).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA, "roof_shingle").withBase(RoofGen.Shingle::new).sets(ROOF_COLORS).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.IRON_BARS, "screen").withBase(BlockGen::new).sets(LMD_TYPES).types(sswtTypes()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        SETS.add(GenSet.builder(() -> Blocks.DIRT).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());
        SETS.add(GenSet.builder(() -> Blocks.COARSE_DIRT).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());

        SETS.add(GenSet.builder(() -> Blocks.SNOW_BLOCK).types(sswtTypes()).blockTextures(t -> t.with("", base -> new ResourceLocation("block/snow"))).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());
        SETS.add(GenSet.builder(() -> Blocks.BONE_BLOCK, "bianco_block").withBase(BlockGen::new).types(sswtTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        // TODO
        //SETS.add(GenSet.builder(() -> Blocks.GRASS_BLOCK).types(stoneTypes()).textures(grassyTextureSet()).renderType(RenderType.cutoutMipped())
        //    .blockColors((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : GrassColor.get(0.5, 1.0))
        //    .itemColors((pStack, pTintIndex) -> GrassColor.get(0.5, 1.0))
        //    .build());

        SETS.add(GenSet.builder(() -> Blocks.MYCELIUM).types(sswtTypes()).blockTextures(grassyTextureSet()).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());
        SETS.add(GenSet.builder(() -> Blocks.PODZOL).types(sswtTypes()).blockTextures(grassyTextureSet()).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS).types(sswtTypes()).renderType(RenderTypeWrapper::cutout).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        SETS.add(GenSet.builder(BlockSetup.SMOOTH_STONE_BORDERLESS).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(BlockSetup.SMOOTH_STONE_BORDERLESS).withBase(BlockGen::new).sets(LMD_TYPES, WARM_COOL).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        SETS.add(GenSet.builder(() -> Blocks.WHITE_CARPET, "fiber_carpet").sets(LMD_TYPES, NATURAL_TONES).types(HorizontalCarpetGen::new).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.WOOL).build());
        SETS.add(GenSet.builder(() -> Blocks.SAND, "fiber_carpet").sets(LMD_TYPES, List.of("sand", "red_sand")).types(HorizontalCarpetGen::new).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_SHOVEL).build());

        SETS.add(GenSet.builder(() -> Blocks.CHAIN, "chain_coated").sets(DYE_COLORS).types(ChainGen::new).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
        SETS.add(GenSet.builder(() -> Blocks.CHAIN, "pylon").sets(DYE_COLORS).types(b -> new ChainGen(b) {
            @Override
            protected TwoWayBlock generate() {
                return new TwoWayBlock(BlockBehaviour.Properties.copy(this.baseBlock.get()).lightLevel(s -> 15));
            }
        }).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());

        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getSerializedName();
            Block concrete = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_concrete"));
            Block terracotta = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_terracotta"));
            Block wool = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_wool"));
            Block stained_glass = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(colorName + "_stained_glass"));
            SETS.add(GenSet.builder(() -> concrete).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
            SETS.add(GenSet.builder(() -> terracotta).types(stoneTypes()).blockTags(BlockTags.MINEABLE_WITH_PICKAXE).build());
            SETS.add(GenSet.builder(() -> wool).types(sswtTypes()).blockTags(BlockTags.WOOL).build());
            SETS.add(GenSet.builder(() -> stained_glass).types(sswtTypes()).renderType(RenderTypeWrapper::translucent).build());
            SETS.add(GenSet.builder(() -> wool, "wool_pastel_" + colorName).withBase(BlockGen::new).types(sswtTypes()).blockTags(BlockTags.WOOL).build());
            SETS.add(GenSet.builder(() -> wool, "wool_tinted_" + colorName).withBase(BlockGen::new).types(sswtTypes()).blockTags(BlockTags.WOOL).build());
        }

        WOODS.forEach(name -> {
            Block planks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_planks"));
            Block log = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_log"));
            Block strippedLog = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("stripped_" + name + "_log"));
            Block trapdoor = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_trapdoor"));
            Block leaves = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_leaves"));
            SETS.add(GenSet.builder(() -> planks).types(woodenPlanksTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> log).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> strippedLog).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> trapdoor).types(ShutterGen::new).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(() -> planks, name).types(StickGen::new).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> planks, name).types(LadderGen::new).blockTextures(ladders()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> planks, "fence_" + name).types(SWDMFenceGen::new).sets(FENCE_TYPES).blockTextures(swdmFences(name)).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(() -> planks, "beam_" + name + "_planks").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> log, "beam_" + name + "_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> strippedLog, "beam_" + name + "_stripped_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            for (int variant = 1; variant <= LEAVES_VARIANTS; variant++) { // Starting loops at 1.. yuck.
                SETS.add(GenSet.builder(() -> leaves, name + "_leaves_variant" + variant).withBase(BlockGen::new).types(sswtTypes()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.LEAVES).build());
            }

            if (!"oak".equals(name)) { // Oak Bookshelf is the default
                SETS.add(GenSet.builder(() -> planks, name).types(BookshelfGen::new).blockTextures(bookshelf()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            }
        });

        STEMS.forEach(name -> {
            Block planks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_planks"));
            Block stem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_stem"));
            Block strippedStem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("stripped_" + name + "_stem"));
            Block trapdoor = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name + "_trapdoor"));
            SETS.add(GenSet.builder(() -> planks).types(woodenPlanksTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> stem).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> strippedStem).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> trapdoor).types(ShutterGen::new).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(() -> planks, "beam_" + name + "_planks").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> stem, "beam_" + name + "_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> strippedStem, "beam_" + name + "_stripped_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(() -> planks, name).types(StickGen::new).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> planks, name).types(BookshelfGen::new).blockTextures(bookshelf()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> planks, name).types(LadderGen::new).blockTextures(ladders()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(() -> planks, "fence_" + name).types(SWDMFenceGen::new).sets(FENCE_TYPES).blockTextures(swdmFences(name)).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
        });

        MODDED_WOODS.forEach(name -> {
            RegistryObject<Block> planks = BlockSetup.MANUAL_BLOCKS.get(SWDM.res(name + "_planks"));
            RegistryObject<Block> log = BlockSetup.MANUAL_BLOCKS.get(SWDM.res(name + "_log"));
            RegistryObject<Block> strippedLog = BlockSetup.MANUAL_BLOCKS.get(SWDM.res(name + "_stripped_log"));
            SETS.add(GenSet.builder(planks).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(log).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(strippedLog).types(woodenTypes()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(planks, "beam_" + name + "_planks").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(log, "beam_" + name + "_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(strippedLog, "beam_" + name + "_stripped_log").withBase(b -> cast(new BeamGen(b))).blockTextures(beams()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());

            SETS.add(GenSet.builder(planks, name).types(StickGen::new).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(planks, name).types(BookshelfGen::new).blockTextures(bookshelf()).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(planks, name).types(DoorGen::new).blockTextures(doors()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(planks, name).types(LadderGen::new).blockTextures(ladders()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(planks, name).withBase(b -> cast(new TrapDoorGen(b))).types(ShutterGen::new).blockTextures(trapdoors()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            SETS.add(GenSet.builder(planks, "fence_" + name).types(SWDMFenceGen::new).sets(FENCE_TYPES).blockTextures(swdmFences(name)).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
        });

        // Trapdoor/Door variations (wood_trapdoor_variation and wood_door_variation)
        ALL_WOODS.forEach(variation -> {
            VANILLA_WOODS.forEach(wood -> {
                if (wood.equals(variation)) return;
                Block planks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(wood + "_planks"));
                SETS.add(GenSet.builder(() -> planks, wood).types(b -> new TrapdoorVariationGen(b, variation), b -> new DoorVariationGen(b, variation)).blockTextures(variationDoors()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            });
            MODDED_WOODS.forEach(wood -> {
                if (wood.equals(variation)) return;
                RegistryObject<Block> planks = BlockSetup.MANUAL_BLOCKS.get(SWDM.res(wood + "_planks"));
                SETS.add(GenSet.builder(planks, wood).types(b -> new TrapdoorVariationGen(b, variation), b -> new DoorVariationGen(b, variation)).blockTextures(variationDoors()).renderType(RenderTypeWrapper::cutout).blockTags(BlockTags.MINEABLE_WITH_AXE).build());
            });
        });

        // Array Trapdoors
        List.of("white", "black").forEach(color -> {
            RegistryObject<Block> smoothStone = BlockSetup.SMOOTH_STONE_BORDERLESS;
            ARRAY_NUMBERS.forEach(type -> {
                SETS.add(GenSet.builder(smoothStone, "array_" + color + "_number_" + type).withBase(b -> cast(new ArrayGen(b))).blockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE).build());
            });
            ARRAY_LETTERS.forEach(type -> {
                SETS.add(GenSet.builder(smoothStone, "array_" + color + "_letter_" + type).withBase(b -> cast(new ArrayGen(b))).blockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE).build());
            });
            ARRAY_SYMBOLS.forEach(type -> {
                SETS.add(GenSet.builder(smoothStone, "array_" + color + "_symbol_" + type).withBase(b -> cast(new ArrayGen(b))).blockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE).build());
            });
        });


        for (GenSet set : SETS) {
            set.register(BlockSetup.BLOCKS, ItemSetup.ITEMS);
        }
    }

    private static List<Function<Supplier<Block>, GenType<?>>> sswtTypes() {
        return List.of(StairGen::new, SlabGen::new, /*HalfWallGen::new,*/ TrapDoorGen::new);
    }

    private static List<Function<Supplier<Block>, GenType<?>>> stoneTypes() {
        return List.of(StairGen::new, SlabGen::new, /*HalfWallGen::new,*/ TrapDoorGen::new, b -> new ButtonGen(b, true), b -> new PressurePlateGen(b, PressurePlateBlock.Sensitivity.MOBS));
    }

    private static List<Function<Supplier<Block>, GenType<?>>> woodenTypes() {
        return List.of(StairGen::new, SlabGen::new, /*HalfWallGen::new,*/ TrapDoorGen::new, b -> new ButtonGen(b, false), b -> new PressurePlateGen(b, PressurePlateBlock.Sensitivity.EVERYTHING), FenceGateGen::new);
    }

    private static List<Function<Supplier<Block>, GenType<?>>> woodenPlanksTypes() {
        return List.of(/*HalfWallGen::new,*/ TrapDoorGen::new);
    }

    private static Consumer<TextureSet.Builder> grassyTextureSet() {
        return textures -> {
            textures.with("top", base -> TextureSet.Builder.block(RL.suffix(base, "_top")));
            textures.with("side", base -> TextureSet.Builder.block(RL.suffix(base, "_side")));
            textures.with("bottom", base -> new ResourceLocation("block/dirt"));
        };
    }

    private static Consumer<TextureSet.Builder> swdmFences(String name) {
        return tex -> tex
                .with("", base -> new ResourceLocation(base.getNamespace(), "block/" + name + "_planks"))
                .with("lattice", base -> SWDM.res("block/lattice_" + name));
    }

    private static Consumer<TextureSet.Builder> sandstone() {
        BiFunction<String, String, String> transform = (name, type) -> {
            int splitIndex = name.indexOf("_");
            return name.substring(0, splitIndex) + "_" + type + name.substring(splitIndex);
        };
        return tex -> tex
            .with("top", base -> TextureSet.Builder.block(new ResourceLocation(base.getNamespace(), transform.apply(base.getPath(), "top"))))
            .with("bottom", base -> TextureSet.Builder.block(new ResourceLocation(base.getNamespace(), transform.apply(base.getPath(), "bottom"))));
    }

    private static Consumer<TextureSet.Builder> ladders() {
        return textures -> {
            textures.with("ladder", base -> TextureSet.Builder.block(RL.suffix(base, "_ladder")));
        };
    }

    private static Consumer<TextureSet.Builder> trapdoors() {
        return tex -> tex.with("", base -> TextureSet.Builder.block(RL.suffix(base, "_trapdoor")));
    }

    private static Consumer<TextureSet.Builder> doors() {
        return tex -> tex
            .with("top", base -> TextureSet.Builder.block(RL.suffix(base, "_door_top")))
            .with("bottom", base -> TextureSet.Builder.block(RL.suffix(base, "_door_bottom")));
    }

    private static Consumer<TextureSet.Builder> bookshelf() {
        return tex -> tex
            .with("side", base -> TextureSet.Builder.block(RL.withNamespace(RL.suffix(base, "_bookshelf"), SWDM.MOD_ID)))
            .with("end", base -> TextureSet.Builder.block(RL.suffix(base, "_planks")));
    }

    private static Consumer<TextureSet.Builder> beams() {
        return tex -> tex
            .with("beam.down", base -> TextureSet.Builder.block(RL.suffix(base, "_down")))
            .with("beam.lower", base -> TextureSet.Builder.block(RL.suffix(base, "_lower")))
            .with("beam.middle", base -> TextureSet.Builder.block(RL.suffix(base, "_middle")))
            .with("beam.single", base -> TextureSet.Builder.block(RL.suffix(base, "_single")));
    }


    private static Consumer<TextureSet.Builder> variationDoors() {
        BiFunction<String, String, String> transform = (name, type) -> {
            int splitIndex = name.lastIndexOf("door");
            return name.substring(0, splitIndex) + "door_" + type + name.substring(splitIndex + 4);
        };
        return tex -> tex
            .with("top", base -> TextureSet.Builder.block(new ResourceLocation(base.getNamespace(), transform.apply(base.getPath(), "top"))))
            .with("bottom", base -> TextureSet.Builder.block(new ResourceLocation(base.getNamespace(), transform.apply(base.getPath(), "bottom"))));
    }


    private static <T, R extends Object> T cast(R o) {
        return (T) o;
    }
}
