package com.alaharranhonor.swdm.old.util.init;

import com.alaharranhonor.swdm.old.SWDM;
import com.alaharranhonor.swdm.old.block.*;
import com.alaharranhonor.swdm.old.util.MultiTable;
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

    public static final HashMap<String, RegistryObject<TwoWayBlock>> COATED_CHAINS = new HashMap<>(); // Color -> Block
    public static final HashMap<String, RegistryObject<TwoWayBlock>> PYLONS = new HashMap<>(); // Color -> Block
    public static final HashMap<String, RegistryObject<SandBlock>> SAND_BLOCKS = new HashMap<>(); // Tone -> Block
    public static final HashMap<String, RegistryObject<Block>> SANDSTONE_BLOCKS = new HashMap<>(); // Tone -> Block

    // (LMD, Tone) -> Block
    public static final Table<String, String, RegistryObject<CarpetBlock>> FIBER_CARPETS = HashBasedTable.create();

    static {

        for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("standard").entrySet()) {
            String name = blockType.getKey();

            Supplier<Block> baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
            if (name.contains("polished")) {
                STONE_SET_WALLS.putSingle("standard", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
            }

            STONE_SET_TRAPDOORS.putSingle("standard", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
            STONE_SET_BUTTONS.putSingle("standard", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
            STONE_SET_PRESSURE_PLATES.putSingle("standard", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
        }

        for (String stoneType : SWDM.STONE_TYPES) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("standard").entrySet()) {
                String name = blockType.getKey() + "_" + stoneType;

                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                STONE_SET_BLOCKS.putSingle("standard", blockType.getKey(), baseBlock);
                STONE_SET_STAIRS.putSingle("standard", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("standard", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("standard", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("standard", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("standard", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("standard", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }

            for (String lmdType : SWDM.LMD_TYPES) {
                for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {
                    String name = blockType.getKey() + "_" + lmdType + "_" + stoneType;
                    RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                    STONE_SET_BLOCKS.putSingle("lmd", blockType.getKey(), baseBlock);
                    STONE_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
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
            final Supplier<Block> baseBlock;
            if (ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(name))) {
                baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
            } else {
                baseBlock = STONE_SET_BLOCKS.putSingle("standalone", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
            }
            STONE_SET_STAIRS.putSingle("standalone", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
            STONE_SET_SLABS.putSingle("standalone", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
            STONE_SET_WALLS.putSingle("standalone", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
            STONE_SET_TRAPDOORS.putSingle("standalone", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
            STONE_SET_BUTTONS.putSingle("standalone", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
            STONE_SET_PRESSURE_PLATES.putSingle("standalone", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
        }

        for (String lmdType : SWDM.LMD_TYPES) {
            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd-only").entrySet()) {
                String name = blockType.getKey() + "_" + lmdType;
                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                STONE_SET_BLOCKS.putSingle("lmd-only", blockType.getKey(), baseBlock);
                STONE_SET_STAIRS.putSingle("lmd-only", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("lmd-only", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("lmd-only", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("lmd-only", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("lmd-only", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("lmd-only", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }

            for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {
                String name = blockType.getKey() + "_" + lmdType;
                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                STONE_SET_BLOCKS.putSingle("lmd", blockType.getKey(), baseBlock);
                STONE_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
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
                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                STONE_SET_BLOCKS.putSingle("natural_tones", blockType.getKey(), baseBlock);
                STONE_SET_STAIRS.putSingle("natural_tones", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                STONE_SET_SLABS.putSingle("natural_tones", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                STONE_SET_WALLS.putSingle("natural_tones", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                STONE_SET_TRAPDOORS.putSingle("natural_tones", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                STONE_SET_BUTTONS.putSingle("natural_tones", blockType.getKey(), register(name + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                STONE_SET_PRESSURE_PLATES.putSingle("natural_tones", blockType.getKey(), register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
            }
        }


        for (DyeColor color : DyeColor.values()) {

            COATED_CHAINS.put(color.getName(), register("chain_coated_" + color.getName(), () -> new TwoWayBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion())));
            PYLONS.put(color.getName(), register("pylon_" + color.getName(), () -> new TwoWayBlock(Block.Properties.of(Material.STONE, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 15))));

            for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("color").entrySet()) {
                String name;
                if (blockType.getKey().equals("stained_glass") || blockType.getKey().equals("wool")) {
                    name = color + "_" + blockType.getKey();
                } else {
                    name = blockType.getKey() + "_" + color;
                }

                Supplier<Block> baseBlock;
                if (!blockType.getKey().equals("wool") && !blockType.getKey().equals("stained_glass")) {
                    baseBlock = SSWT_SET_BLOCKS.putSingle("color", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                } else {
                    baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
                }

                SSWT_SET_STAIRS.putSingle("color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
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

                Supplier<Block> baseBlock;
                // We don't want to add terracotta or concrete as they already exist in vanilla
                if (!blockType.getKey().equals("concrete") && !blockType.getKey().equals("terracotta")) {
                    baseBlock = STONE_SET_BLOCKS.putSingle("color", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                } else {
                    baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
                }

                STONE_SET_STAIRS.putSingle("color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
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
                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                STONE_SET_BLOCKS.putSingle("color_custom", blockType.getKey(), baseBlock);
                STONE_SET_STAIRS.putSingle("color_custom", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
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
                    Supplier<Block> baseBlock;
                    // Is this really needed? planks, log and stripped_log are the only variants anyways.
                    if (!blockType.getKey().equals("planks") && !blockType.getKey().equals("log") && !blockType.getKey().equals("stripped_log")) {
                        baseBlock = SSWT_SET_BLOCKS.putSingle("wv", blockType.getKey(), register(name, () -> new Block(blockType.getValue())));
                    } else {
                        baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(sanitizedWoodName + "_" + blockType.getKey()));
                    }
                    SSWT_SET_STAIRS.putSingle("wv", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_SLABS.putSingle("wv", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("wv", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("wv", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                    SSWT_SET_BEAMS.putSingle("wv", blockType.getKey(), register("beam_" + name, () -> new BeamBlock(blockType.getValue())));
                }

                for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("wv-extra").entrySet()) {
                    if (wood.name().contains("swem:")) break;
                    Supplier<Block> baseBlock;
                    if (!blockType.getKey().equals("leaves")) {
                        baseBlock = SSWT_SET_BLOCKS.putSingle("wv-extra", blockType.getKey(), register(wood.name() + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                    } else {
                        baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(wood.name() + "_planks"));
                    }
                    SSWT_SET_SLABS.putSingle("wv-extra", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_STAIRS.putSingle("wv-extra", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("wv-extra", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("wv-extra", blockType.getKey(), register(wood.name() + "_" + blockType.getKey() + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
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

                RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                SSWT_SET_BLOCKS.putSingle("lmd", blockType.getKey(), baseBlock);
                SSWT_SET_STAIRS.putSingle("lmd", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                SSWT_SET_SLABS.putSingle("lmd", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                SSWT_SET_WALLS.putSingle("lmd", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                SSWT_SET_TRAPDOORS.putSingle("lmd", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                if (blockType.getKey().equals("screen")) {
                    SSWT_SET_GLASS_PANES.putSingle("lmd", blockType.getKey(), register(name + "_glass_pane", () -> new StainedGlassPaneBlock(DyeColor.BLACK, blockType.getValue())));
                }
            }

            FIBER_CARPETS.put(lmdType, "sand", register("fiber_carpet_" + lmdType + "_sand", () -> new HorizontalCarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
            FIBER_CARPETS.put(lmdType, "red_sand", register("fiber_carpet_" + lmdType + "_red_sand", () -> new HorizontalCarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
        });

        for (String tone : SWDM.NATURAL_TONES) {
            SAND_BLOCKS.put(tone, register("sand_" + tone, () -> new SandBlock(14406560, Block.Properties.copy(Blocks.SAND))));
            SANDSTONE_BLOCKS.put(tone, register("sandstone_" + tone, () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))));
            for (String lmdType : SWDM.LMD_TYPES) {
                FIBER_CARPETS.put(lmdType, tone, register("fiber_carpet_" + lmdType + "_" + tone, () -> new HorizontalCarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
            }
        }

        for (DyeColor color : DyeColor.values()) {
            for (String lmdType : SWDM.LMD_TYPES) {
                for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("lmd-color").entrySet()) {
                    String name = blockType.getKey() + "_" + lmdType + "_" + color.getName();

                    RegistryObject<Block> baseBlock = register(name, () -> new Block(blockType.getValue()));
                    SSWT_SET_BLOCKS.putSingle("lmd-color", blockType.getKey(), baseBlock);
                    SSWT_SET_SLABS.putSingle("lmd-color", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
                    SSWT_SET_STAIRS.putSingle("lmd-color", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
                    SSWT_SET_WALLS.putSingle("lmd-color", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
                    SSWT_SET_TRAPDOORS.putSingle("lmd-color", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
                }
            }
        }

        // Vanilla sets
        for (Map.Entry<String, Block.Properties> blockType : SWDM.SSWT_SETS.get("standalone").entrySet()) {
            String name = blockType.getKey();

            Supplier<Block> baseBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
            SSWT_SET_SLABS.putSingle("standalone", blockType.getKey(), register(name + "_slab", () -> new SlabBlock(blockType.getValue())));
            SSWT_SET_STAIRS.putSingle("standalone", blockType.getKey(), register(name + "_stairs", () -> new StairBlock(() -> baseBlock.get().defaultBlockState(), blockType.getValue())));
            SSWT_SET_WALLS.putSingle("standalone", blockType.getKey(), register(name + "_wall", () -> new HalfWallBlock(blockType.getValue())));
            SSWT_SET_TRAPDOORS.putSingle("standalone", blockType.getKey(), register(name + "_trapdoor", () -> new TrapDoorBlock(blockType.getValue())));
        }
    }

    public static final RegistryObject<HalfWallBlock> STONE_WALL = register("stone_wall", () -> new HalfWallBlock(Block.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> CLAY_BLOCK_LIGHT = register("clay_block_light", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> CLAY_BLOCK_MEDIUM = register("clay_block_medium", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> CLAY_BLOCK_DARK = register("clay_block_dark", () -> new Block(Block.Properties.copy(Blocks.CLAY)));

    //Items
    public static final RegistryObject<Item> CHANGE_TOOL = ITEMS.register("change_tool", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
}
