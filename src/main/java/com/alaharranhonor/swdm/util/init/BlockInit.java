package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.*;
import com.alaharranhonor.swdm.util.MultiTable;
import com.google.common.collect.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWDM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWDM.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<? extends T> sup) {
        return register(name, sup, BlockInit::itemDefault);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<? extends T> sup, Function<RegistryObject<T>, Supplier<? extends Item>> itemCreator) {
        RegistryObject<T> ret = registerNoItem(name, sup);
        ITEMS.register(name, itemCreator.apply(ret));
        return ret;
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<? extends T> sup) {
        return BLOCKS.register(name, sup);
    }

    private static Supplier<BlockItem> itemDefault(final RegistryObject<? extends Block> block) {
        return item(block, SWDM.SWDMTAB);
    }

    private static Supplier<BlockItem> item(final RegistryObject<? extends Block> block, final CreativeModeTab itemGroup) {
        return () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup));
    }

    // Stone sets
    // The order is STONE TYPE then STONE.

    // Stone sets 16 colours.
    // Stone set -> List of the 16 colours
    public static final MultiTable<String, String, RegistryObject<Block>> STONE_SET_BLOCKS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<StairBlock>> STONE_SET_STAIRS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<SlabBlock>> STONE_SET_SLABS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<HalfWallBlock>> STONE_SET_WALLS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<StoneButtonBlock>> STONE_SET_BUTTONS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<PressurePlateBlock>> STONE_SET_PRESSURE_PLATES = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<TrapDoorBlock>> STONE_SET_TRAPDOORS = MultiTable.create();

    // Vanilla Wood types
    //public static final List<RegistryObject<Block>> LEAVES_TRAPDOORS = new ArrayList<>();
    //public static final List<RegistryObject<Block>> LADDERS = new ArrayList<>();

    // SSW_SET
    // (SetType, BlockType) -> List<Block>
    public static final MultiTable<String, String, RegistryObject<Block>> SSWT_SET_BLOCKS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<StairBlock>> SSWT_SET_STAIRS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<SlabBlock>> SSWT_SET_SLABS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<HalfWallBlock>> SSWT_SET_WALLS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<TrapDoorBlock>> SSWT_SET_TRAPDOORS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<CarpetBlock>> SSWT_SET_CARPETS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<Block>> SSWT_SET_BEAMS = MultiTable.create();
    public static final MultiTable<String, String, RegistryObject<StainedGlassPaneBlock>> SSWT_SET_GLASS_PANES = MultiTable.create();

    public static final HashMap<String, RegistryObject<CoatedChain>> COATED_CHAINS = new HashMap<>(); // Color -> Block
    public static final HashMap<String, RegistryObject<SandBlock>> SAND_BLOCKS = new HashMap<>(); // Tone -> Block
    public static final HashMap<String, RegistryObject<Block>> SANDSTONE_BLOCKS = new HashMap<>(); // Tone -> Block

    // (LMD, Tone) -> Block
    public static final Table<String, String, RegistryObject<CarpetBlock>> FIBER_CARPETS = HashBasedTable.create();

    static {

        for (String stoneType : SWDM.STONE_TYPES) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("standard").entrySet()) {
                String name = blockType.getKey() + "_" + stoneType;

                Supplier<Block> stairBlock = () -> new Block(blockType.getValue());
                STONE_SET_BLOCKS.putSingle("standard", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                STONE_SET_STAIRS.putSingle("standard", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("standard", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("standard", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("standard", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("standard", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("standard", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }

            for (String lmdType : SWDM.LMD_TYPES) {
                for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {
                    String name = blockType.getKey() + "_" + lmdType + "_" + stoneType;
                    STONE_SET_BLOCKS.putSingle("lmd", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                    STONE_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                    STONE_SET_SLABS.putSingle("lmd", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                    STONE_SET_WALLS.putSingle("lmd", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    STONE_SET_TRAPDOORS.putSingle("lmd", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                    STONE_SET_BUTTONS.putSingle("lmd", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                    STONE_SET_PRESSURE_PLATES.putSingle("lmd", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                }
            }

        }

        for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("standalone").entrySet()) {
            String name = blockType.getKey();
            STONE_SET_STAIRS.putSingle("standalone", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
            STONE_SET_SLABS.putSingle("standalone", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
            STONE_SET_WALLS.putSingle("standalone", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
            STONE_SET_TRAPDOORS.putSingle("standalone", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
            STONE_SET_BUTTONS.putSingle("standalone", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
            STONE_SET_PRESSURE_PLATES.putSingle("standalone", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
        }

        for (String lmdType : SWDM.LMD_TYPES) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd-only").entrySet()) {
                String name = blockType.getKey() + "_" + lmdType;
                STONE_SET_BLOCKS.putSingle("lmd-only", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                STONE_SET_STAIRS.putSingle("lmd-only", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("lmd-only", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("lmd-only", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("lmd-only", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("lmd-only", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("lmd-only", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }

            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {
                String name = blockType.getKey() + "_" + lmdType;
                STONE_SET_BLOCKS.putSingle("lmd", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                STONE_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("lmd", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("lmd", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("lmd", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("lmd", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("lmd", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }
        }

        for (String tone : SWDM.NATURAL_TONES) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("natural_tones").entrySet()) {
                String name = blockType.getKey() + "_" + tone;
                STONE_SET_BLOCKS.putSingle("natural_tones", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                STONE_SET_STAIRS.putSingle("natural_tones", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("natural_tones", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("natural_tones", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("natural_tones", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("natural_tones", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("natural_tones", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }
        }


        for (DyeColor color : DyeColor.values()) {

            COATED_CHAINS.put(color.getName(), register("chain_coated_" + color.getName(), () -> new CoatedChain(Block.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion())));

            for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("color").entrySet()) {
                String name;
                if (blockType.getKey().equals("stained_glass") || blockType.getKey().equals("wool")) {
                    name = color + "_" + blockType.getKey();
                } else {
                    name = blockType.getKey() + "_" + color;
                }

                Supplier<Block> stairBlock;
                if (!blockType.getKey().equals("wool") && !blockType.getKey().equals("stained_glass")) {
                    stairBlock = () -> new Block(blockType.getValue());
                    SSWT_SET_BLOCKS.putSingle("color", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                } else {
                    stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + name));
                }

                SSWT_SET_STAIRS.putSingle("color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                SSWT_SET_SLABS.putSingle("color", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                SSWT_SET_WALLS.putSingle("color", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                SSWT_SET_TRAPDOORS.putSingle("color", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));

                if (blockType.getKey().equals("wool_pastel") || blockType.getKey().equals("wool_tinted")) {
                    SSWT_SET_CARPETS.putSingle("color", blockType.getKey(), register(name + "_carpet", () -> new CarpetBlock(blockType.getValue())));
                }
            }

            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("color").entrySet()) {
                final String name;

                // Vanilla items are reversed to what we use
                if (blockType.getKey().contains("concrete") || blockType.getKey().contains("terracotta")) {
                    name = color.getName() + "_" + blockType.getKey();
                } else {
                    name = blockType.getKey() + "_" + color.getName();
                }

                Supplier<Block> stairBlock;
                // We don't want to add terracotta or concrete as they already exist in vanilla
                if (!blockType.getKey().equals("concrete") && !blockType.getKey().equals("terracotta")) {
                    STONE_SET_BLOCKS.putSingle("color", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                    stairBlock = () -> new Block(blockType.getValue());
                } else {
                    stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + name));
                }

                STONE_SET_STAIRS.putSingle("color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("color", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("color", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("color", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("color", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("color", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }

        }

        for (String color : SWDM.CUSTOM_COLORS) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("color_custom").entrySet()) {
                String name = blockType.getKey() + "_" + color;
                Supplier<Block> stairBlock = () -> new Block(blockType.getValue());
                STONE_SET_BLOCKS.putSingle("color_custom", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                STONE_SET_STAIRS.putSingle("color_custom", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("color_custom", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("color_custom", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("color_custom", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("color_custom", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("color_custom", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }
        }

        // Bug? Using ':' could cause some modded wood types to be registered even when the textures aren't provided.
        // Because mods aren't strictly required to put the full namespace for their wood types.
        WoodType.values().forEach((wood) -> {
            if (!wood.name().equals("crimson") && !wood.name().equals("warped") && !wood.name().contains(":")) { // Vanilla wood types.
                String sanitizedWoodName = wood.name().contains(":") ? wood.name().split(":")[1] : wood.name();

                for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("wv").entrySet()) {
                    String name = sanitizedWoodName + "_" + blockType.getKey();
                    Supplier<Block> stairBlock;
                    // Is this really needed? planks, log and stripped_log are the only variants anyways.
                    if (!blockType.getKey().equals("planks") && !blockType.getKey().equals("log") && !blockType.getKey().equals("stripped_log")) {
                        stairBlock = () -> new Block(blockType.getValue());
                        SSWT_SET_BLOCKS.putSingle("wv", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                    } else {
                        stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + sanitizedWoodName + "_" + blockType.getKey()));
                    }
                    SSWT_SET_STAIRS.putSingle("wv", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_SLABS.putSingle("wv", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("wv", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("wv", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                    SSWT_SET_BEAMS.putSingle("wv", blockType.getKey(), register("beam_" + name, () -> new BeamBlock(blockType.getValue())));
                }

                for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("wv-whitewash").entrySet()) {
                    if (wood.name().contains("swem:")) break;
                    Supplier<Block> stairBlock;
                    if (!blockType.getKey().equals("leaves")) {
                        stairBlock = () -> new Block(blockType.getValue());
                        SSWT_SET_BLOCKS.putSingle("wv-whitewash", blockType.getKey(), register(wood.name() + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                    } else {
                        stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + wood.name() + "_planks"));
                    }
                    SSWT_SET_SLABS.putSingle("wv-whitewash", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_STAIRS.putSingle("wv-whitewash", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("wv-whitewash", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("wv-whitewash", blockType.getKey(), register(wood.name() + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                }
            }

            if (wood.name().equals("crimson") || wood.name().equals("warped") || wood.name().contains("swdm:") || wood.name().contains("swem:")) {
                // Special vanilla wood types, and swdm and swem wood types.
            } else {
                if (!wood.name().contains(":")) { // Vanilla
                    //LEAVES_TRAPDOORS.add(register(wood.name() + "_leaves_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).sound(SoundType.GRASS).noOcclusion())));
                    //LADDERS.add(register(wood.name() + "_ladder", () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER))));
                }
            }
            // All other vanilla wood types.

        });

        SWDM.LMD_TYPES.forEach((lmdType) -> {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("lmd").entrySet()) {
                String name = blockType.getKey() + "_" + lmdType;

                Supplier<Block> stairBlock = () -> new Block(blockType.getValue());
                SSWT_SET_BLOCKS.putSingle("lmd", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                SSWT_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                SSWT_SET_SLABS.putSingle("lmd", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                SSWT_SET_WALLS.putSingle("lmd", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                SSWT_SET_TRAPDOORS.putSingle("lmd", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                if (blockType.getKey().equals("screen")) {
                    SSWT_SET_GLASS_PANES.putSingle("lmd", blockType.getKey(), register(name + "_glass_pane", () -> new StainedGlassPaneBlock(DyeColor.BLACK, blockType.getValue())));
                }
            }

            FIBER_CARPETS.put(lmdType, "sand", register("fiber_carpet_" + lmdType + "_sand", () -> new CarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
            FIBER_CARPETS.put(lmdType, "red_sand", register("fiber_carpet_" + lmdType + "_red_sand", () -> new CarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
        });

        for (String tone : SWDM.NATURAL_TONES) {
            SAND_BLOCKS.put(tone, register("sand_" + tone, () -> new SandBlock(14406560, Block.Properties.copy(Blocks.SAND))));
            SANDSTONE_BLOCKS.put(tone, register("sandstone_" + tone, () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))));
            for (String lmdType : SWDM.LMD_TYPES) {
                FIBER_CARPETS.put(lmdType, tone, register("fiber_carpet_" + lmdType + "_" + tone, () -> new CarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
            }
        }

        for (DyeColor color : DyeColor.values()) {
            for (String lmdType : SWDM.LMD_TYPES) {
                for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("lmd-color").entrySet()) {
                    String name = blockType.getKey() + "_" + lmdType + "_" + color.getName();

                    Supplier<Block> stairBlock = () -> new Block(blockType.getValue());
                    SSWT_SET_BLOCKS.putSingle("lmd-color", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                    SSWT_SET_SLABS.putSingle("lmd-color", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_STAIRS.putSingle("lmd-color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("lmd-color", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("lmd-color", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                }
            }
        }

        // Vanilla sets
        for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("standalone").entrySet()) {
            String name = blockType.getKey();

            Supplier<Block> stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + name));
            SSWT_SET_SLABS.putSingle("standalone", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
            SSWT_SET_STAIRS.putSingle("standalone", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> stairBlock.get().defaultBlockState(), blockType.getValue())));
            SSWT_SET_WALLS.putSingle("standalone", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
            SSWT_SET_TRAPDOORS.putSingle("standalone", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
        }
    }

    public static final RegistryObject<HalfWallBlock> STONE_WALL = register("stone_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> CLAY_BLOCK_LIGHT = register("clay_block_light", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> CLAY_BLOCK_MEDIUM = register("clay_block_medium", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> CLAY_BLOCK_DARK = register("clay_block_dark", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<SlabBlock> GRASS_SLAB = register("grass_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<SlabBlock> DIRT_SLAB = register("dirt_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.DIRT)));

    //Items
    public static final RegistryObject<Item> CHANGE_TOOL = ITEMS.register("change_tool", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));

    //public static final RegistryObject<Item> ACACIA_STICK = ITEMS.register("acacia_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> BIRCH_STICK = ITEMS.register("birch_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> DARK_OAK_STICK = ITEMS.register("dark_oak_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> JUNGLE_STICK = ITEMS.register("jungle_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> OAK_STICK = ITEMS.register("oak_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> SPRUCE_STICK = ITEMS.register("spruce_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    //public static final RegistryObject<Item> THATCH = ITEMS.register("thatch", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));

    // Sign Items
    //public static final RegistryObject<SignItem> THATCH_SIGN_ITEM = ITEMS.register("thatch_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(SWDM.SWDMTAB), THATCH_SIGN.get(), THATCH_WALL_SIGN.get()));
    //public static final RegistryObject<SignItem> BAMBOO_SIGN_ITEM = ITEMS.register("bamboo_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(SWDM.SWDMTAB), BAMBOO_SIGN.get(), BAMBOO_WALL_SIGN.get()));

    //public static final RegistryObject<Block> TERRACOTTA_BUTTON = register("terracotta_button", () -> new StoneButtonBlock(Block.Properties.copy(Blocks.TERRACOTTA)));
    //public static final RegistryObject<Block> TERRACOTTA_SLAB = register("terracotta_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.TERRACOTTA)));
    //public static final RegistryObject<Block> TERRACOTTA_PRESSURE_PLATE = register("terracotta_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.TERRACOTTA)));
    //public static final RegistryObject<Block> TERRACOTTA_STAIRS = register("terracotta_stairs", () -> new StairBlock(() -> Blocks.TERRACOTTA.defaultBlockState(), Block.Properties.copy(Blocks.TERRACOTTA)));
    //public static final RegistryObject<Block> TERRACOTTA_WALL = register("terracotta_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.TERRACOTTA)));
    //public static final RegistryObject<CarpetBlock> FIBER_CARPET_RED_SAND = register("fiber_carpet_red_sand", () -> new CarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    //public static final RegistryObject<CarpetBlock> FIBER_CARPET_SAND = register("fiber_carpet_sand", () -> new CarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    //public static final RegistryObject<Block> GLASS_STAIRS = register("glass_stairs", () -> new StairBlock(Blocks.GLASS.defaultBlockState(), Block.Properties.copy(Blocks.GLASS)));
    //public static final RegistryObject<Block> GLASS_SLAB = register("glass_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GLASS)));
    //public static final RegistryObject<Block> GLASS_WALL = register("glass_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.GLASS)));
    //public static final RegistryObject<Block> THATCH_BLOCK = register("thatch_block", () -> new RotatedPillarBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F))); // .harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_BUTTON = register("thatch_button", () -> new WoodButtonBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F))); //.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_FENCE = register("thatch_fence", () -> new FenceBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F))); //.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_FENCE_GATE = register("thatch_fence_gate", () -> new FenceGateBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F))); //.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_DOOR = register("thatch_door", () -> new DoorBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F).noOcclusion())); //.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_LADDER = register("thatch_ladder", () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER)));//.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_PRESSURE_PLATE = register("thatch_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F))); //.harvestTool(ToolType.HOE)
    //public static final RegistryObject<Block> THATCH_SLAB = register("thatch_slab", () -> new SlabBlock(Block.Properties.copy(BlockInit.THATCH_BLOCK.get())));
    //public static final RegistryObject<Block> THATCH_STAIRS = register("thatch_stairs", () -> new StairBlock(() -> BlockInit.THATCH_BLOCK.get().defaultBlockState(), Block.Properties.copy(BlockInit.THATCH_BLOCK.get())));
    //public static final RegistryObject<Block> THATCH_TRAPDOOR = register("thatch_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F).noOcclusion()));
    //public static final RegistryObject<ModdedStandingSignBlock> THATCH_SIGN = registerNoItem("thatch_sign", () -> new ModdedStandingSignBlock(Block.Properties.of(Material.PLANT).noCollission().strength(0.5F).sound(SoundType.WET_GRASS), SWDM.THATCH_WT));
    //public static final RegistryObject<ModdedWallSignBlock> THATCH_WALL_SIGN = registerNoItem("thatch_wall_sign", () -> new ModdedWallSignBlock(Block.Properties.of(Material.PLANT).noCollission().strength(0.5F).sound(SoundType.WET_GRASS).lootFrom(THATCH_SIGN), SWDM.THATCH_WT));
    //public static final RegistryObject<Block> BAMBOO_BLOCK = register("bamboo_block", () -> new RotatedPillarBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F)));
    //public static final RegistryObject<Block> BAMBOO_BUTTON = register("bamboo_button", () -> new WoodButtonBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.5F, 0.5F)));
    //public static final RegistryObject<Block> BAMBOO_DOOR = register("bamboo_door", () -> new DoorBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F).noOcclusion()));
    //public static final RegistryObject<Block> BAMBOO_FENCE = register("bamboo_fence", () -> new FenceBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F)));
    //public static final RegistryObject<Block> BAMBOO_FENCE_GATE = register("bamboo_fence_gate", () -> new FenceGateBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F)));
    //public static final RegistryObject<Block> BAMBOO_LADDER = register("bamboo_ladder", () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
    //public static final RegistryObject<Block> BAMBOO_PRESSURE_PLATE = register("bamboo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.5F, 0.5F)));
    //public static final RegistryObject<Block> BAMBOO_SLAB = register("bamboo_slab", () -> new SlabBlock(Block.Properties.copy(BlockInit.BAMBOO_BLOCK.get())));
    //public static final RegistryObject<Block> BAMBOO_STAIRS = register("bamboo_stairs", () -> new StairBlock(() -> BlockInit.BAMBOO_BLOCK.get().defaultBlockState(), Block.Properties.copy(BlockInit.BAMBOO_BLOCK.get())));
    //public static final RegistryObject<Block> BAMBOO_TRAPDOOR = register("bamboo_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F).noOcclusion()));
    //public static final RegistryObject<ModdedStandingSignBlock> BAMBOO_SIGN = registerNoItem("bamboo_sign", () -> new ModdedStandingSignBlock(Block.Properties.of(Material.BAMBOO).noCollission().strength(0.5F).sound(SoundType.BAMBOO_SAPLING), SWDM.BAMBOO_WT));
    //public static final RegistryObject<ModdedWallSignBlock> BAMBOO_WALL_SIGN = registerNoItem("bamboo_wall_sign", () -> new ModdedWallSignBlock(Block.Properties.of(Material.BAMBOO).noCollission().strength(0.5F).sound(SoundType.BAMBOO_SAPLING).lootFrom(() -> BAMBOO_SIGN.get()), SWDM.BAMBOO_WT));
    //public static final RegistryObject<Block> ACACIA_PLANK_WALL = register("acacia_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    //public static final RegistryObject<Block> BIRCH_PLANK_WALL = register("birch_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    //public static final RegistryObject<Block> DARK_OAK_PLANK_WALL = register("dark_oak_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    //public static final RegistryObject<Block> JUNGLE_PLANK_WALL = register("jungle_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    //public static final RegistryObject<Block> OAK_PLANK_WALL = register("oak_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    //public static final RegistryObject<Block> SPRUCE_PLANK_WALL = register("spruce_plank_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.SPRUCE_PLANKS)));

    //public static final RegistryObject<Block> ACACIA_TRAPDOOR_BIRCH = register("acacia_trapdoor_birch", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    //public static final RegistryObject<Block> ACACIA_TRAPDOOR_DARK_OAK = register("acacia_trapdoor_dark_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> ACACIA_TRAPDOOR_JUNGLE = register("acacia_trapdoor_jungle", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    //public static final RegistryObject<Block> ACACIA_TRAPDOOR_OAK = register("acacia_trapdoor_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> ACACIA_TRAPDOOR_SPRUCE = register("acacia_trapdoor_spruce", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    //public static final RegistryObject<Block> BIRCH_TRAPDOOR_ACACIA = register("birch_trapdoor_acacia", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    //public static final RegistryObject<Block> BIRCH_TRAPDOOR_DARK_OAK = register("birch_trapdoor_dark_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> BIRCH_TRAPDOOR_JUNGLE = register("birch_trapdoor_jungle", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    //public static final RegistryObject<Block> BIRCH_TRAPDOOR_OAK = register("birch_trapdoor_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> BIRCH_TRAPDOOR_SPRUCE = register("birch_trapdoor_spruce", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    //public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_ACACIA = register("dark_oak_trapdoor_acacia", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    //public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_BIRCH = register("dark_oak_trapdoor_birch", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    //public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_JUNGLE = register("dark_oak_trapdoor_jungle", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    //public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_OAK = register("dark_oak_trapdoor_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_SPRUCE = register("dark_oak_trapdoor_spruce", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    //public static final RegistryObject<Block> JUNGLE_TRAPDOOR_ACACIA = register("jungle_trapdoor_acacia", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    //public static final RegistryObject<Block> JUNGLE_TRAPDOOR_BIRCH = register("jungle_trapdoor_birch", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    //public static final RegistryObject<Block> JUNGLE_TRAPDOOR_DARK_OAK = register("jungle_trapdoor_dark_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> JUNGLE_TRAPDOOR_OAK = register("jungle_trapdoor_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> JUNGLE_TRAPDOOR_SPRUCE = register("jungle_trapdoor_spruce", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    //public static final RegistryObject<Block> OAK_TRAPDOOR_ACACIA = register("oak_trapdoor_acacia", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    //public static final RegistryObject<Block> OAK_TRAPDOOR_BIRCH = register("oak_trapdoor_birch", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    //public static final RegistryObject<Block> OAK_TRAPDOOR_DARK_OAK = register("oak_trapdoor_dark_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> OAK_TRAPDOOR_JUNGLE = register("oak_trapdoor_jungle", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    //public static final RegistryObject<Block> OAK_TRAPDOOR_SPRUCE = register("oak_trapdoor_spruce", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    //public static final RegistryObject<Block> SPRUCE_TRAPDOOR_ACACIA = register("spruce_trapdoor_acacia", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    //public static final RegistryObject<Block> SPRUCE_TRAPDOOR_BIRCH = register("spruce_trapdoor_birch", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    //public static final RegistryObject<Block> SPRUCE_TRAPDOOR_DARK_OAK = register("spruce_trapdoor_dark_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    //public static final RegistryObject<Block> SPRUCE_TRAPDOOR_JUNGLE = register("spruce_trapdoor_jungle", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    //public static final RegistryObject<Block> SPRUCE_TRAPDOOR_OAK = register("spruce_trapdoor_oak", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
}
