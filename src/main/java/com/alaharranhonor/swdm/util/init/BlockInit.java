package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.blocks.BeamBlock;
import com.alaharranhonor.swdm.blocks.ClockBlock;
import com.alaharranhonor.swdm.blocks.ModdedStandingSignBlock;
import com.alaharranhonor.swdm.blocks.ModdedWallSignBlock;
import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static Supplier<BlockItem> item(final RegistryObject<? extends Block> block, final ItemGroup itemGroup) {
        return () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup));
    }

    //Blocks

    // 16 Dye Colour blocks
    public static final List<RegistryObject<StairsBlock>> WOOL_STAIRS = new ArrayList<>();
    public static final List<RegistryObject<Block>> WOOL_SLABS = new ArrayList<>();
    public static final List<RegistryObject<Block>> WOOL_WALL = new ArrayList<>();
    public static final List<RegistryObject<Block>> PASTEL_WOOL = new ArrayList<>();
    public static final List<RegistryObject<StairsBlock>> PASTEL_WOOL_STAIRS = new ArrayList<>();
    public static final List<RegistryObject<Block>> PASTEL_WOOL_SLABS = new ArrayList<>();
    public static final List<RegistryObject<Block>> PASTEL_WOOL_WALL = new ArrayList<>();
    public static final List<RegistryObject<Block>> PASTEL_WOOL_CARPETS = new ArrayList<>();
    public static final List<RegistryObject<Block>> STAINED_GLASS_STAIRS = new ArrayList<>();
    public static final List<RegistryObject<Block>> STAINED_GLASS_SLABS = new ArrayList<>();
    public static final List<RegistryObject<Block>> STAINED_GLASS_WALLS = new ArrayList<>();

    // Stone sets
    // The order is STONE TYPE then STONE.
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_WALLS = new HashMap<>();
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_BLOCKS = new HashMap<>();
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_BUTTONS = new HashMap<>();
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_PRESSURE_PLATES = new HashMap<>();
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_SLAB = new HashMap<>();
    public static final Map<String, Map<String, RegistryObject<Block>>> STONE_STAIRS = new HashMap<>();

    // Stone sets 16 colours.
    // Stonem set -> List of the 16 colours
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_BLOCKS = new HashMap<>();
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_STAIRS = new HashMap<>();
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_SLABS = new HashMap<>();
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_BUTTON = new HashMap<>();
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_PRESSURE_PLATE = new HashMap<>();
    public static final Map<String, List<RegistryObject<Block>>> STONE_SET_WALL = new HashMap<>();


    // Vanilla Wood types
    public static final List<RegistryObject<Block>> LEAVES_STAIRS = new ArrayList<>();
    public static final List<RegistryObject<Block>> LEAVES_SLABS = new ArrayList<>();
    public static final List<RegistryObject<Block>> LEAVES_WALLS = new ArrayList<>();
    public static final List<RegistryObject<Block>> LEAVES_TRAPDOORS = new ArrayList<>();
    public static final List<RegistryObject<Block>> LADDERS = new ArrayList<>();


    // SSW_SET
    // Type of set -> Map<BlockTypes> -> List<Blocks>'
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_BLOCKS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_STAIRS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_SLABS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_WALLS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_CARPETS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_BEAMS = new HashMap<>();




    static {

        for (String stone_type : SWDM.STONE_TYPES) {
            for (String stone : Stream.of(SWDM.STONE_SETS.get("no_default"), SWDM.STONE_SETS.get("with_default")).flatMap(Collection::stream).collect(Collectors.toList())) {
                Map<String, RegistryObject<Block>> stone_walls = STONE_WALLS.getOrDefault(stone_type, new HashMap<>());
                stone_walls.put(stone, register(stone_type+"_"+stone+"_wall", () -> new WallBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_WALLS.put(stone_type, stone_walls);

                Map<String, RegistryObject<Block>> stone_blocks = STONE_BLOCKS.getOrDefault(stone_type, new HashMap<>());
                stone_blocks.put(stone, register(stone_type+"_"+stone, () -> new Block(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_BLOCKS.put(stone_type, stone_blocks);

                Map<String, RegistryObject<Block>> stone_buttons = STONE_BUTTONS.getOrDefault(stone_type, new HashMap<>());
                stone_buttons.put(stone, register(stone_type+"_"+stone+"_button", () -> new StoneButtonBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_BUTTONS.put(stone_type, stone_buttons);

                Map<String, RegistryObject<Block>> stone_pressure_plates = STONE_PRESSURE_PLATES.getOrDefault(stone_type, new HashMap<>());
                stone_pressure_plates.put(stone, register(stone_type+"_"+stone+"_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_PRESSURE_PLATES.put(stone_type, stone_pressure_plates);

                Map<String, RegistryObject<Block>> stone_slabs = STONE_SLAB.getOrDefault(stone_type, new HashMap<>());
                stone_slabs.put(stone, register(stone_type+"_"+stone+"_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_SLAB.put(stone_type, stone_slabs);

                Map<String, RegistryObject<Block>> stone_stairs = STONE_STAIRS.getOrDefault(stone_type, new HashMap<>());
                stone_stairs.put(stone, register(stone_type+"_"+stone+"_stairs", () -> new StairsBlock(() -> STONE_BLOCKS.get(stone_type).get(stone).get().defaultBlockState(), AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops())));
                STONE_STAIRS.put(stone_type, stone_stairs);
            }
        }

        for (String stone : SWDM.STONE_SETS.get("with_default")) {
            List<RegistryObject<Block>> ssSlabs = STONE_SET_SLABS.getOrDefault(stone, new ArrayList<>());
            ssSlabs.add(register(stone + "_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_SLABS.put(stone, ssSlabs);


            List<RegistryObject<Block>> ssBlocks = STONE_SET_BLOCKS.getOrDefault(stone, new ArrayList<>());
            ssBlocks.add(register(stone, () -> new Block(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_BLOCKS.put(stone, ssBlocks);
            System.out.println("Registered " + STONE_SET_BLOCKS);

            List<RegistryObject<Block>> ssButtons = STONE_SET_BUTTON.getOrDefault(stone, new ArrayList<>());
            ssButtons.add(register(stone + "_button", () -> new StoneButtonBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_BUTTON.put(stone, ssButtons);

            List<RegistryObject<Block>> ssWalls = STONE_SET_WALL.getOrDefault(stone, new ArrayList<>());
            ssWalls.add(register(stone + "_wall", () -> new WallBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_WALL.put(stone, ssWalls);

            List<RegistryObject<Block>> ssPressurePlate = STONE_SET_PRESSURE_PLATE.getOrDefault(stone, new ArrayList<>());
            ssPressurePlate.add(register(stone + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_PRESSURE_PLATE.put(stone, ssPressurePlate);

            Block stairBlock = STONE_SET_BLOCKS.get(stone).get(0).get();
            List<RegistryObject<Block>> ssStairs = STONE_SET_STAIRS.getOrDefault(stone, new ArrayList<>());
            ssStairs.add(register(stone + "_stairs", () -> new StairsBlock(() -> stairBlock.defaultBlockState(), Block.Properties.copy(Blocks.CYAN_CONCRETE))));
            STONE_SET_STAIRS.put(stone, ssStairs);
        }



        for (DyeColor color : DyeColor.values()) {

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("color").entrySet()) {
                Block stairBlock = null;
                if (!blockType.getKey().equals("wool") && !blockType.getKey().equals("stained_glass")) {
                    List<RegistryObject<Block>> sswTypeBlocks = SSW_SET_BLOCKS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairBlock = new Block(blockType.getValue());
                    sswTypeBlocks.add(register(color.getName()+"_"+blockType, () -> new Block(blockType.getValue())));
                    SSW_SET_BLOCKS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeBlocks);
                }

                if (stairBlock == null) {
                    stairBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:"+color.getName()+"_"+blockType.getKey()));
                }

                List<RegistryObject<Block>> sswTypeSlabs = SSW_SET_SLABS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeSlabs.add(register(color.getName() + "_" + blockType + "_slab", () -> new SlabBlock(blockType.getValue())));
                SSW_SET_SLABS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeSlabs);

                List<RegistryObject<Block>> sswTypeWalls = SSW_SET_WALLS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeWalls.add(register(color.getName() + "_" + blockType + "_wall", () -> new WallBlock(blockType.getValue())));
                SSW_SET_WALLS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeWalls);
            }

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("color_carpet").entrySet()) {
                List<RegistryObject<Block>> sswTypeCarpets = SSW_SET_CARPETS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeCarpets.add(register(color.getName() + "_" + blockType + "_carpet", () -> new CarpetBlock(color, blockType.getValue())));
                SSW_SET_CARPETS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeCarpets);
            }



            for (String stoneSetWithColor : SWDM.STONE_SETS.get("color")) {
                List<RegistryObject<Block>> ssSlabs = STONE_SET_SLABS.getOrDefault(stoneSetWithColor, new ArrayList<>());
                ssSlabs.add(register(color.getName() + "_" + stoneSetWithColor + "_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                STONE_SET_SLABS.put(stoneSetWithColor, ssSlabs);

                if (!stoneSetWithColor.equals("concrete") || !stoneSetWithColor.equals("terracotta")) {
                    List<RegistryObject<Block>> ssBlocks = STONE_SET_BLOCKS.getOrDefault(stoneSetWithColor, new ArrayList<>());
                    ssBlocks.add(register(color.getName() + "_" + stoneSetWithColor, () -> new Block(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                    STONE_SET_BLOCKS.put(stoneSetWithColor, ssBlocks);
                }

                Block stairBlock = stoneSetWithColor.equals("concrete")
                    ? ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:"+color.getName()+"_concrete"))
                    : stoneSetWithColor.equals("terracotta")
                    ? ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:"+color.getName()+"_terracotta"))
                    : STONE_SET_BLOCKS.get(stoneSetWithColor).get(color.getId()).get();
                List<RegistryObject<Block>> ssStairs = STONE_SET_STAIRS.getOrDefault(stoneSetWithColor, new ArrayList<>());
                ssStairs.add(register(color.getName() + "_" + stoneSetWithColor + "_stairs", () -> new StairsBlock(() -> stairBlock.defaultBlockState(), Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                STONE_SET_STAIRS.put(stoneSetWithColor, ssStairs);

                List<RegistryObject<Block>> ssButtons = STONE_SET_BUTTON.getOrDefault(stoneSetWithColor, new ArrayList<>());
                ssButtons.add(register(color.getName() + "_" + stoneSetWithColor + "_button", () -> new StoneButtonBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                STONE_SET_BUTTON.put(stoneSetWithColor, ssButtons);

                List<RegistryObject<Block>> ssWalls = STONE_SET_WALL.getOrDefault(stoneSetWithColor, new ArrayList<>());
                ssWalls.add(register(color.getName() + "_" + stoneSetWithColor + "_wall", () -> new WallBlock(Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                STONE_SET_WALL.put(stoneSetWithColor, ssWalls);

                List<RegistryObject<Block>> ssPressurePlate = STONE_SET_PRESSURE_PLATE.getOrDefault(stoneSetWithColor, new ArrayList<>());
                ssPressurePlate.add(register(color.getName() + "_" + stoneSetWithColor + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.CYAN_CONCRETE))));
                STONE_SET_PRESSURE_PLATE.put(stoneSetWithColor, ssPressurePlate);
            }


       }

        WoodType.values().forEach((wood) -> {
            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("wv").entrySet()) {
                if (wood.name().equals("crimson") || wood.name().equals("warped") || wood.name().contains("swdm:")) {
                    // Do nothing for these wood types.
                }

                Block stairBlock = null;
                if (!blockType.getKey().equals("wool") && !blockType.getKey().equals("stained_glass")) {
                    List<RegistryObject<Block>> sswTypeBlocks = SSW_SET_BLOCKS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairBlock = new Block(blockType.getValue());
                    sswTypeBlocks.add(register(wood.name()+"_"+blockType, () -> new Block(blockType.getValue())));
                    SSW_SET_BLOCKS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeBlocks);
                }

                if (stairBlock == null) {
                    stairBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:"+wood.name()+"_"+blockType.getKey()));
                }

                List<RegistryObject<Block>> sswTypeSlabs = SSW_SET_SLABS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeSlabs.add(register(wood.name() + "_" + blockType + "_slab", () -> new SlabBlock(blockType.getValue())));
                SSW_SET_SLABS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeSlabs);

                List<RegistryObject<Block>> sswTypeStairs = SSW_SET_STAIRS.getOrDefault("stairs", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                Block finalStairBlock = stairBlock;
                sswTypeStairs.add(register(wood.name() + "_" + blockType + "_stairs", () -> new StairsBlock(() -> finalStairBlock.defaultBlockState(), blockType.getValue())));
                SSW_SET_STAIRS.getOrDefault("stairs", new HashMap<>()).put(blockType.getKey(), sswTypeStairs);

                List<RegistryObject<Block>> sswTypeWalls = SSW_SET_WALLS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeWalls.add(register(wood.name() + "_" + blockType + "_wall", () -> new WallBlock(blockType.getValue())));
                SSW_SET_WALLS.getOrDefault("color", new HashMap<>()).put(blockType.getKey(), sswTypeWalls);
            }

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("wv_beam").entrySet()) {
                List<RegistryObject<Block>> sswTypeBeams = SSW_SET_BEAMS.getOrDefault("color", new HashMap<>()).getOrDefault(blockType.getKey(), new ArrayList<>());
                sswTypeBeams.add(register(wood.name() + "_" + blockType + "_beam", () -> new BeamBlock(blockType.getValue())));
                SSW_SET_BEAMS.getOrDefault("wv", new HashMap<>()).put(blockType.getKey(), sswTypeBeams);
            }


                // All other vanilla wood types.
                LEAVES_STAIRS.add(register(wood.name() + "_leaves_stairs", () -> new StairsBlock(() -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", wood.name() + "_planks")).defaultBlockState(), Block.Properties.of(Material.LEAVES).strength(0.2F).sound(SoundType.GRASS).noOcclusion())));
                LEAVES_SLABS.add(register(wood.name() + "_leaves_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES))));
                LEAVES_WALLS.add(register(wood.name() + "_leaves_wall", () -> new WallBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES))));
                LEAVES_TRAPDOORS.add(register(wood.name() + "_leaves_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).sound(SoundType.GRASS).noOcclusion())));
                LADDERS.add(register(wood.name() + "_ladder", () -> new LadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER))));

        });
    }

    public static final RegistryObject<Block> STONE_WALL = register("stone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GLASS_STAIRS = register("glass_stairs", () -> new StairsBlock(Blocks.GLASS.defaultBlockState(), Block.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> GLASS_SLAB = register("glass_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> GLASS_WALL = register("glass_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> SAND_BLACK = register("sand_black", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_BLUE_GRAY = register("sand_blue_gray", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_BROWN = register("sand_brown", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_DARK_BROWN = register("sand_dark_brown", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_DUSTED_GRAY = register("sand_dusted_gray", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_GOLDEN = register("sand_golden", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_GRAY = register("sand_gray", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_LIGHT_GRAY = register("sand_light_gray", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_MUTED_BROWN = register("sand_muted_brown", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_VIVID_RED = register("sand_vivid_red", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SAND_WHITE = register("sand_white", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> SANDSTONE_BLACK = register("sandstone_black", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_BLUE_GRAY = register("sandstone_blue_gray", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_BROWN = register("sandstone_brown", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_DARK_BROWN = register("sandstone_dark_brown", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_DUSTED_GRAY = register("sandstone_dusted_gray", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_GOLDEN = register("sandstone_golden", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_GRAY = register("sandstone_gray", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_LIGHT_GRAY = register("sandstone_light_gray", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_MUTED_BROWN = register("sandstone_muted_brown", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_VIVID_RED = register("sandstone_vivid_red", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> SANDSTONE_WHITE = register("sandstone_white", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> THATCH_BLOCK = register("thatch_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_BUTTON = register("thatch_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_FENCE = register("thatch_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F,0.5F).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_FENCE_GATE = register("thatch_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F,0.5F).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_DOOR = register("thatch_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F, 0.5F).harvestTool(ToolType.HOE).noOcclusion()));
    public static final RegistryObject<Block> THATCH_LADDER = register("thatch_ladder", () -> new LadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_PRESSURE_PLATE = register("thatch_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F,0.5F).harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> THATCH_SLAB = register("thatch_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BlockInit.THATCH_BLOCK.get())));
    public static final RegistryObject<Block> THATCH_STAIRS = register("thatch_stairs", () -> new StairsBlock(() -> BlockInit.THATCH_BLOCK.get().defaultBlockState() , AbstractBlock.Properties.copy(BlockInit.THATCH_BLOCK.get())));
    public static final RegistryObject<Block> THATCH_TRAPDOOR = register("thatch_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).strength(0.5F,0.5F).noOcclusion()));
    public static final RegistryObject<ModdedStandingSignBlock> THATCH_SIGN = registerNoItem("thatch_sign", () -> new ModdedStandingSignBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().strength(0.5F).sound(SoundType.WET_GRASS), SWDM.THATCH_WT));
    public static final RegistryObject<ModdedWallSignBlock> THATCH_WALL_SIGN = registerNoItem("thatch_wall_sign", () -> new ModdedWallSignBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().strength(0.5F).sound(SoundType.WET_GRASS).lootFrom(THATCH_SIGN), SWDM.THATCH_WT));
    public static final RegistryObject<Block> BAMBOO_BLOCK = register("bamboo_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> BAMBOO_BUTTON = register("bamboo_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> BAMBOO_DOOR = register("bamboo_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F, 0.5F).noOcclusion()));
    public static final RegistryObject<Block> BAMBOO_FENCE = register("bamboo_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F,0.5F)));
    public static final RegistryObject<Block> BAMBOO_FENCE_GATE = register("bamboo_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F,0.5F)));
    public static final RegistryObject<Block> BAMBOO_LADDER = register("bamboo_ladder", () -> new LadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER)));
    public static final RegistryObject<Block> BAMBOO_PRESSURE_PLATE = register("bamboo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.5F,0.5F)));
    public static final RegistryObject<Block> BAMBOO_SLAB = register("bamboo_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BlockInit.BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> BAMBOO_STAIRS = register("bamboo_stairs", () -> new StairsBlock(() -> BlockInit.BAMBOO_BLOCK.get().defaultBlockState() , AbstractBlock.Properties.copy(BlockInit.BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> BAMBOO_TRAPDOOR = register("bamboo_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO_SAPLING).strength(0.5F,0.5F).noOcclusion()));
    public static final RegistryObject<ModdedStandingSignBlock> BAMBOO_SIGN = registerNoItem("bamboo_sign", () -> new ModdedStandingSignBlock(AbstractBlock.Properties.of(Material.BAMBOO).noCollission().strength(0.5F).sound(SoundType.BAMBOO_SAPLING), SWDM.BAMBOO_WT));
    public static final RegistryObject<ModdedWallSignBlock> BAMBOO_WALL_SIGN = registerNoItem("bamboo_wall_sign", () -> new ModdedWallSignBlock(AbstractBlock.Properties.of(Material.BAMBOO).noCollission().strength(0.5F).sound(SoundType.BAMBOO_SAPLING).lootFrom(() -> BAMBOO_SIGN.get()), SWDM.BAMBOO_WT));
    public static final RegistryObject<Block> ACACIA_BEAM = register("acacia_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> BIRCH_BEAM = register("birch_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> DARK_OAK_BEAM = register("dark_oak_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> JUNGLE_BEAM = register("jungle_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> OAK_BEAM = register("oak_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> SPRUCE_BEAM = register("spruce_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> ACACIA_PLANK_WALL = register("acacia_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> BIRCH_PLANK_WALL = register("birch_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<Block> DARK_OAK_PLANK_WALL = register("dark_oak_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<Block> JUNGLE_PLANK_WALL = register("jungle_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<Block> OAK_PLANK_WALL = register("oak_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> SPRUCE_PLANK_WALL = register("spruce_plank_wall", () -> new WallBlock(Block.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<Block> LIGHT_CLAY_BLOCK = register("light_clay_block", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> MEDIUM_CLAY_BLOCK = register("medium_clay_block", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> DARK_CLAY_BLOCK= register("dark_clay_block", () -> new Block(Block.Properties.copy(Blocks.CLAY)));
    public static final RegistryObject<Block> ACACIA_TRAPDOOR_BIRCH = register("acacia_trapdoor_birch", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<Block> ACACIA_TRAPDOOR_DARK_OAK = register("acacia_trapdoor_dark_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    public static final RegistryObject<Block> ACACIA_TRAPDOOR_JUNGLE = register("acacia_trapdoor_jungle", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<Block> ACACIA_TRAPDOOR_OAK = register("acacia_trapdoor_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> ACACIA_TRAPDOOR_SPRUCE = register("acacia_trapdoor_spruce", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<Block> BIRCH_TRAPDOOR_ACACIA = register("birch_trapdoor_acacia", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> BIRCH_TRAPDOOR_DARK_OAK = register("birch_trapdoor_dark_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    public static final RegistryObject<Block> BIRCH_TRAPDOOR_JUNGLE = register("birch_trapdoor_jungle", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<Block> BIRCH_TRAPDOOR_OAK = register("birch_trapdoor_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> BIRCH_TRAPDOOR_SPRUCE = register("birch_trapdoor_spruce", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_ACACIA = register("dark_oak_trapdoor_acacia", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_BIRCH = register("dark_oak_trapdoor_birch", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_JUNGLE = register("dark_oak_trapdoor_jungle", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_OAK = register("dark_oak_trapdoor_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> DARK_OAK_TRAPDOOR_SPRUCE = register("dark_oak_trapdoor_spruce", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<Block> JUNGLE_TRAPDOOR_ACACIA = register("jungle_trapdoor_acacia", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> JUNGLE_TRAPDOOR_BIRCH = register("jungle_trapdoor_birch", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<Block> JUNGLE_TRAPDOOR_DARK_OAK = register("jungle_trapdoor_dark_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    public static final RegistryObject<Block> JUNGLE_TRAPDOOR_OAK = register("jungle_trapdoor_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> JUNGLE_TRAPDOOR_SPRUCE = register("jungle_trapdoor_spruce", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<Block> OAK_TRAPDOOR_ACACIA = register("oak_trapdoor_acacia", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> OAK_TRAPDOOR_BIRCH = register("oak_trapdoor_birch", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<Block> OAK_TRAPDOOR_DARK_OAK = register("oak_trapdoor_dark_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    public static final RegistryObject<Block> OAK_TRAPDOOR_JUNGLE = register("oak_trapdoor_jungle", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<Block> OAK_TRAPDOOR_SPRUCE = register("oak_trapdoor_spruce", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<Block> SPRUCE_TRAPDOOR_ACACIA = register("spruce_trapdoor_acacia", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> SPRUCE_TRAPDOOR_BIRCH = register("spruce_trapdoor_birch", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<Block> SPRUCE_TRAPDOOR_DARK_OAK = register("spruce_trapdoor_dark_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)));
    public static final RegistryObject<Block> SPRUCE_TRAPDOOR_JUNGLE = register("spruce_trapdoor_jungle", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<Block> SPRUCE_TRAPDOOR_OAK = register("spruce_trapdoor_oak", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> LIGHT_SCREEN = register("light_screen", () -> new Block(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_SCREEN_STAIRS = register("light_screen_stairs", () -> new StairsBlock(LIGHT_SCREEN.get().defaultBlockState(), Block.Properties.copy(LIGHT_SCREEN.get())));
    public static final RegistryObject<Block> LIGHT_SCREEN_SLAB = register("light_screen_slab", () -> new SlabBlock(Block.Properties.copy(LIGHT_SCREEN.get())));
    public static final RegistryObject<Block> LIGHT_SCREEN_WALL = register("light_screen_wall", () -> new WallBlock(Block.Properties.copy(LIGHT_SCREEN.get())));
    public static final RegistryObject<Block> LIGHT_SCREEN_GLASS_PANE = register("light_screen_glass_pane", () -> new PaneBlock(Block.Properties.copy(LIGHT_SCREEN.get())));
    public static final RegistryObject<Block> MEDIUM_SCREEN = register("medium_screen", () -> new Block(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).noOcclusion()));
    public static final RegistryObject<Block> MEDIUM_SCREEN_STAIRS = register("medium_screen_stairs", () -> new StairsBlock(MEDIUM_SCREEN.get().defaultBlockState(), Block.Properties.copy(MEDIUM_SCREEN.get())));
    public static final RegistryObject<Block> MEDIUM_SCREEN_SLAB = register("medium_screen_slab", () -> new SlabBlock(Block.Properties.copy(MEDIUM_SCREEN.get())));
    public static final RegistryObject<Block> MEDIUM_SCREEN_WALL = register("medium_screen_wall", () -> new WallBlock(Block.Properties.copy(MEDIUM_SCREEN.get())));
    public static final RegistryObject<Block> MEDIUM_SCREEN_GLASS_PANE = register("medium_screen_glass_pane", () -> new PaneBlock(Block.Properties.copy(MEDIUM_SCREEN.get())));
    public static final RegistryObject<Block> DARK_SCREEN = register("dark_screen", () -> new Block(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).noOcclusion()));
    public static final RegistryObject<Block> DARK_SCREEN_STAIRS = register("dark_screen_stairs", () -> new StairsBlock(DARK_SCREEN.get().defaultBlockState(), Block.Properties.copy(DARK_SCREEN.get())));
    public static final RegistryObject<Block> DARK_SCREEN_SLAB = register("dark_screen_slab", () -> new SlabBlock(Block.Properties.copy(DARK_SCREEN.get())));
    public static final RegistryObject<Block> DARK_SCREEN_WALL = register("dark_screen_wall", () -> new WallBlock(Block.Properties.copy(DARK_SCREEN.get())));
    public static final RegistryObject<Block> DARK_SCREEN_GLASS_PANE = register("dark_screen_glass_pane", () -> new PaneBlock(Block.Properties.copy(DARK_SCREEN.get())));
    public static final RegistryObject<Block> CLOCK = register("clock", () -> new ClockBlock(AbstractBlock.Properties.of(Material.WOOD).strength(1).noOcclusion()));
    public static final RegistryObject<Block> BLACK_FIBER_CARPET = register("black_fiber_carpet", () -> new CarpetBlock(DyeColor.BLACK, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> BLUE_GRAY_FIBER_CARPET = register("blue_gray_fiber_carpet", () -> new CarpetBlock(DyeColor.BLUE, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> BROWN_FIBER_CARPET = register("brown_fiber_carpet", () -> new CarpetBlock(DyeColor.BROWN, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> DARK_BROWN_FIBER_CARPET = register("dark_brown_fiber_carpet", () -> new CarpetBlock(DyeColor.BROWN, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> DUSTED_GRAY_FIBER_CARPET = register("dusted_gray_fiber_carpet", () -> new CarpetBlock(DyeColor.GRAY, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> GOLDEN_FIBER_CARPET = register("golden_fiber_carpet", () -> new CarpetBlock(DyeColor.YELLOW, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> GRAY_FIBER_CARPET = register("gray_fiber_carpet", () -> new CarpetBlock(DyeColor.GRAY, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_GRAY_FIBER_CARPET = register("light_gray_fiber_carpet", () -> new CarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> MUTED_BROWN_FIBER_CARPET = register("muted_brown_fiber_carpet", () -> new CarpetBlock(DyeColor.BROWN, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> VIVID_RED_FIBER_CARPET = register("vivid_red_fiber_carpet", () -> new CarpetBlock(DyeColor.RED, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> WHITE_FIBER_CARPET = register("white_fiber_carpet", () -> new CarpetBlock(DyeColor.WHITE, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    //Items
    public static final RegistryObject<Item> ACACIA_STICK = ITEMS.register("acacia_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> BIRCH_STICK = ITEMS.register("birch_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> DARK_OAK_STICK = ITEMS.register("dark_oak_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> JUNGLE_STICK = ITEMS.register("jungle_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> OAK_STICK = ITEMS.register("oak_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> SPRUCE_STICK = ITEMS.register("spruce_stick", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));
    public static final RegistryObject<Item> THATCH = ITEMS.register("thatch", () -> new Item(new Item.Properties().tab(SWDM.SWDMTAB)));

    // Sign Items
    public static final RegistryObject<SignItem> THATCH_SIGN_ITEM = ITEMS.register("thatch_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(SWDM.SWDMTAB), THATCH_SIGN.get(), THATCH_WALL_SIGN.get()));
    public static final RegistryObject<SignItem> BAMBOO_SIGN_ITEM = ITEMS.register("bamboo_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(SWDM.SWDMTAB), BAMBOO_SIGN.get(), BAMBOO_WALL_SIGN.get()));
}
