package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.blocks.*;
import com.alaharranhonor.swem.blocks.NonParallelBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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

    private static Supplier<BlockItem> item(final RegistryObject<? extends Block> block, final ItemGroup itemGroup) {
        return () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup));
    }

    // Stone sets
    // The order is STONE TYPE then STONE.

    // Stone sets 16 colours.
    // Stonem set -> List of the 16 colours
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> STONE_SET_BLOCKS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<StairsBlock>>>> STONE_SET_STAIRS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<SlabBlock>>>> STONE_SET_SLABS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<WallBlock>>>> STONE_SET_WALLS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<StoneButtonBlock>>>> STONE_SET_BUTTONS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<PressurePlateBlock>>>> STONE_SET_PRESSURE_PLATES = new HashMap<>();


    // Vanilla Wood types
    public static final List<RegistryObject<Block>> LEAVES_TRAPDOORS = new ArrayList<>();
    public static final List<RegistryObject<Block>> LADDERS = new ArrayList<>();


    // SSW_SET
    // Type of set -> Map<BlockTypes> -> List<Blocks>'
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_BLOCKS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<StairsBlock>>>> SSW_SET_STAIRS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<SlabBlock>>>> SSW_SET_SLABS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<WallBlock>>>> SSW_SET_WALLS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<CarpetBlock>>>> SSW_SET_CARPETS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<Block>>>> SSW_SET_BEAMS = new HashMap<>();
    public static final HashMap<String, HashMap<String, List<RegistryObject<PaneBlock>>>> SSW_SET_GLASS_PANES = new HashMap<>();

    public static final List<RegistryObject<CoatedChain>> COATED_CHAINS = new ArrayList<>();
    public static final List<RegistryObject<SandBlock>> SAND_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<Block>> SANDSTONE_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<CarpetBlock>> FIBER_CARPETS = new ArrayList<>();


    static {

        for (String stoneType : SWDM.STONE_TYPES) {

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("standard").entrySet()) {
                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(stoneType + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                STONE_SET_SLABS.put("standard", slabMap);

                Supplier<Block> stairBlock = () -> new Block(blockType.getValue());


                HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                blockList.add(register(stoneType + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                blockMap.put(blockType.getKey(), blockList);
                STONE_SET_BLOCKS.put("standard", blockMap);


                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                Supplier<Block> finalStairBlock = stairBlock;
                stairList.add(register(stoneType + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                STONE_SET_STAIRS.put("standard", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(stoneType + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                STONE_SET_WALLS.put("standard", wallMap);

                HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                buttonList.add(register(stoneType + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                buttonMap.put(blockType.getKey(), buttonList);
                STONE_SET_BUTTONS.put("standard", buttonMap);

                HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("standard", new HashMap<>());
                List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                pressurePlateList.add(register(stoneType + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                STONE_SET_PRESSURE_PLATES.put("standard", pressurePlateMap);
            }

            for (String lmdType : SWDM.LMD_TYPES) {
                for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {

                    HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    slabList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                    slabMap.put(blockType.getKey(), slabList);
                    STONE_SET_SLABS.put("lmd", slabMap);

                    HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    blockList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                    blockMap.put(blockType.getKey(), blockList);
                    STONE_SET_BLOCKS.put("lmd", blockMap);


                    HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                    stairMap.put(blockType.getKey(), stairList);
                    STONE_SET_STAIRS.put("lmd", stairMap);

                    HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    wallList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                    wallMap.put(blockType.getKey(), wallList);
                    STONE_SET_WALLS.put("lmd", wallMap);

                    HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    buttonList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                    buttonMap.put(blockType.getKey(), buttonList);
                    STONE_SET_BUTTONS.put("lmd", buttonMap);

                    HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    pressurePlateList.add(register(stoneType + "_" + lmdType + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                    pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                    STONE_SET_PRESSURE_PLATES.put("lmd", pressurePlateMap);
                }
            }

        }

        for (String lmdType : SWDM.LMD_TYPES) {
            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("lmd-only").entrySet()) {

                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(lmdType + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                STONE_SET_SLABS.put("lmd-only", slabMap);

                HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                blockList.add(register(lmdType + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                blockMap.put(blockType.getKey(), blockList);
                STONE_SET_BLOCKS.put("lmd-only", blockMap);


                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                stairList.add(register(lmdType + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                STONE_SET_STAIRS.put("lmd-only", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(lmdType + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                STONE_SET_WALLS.put("lmd-only", wallMap);

                HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                buttonList.add(register(lmdType + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                buttonMap.put(blockType.getKey(), buttonList);
                STONE_SET_BUTTONS.put("lmd-only", buttonMap);

                HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("lmd-only", new HashMap<>());
                List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                pressurePlateList.add(register(lmdType + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                STONE_SET_PRESSURE_PLATES.put("lmd-only", pressurePlateMap);
            }
        }


        for (DyeColor color : DyeColor.values()) {

            COATED_CHAINS.add(register("coated_chain_" +color.getName(), () -> new CoatedChain(AbstractBlock.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion())));

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("color").entrySet()) {
                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = SSW_SET_SLABS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(color.getName() + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                SSW_SET_SLABS.put("color", slabMap);


                Supplier<Block> stairBlock = null;
                if (!blockType.getKey().equals("wool") && !blockType.getKey().equals("stained_glass")) {

                    HashMap<String, List<RegistryObject<Block>>> blockMap = SSW_SET_BLOCKS.getOrDefault("color", new HashMap<>());
                    List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairBlock = () -> new Block(blockType.getValue());
                    blockList.add(register(color.getName() + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                    blockMap.put(blockType.getKey(), blockList);
                    SSW_SET_BLOCKS.put("color", blockMap);

                }

                if (stairBlock == null) {
                    stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + color.getName() + "_" + blockType.getKey()));
                }


                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = SSW_SET_STAIRS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                Supplier<Block> finalStairBlock = stairBlock;

                stairList.add(register(color.getName() + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));

                stairMap.put(blockType.getKey(), stairList);
                SSW_SET_STAIRS.put("color", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = SSW_SET_WALLS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(color.getName() + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                SSW_SET_WALLS.put("color", wallMap);

                if (blockType.getKey().equals("pastel_wool") || blockType.getKey().equals("tinted_wool")) {
                    HashMap<String, List<RegistryObject<CarpetBlock>>> carpetMap = SSW_SET_CARPETS.getOrDefault("color", new HashMap<>());
                    List<RegistryObject<CarpetBlock>> carpetList = carpetMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    carpetList.add(register(color.getName() + "_" + blockType.getKey() + "_carpet", () -> new CarpetBlock(color, blockType.getValue())));
                    carpetMap.put(blockType.getKey(), carpetList);
                    SSW_SET_CARPETS.put("color", carpetMap);
                }
            }

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("color").entrySet()) {

                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(color.getName() + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                STONE_SET_SLABS.put("color", slabMap);

                Supplier<Block> stairBlock = null;
                if (!blockType.getKey().equals("concrete") && !blockType.getKey().equals("terracotta")) {

                    HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("color", new HashMap<>());
                    List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairBlock = () -> new Block(blockType.getValue());
                    blockList.add(register(color.getName() + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                    blockMap.put(blockType.getKey(), blockList);
                    STONE_SET_BLOCKS.put("color", blockMap);
                }

                if (stairBlock == null) {
                    stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + color.getName() + "_" + blockType.getKey()));
                }

                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                Supplier<Block> finalStairBlock = stairBlock;
                stairList.add(register(color.getName() + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() ->finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                STONE_SET_STAIRS.put("color", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(color.getName() + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                STONE_SET_WALLS.put("color", wallMap);

                HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("color", new HashMap<>());
                List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                buttonList.add(register(color.getName() + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                buttonMap.put(blockType.getKey(), buttonList);
                STONE_SET_BUTTONS.put("color", buttonMap);

                HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("color", new HashMap<>());
                List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                pressurePlateList.add(register(color.getName() + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                STONE_SET_PRESSURE_PLATES.put("color", pressurePlateMap);
            }

       }

        for (String color : SWDM.CUSTOM_COLORS) {

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("color_custom").entrySet()) {

                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(color + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                STONE_SET_SLABS.put("color_custom", slabMap);

                Supplier<Block> stairBlock = null;
                HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                stairBlock = () -> new Block(blockType.getValue());
                blockList.add(register(color + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                blockMap.put(blockType.getKey(), blockList);
                STONE_SET_BLOCKS.put("color_custom", blockMap);

                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                Supplier<Block> finalStairBlock = stairBlock;
                stairList.add(register(color + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() ->finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                STONE_SET_STAIRS.put("color_custom", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(color + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                STONE_SET_WALLS.put("color_custom", wallMap);

                HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                buttonList.add(register(color + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                buttonMap.put(blockType.getKey(), buttonList);
                STONE_SET_BUTTONS.put("color_custom", buttonMap);

                HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("color_custom", new HashMap<>());
                List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                pressurePlateList.add(register(color + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                STONE_SET_PRESSURE_PLATES.put("color_custom", pressurePlateMap);
            }
        }

        WoodType.values().forEach((wood) -> {

            if (!wood.name().equals("crimson") && !wood.name().equals("warped") && !wood.name().contains(":")) { // Vanilla wood types.
                String sanitizedWoodName = wood.name().contains(":") ? wood.name().split(":")[1] : wood.name();
                HashMap<String, List<RegistryObject<Block>>> beamMap = SSW_SET_BEAMS.getOrDefault("wv", new HashMap<>());
                List<RegistryObject<Block>> beamList = beamMap.getOrDefault("wood", new ArrayList<>());
                beamList.add(register(sanitizedWoodName + "_beam", () -> new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD))));
                beamMap.put("wood", beamList);
                SSW_SET_BEAMS.put("wv", beamMap);
                for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("wv").entrySet()) {
                    String typeName;
                    if (blockType.getKey().equals("stripped_log")) {
                        typeName = "stripped_"+sanitizedWoodName+"_log";
                    } else {
                        typeName = sanitizedWoodName + "_" + blockType.getKey();
                    }

                    // Skip these wood types. ^^
                    HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = SSW_SET_SLABS.getOrDefault("wv", new HashMap<>());
                    List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    slabList.add(register(typeName + "_slab", () -> new SlabBlock(blockType.getValue())));
                    slabMap.put(blockType.getKey(), slabList);
                    SSW_SET_SLABS.put("wv", slabMap);


                    Supplier<Block> stairBlock = null;
                    if (!blockType.getKey().equals("log") && !blockType.getKey().equals("stripped_log")) {

                        HashMap<String, List<RegistryObject<Block>>> blockMap = SSW_SET_BLOCKS.getOrDefault("wv", new HashMap<>());
                        List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                        stairBlock = () -> new Block(blockType.getValue());
                        blockList.add(register(typeName, () -> new Block(blockType.getValue())));
                        blockMap.put(blockType.getKey(), blockList);
                        SSW_SET_BLOCKS.put("wv", blockMap);

                    }

                    if (stairBlock == null) {
                        stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + sanitizedWoodName + "_" + blockType.getKey()));
                    }


                    HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = SSW_SET_STAIRS.getOrDefault("wv", new HashMap<>());
                    List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    Supplier<Block> finalStairBlock = stairBlock;
                    stairList.add(register(typeName + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                    stairMap.put(blockType.getKey(), stairList);
                    SSW_SET_STAIRS.put("wv", stairMap);

                    HashMap<String, List<RegistryObject<WallBlock>>> wallMap = SSW_SET_WALLS.getOrDefault("wv", new HashMap<>());
                    List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    wallList.add(register(typeName + "_wall", () -> new WallBlock(blockType.getValue())));
                    wallMap.put(blockType.getKey(), wallList);
                    SSW_SET_WALLS.put("wv", wallMap);

                    if (blockType.getKey().equals("log") || blockType.getKey().equals("stripped_log")) {
                        beamList.add(register(typeName + "_beam", () -> new BeamBlock(blockType.getValue())));
                        beamMap.put(blockType.getKey(), beamList);
                        SSW_SET_BEAMS.put("wv", beamMap);
                    }

                }

                for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("wv-whitewash").entrySet()) {
                    if (wood.name().contains("swem:")) break;


                    HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = SSW_SET_SLABS.getOrDefault("wv-whitewash", new HashMap<>());
                    List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    slabList.add(register(wood.name() + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                    slabMap.put(blockType.getKey(), slabList);
                    SSW_SET_SLABS.put("wv-whitewash", slabMap);


                    Supplier<Block> stairBlock = null;
                    if (!blockType.getKey().equals("leaves")) {

                        HashMap<String, List<RegistryObject<Block>>> blockMap = SSW_SET_BLOCKS.getOrDefault("wv-whitewash", new HashMap<>());
                        List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                        stairBlock = () -> new Block(blockType.getValue());
                        blockList.add(register(wood.name() + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                        blockMap.put(blockType.getKey(), blockList);
                        SSW_SET_BLOCKS.put("wv-whitewash", blockMap);

                    }

                    if (stairBlock == null) {
                        stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + wood.name() + "_planks"));
                    }


                    HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = SSW_SET_STAIRS.getOrDefault("wv-whitewash", new HashMap<>());
                    List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    Supplier<Block> finalStairBlock = stairBlock;
                    stairList.add(register(wood.name() + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                    stairMap.put(blockType.getKey(), stairList);
                    SSW_SET_STAIRS.put("wv-whitewash", stairMap);

                    HashMap<String, List<RegistryObject<WallBlock>>> wallMap = SSW_SET_WALLS.getOrDefault("wv-whitewash", new HashMap<>());
                    List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    wallList.add(register(wood.name() + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                    wallMap.put(blockType.getKey(), wallList);
                    SSW_SET_WALLS.put("wv-whitewash", wallMap);

                }
            }

            if (wood.name().equals("crimson") || wood.name().equals("warped") || wood.name().contains("swdm:") || wood.name().contains("swem:")) {
                // Special vanilla wood types, and swdm and swem wood types.
            } else {
                if (!wood.name().contains(":")) {
                    LEAVES_TRAPDOORS.add(register(wood.name() + "_leaves_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).sound(SoundType.GRASS).noOcclusion())));
                    LADDERS.add(register(wood.name() + "_ladder", () -> new LadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER))));
                }
            }
            // All other vanilla wood types.

        });

        SWDM.LMD_TYPES.forEach((lmdType) -> {
            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("lmd").entrySet()) {

                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = SSW_SET_SLABS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(lmdType + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                SSW_SET_SLABS.put("lmd", slabMap);


                Supplier<Block> stairBlock = null;

                HashMap<String, List<RegistryObject<Block>>> blockMap = SSW_SET_BLOCKS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                stairBlock = () -> new Block(blockType.getValue());
                blockList.add(register(lmdType + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                blockMap.put(blockType.getKey(), blockList);
                SSW_SET_BLOCKS.put("lmd", blockMap);


                if (stairBlock == null) {
                    stairBlock = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:" + lmdType + "_" + blockType.getKey()));
                }


                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = SSW_SET_STAIRS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                Supplier<Block> finalStairBlock = stairBlock;
                stairList.add(register(lmdType + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                SSW_SET_STAIRS.put("lmd", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = SSW_SET_WALLS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(lmdType + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                SSW_SET_WALLS.put("lmd", wallMap);

                if (blockType.getKey().equals("screen")) {
                    HashMap<String, List<RegistryObject<PaneBlock>>> beamMap = SSW_SET_GLASS_PANES.getOrDefault("lmd", new HashMap<>());
                    List<RegistryObject<PaneBlock>> beamList = beamMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    beamList.add(register(lmdType + "_" + blockType.getKey() + "_glass_pane", () -> new PaneBlock(blockType.getValue())));
                    beamMap.put(blockType.getKey(), beamList);
                    SSW_SET_GLASS_PANES.put("lmd", beamMap);
                }
            }

            for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {

                HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = STONE_SET_SLABS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                slabList.add(register(lmdType + "_" + blockType.getKey() + "_slab", () -> new SlabBlock(blockType.getValue())));
                slabMap.put(blockType.getKey(), slabList);
                STONE_SET_SLABS.put("lmd", slabMap);

                HashMap<String, List<RegistryObject<Block>>> blockMap = STONE_SET_BLOCKS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                blockList.add(register(lmdType + "_" + blockType.getKey(), () -> new Block(blockType.getValue())));
                blockMap.put(blockType.getKey(), blockList);
                STONE_SET_BLOCKS.put("lmd", blockMap);



                HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = STONE_SET_STAIRS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                stairList.add(register(lmdType + "_" + blockType.getKey() + "_stairs", () -> new StairsBlock(() ->new Block(blockType.getValue()).defaultBlockState(), blockType.getValue())));
                stairMap.put(blockType.getKey(), stairList);
                STONE_SET_STAIRS.put("lmd", stairMap);

                HashMap<String, List<RegistryObject<WallBlock>>> wallMap = STONE_SET_WALLS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                wallList.add(register(lmdType + "_" + blockType.getKey() + "_wall", () -> new WallBlock(blockType.getValue())));
                wallMap.put(blockType.getKey(), wallList);
                STONE_SET_WALLS.put("lmd", wallMap);

                HashMap<String, List<RegistryObject<StoneButtonBlock>>> buttonMap = STONE_SET_BUTTONS.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<StoneButtonBlock>> buttonList = buttonMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                buttonList.add(register(lmdType + "_" + blockType.getKey() + "_button", () -> new StoneButtonBlock(blockType.getValue())));
                buttonMap.put(blockType.getKey(), buttonList);
                STONE_SET_BUTTONS.put("lmd", buttonMap);

                HashMap<String, List<RegistryObject<PressurePlateBlock>>> pressurePlateMap = STONE_SET_PRESSURE_PLATES.getOrDefault("lmd", new HashMap<>());
                List<RegistryObject<PressurePlateBlock>> pressurePlateList = pressurePlateMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                pressurePlateList.add(register(lmdType + "_" + blockType.getKey() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, blockType.getValue())));
                pressurePlateMap.put(blockType.getKey(), pressurePlateList);
                STONE_SET_PRESSURE_PLATES.put("lmd", pressurePlateMap);
            }
        });

        for (String natural : SWDM.NATURAL_TONES) {
            SAND_BLOCKS.add(register("sand_" + natural, () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND))));
            SANDSTONE_BLOCKS.add(register("sandstone_" + natural, () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE))));
            FIBER_CARPETS.add(register("fiber_carpet_" + natural, () -> new CarpetBlock(DyeColor.BLACK, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion())));
        }

        for (DyeColor color : DyeColor.values()) {
            for (String lmdType : SWDM.LMD_TYPES) {

                for (Map.Entry<String, AbstractBlock.Properties> blockType : SWDM.SSW_SETS.get("lmd-color").entrySet()) {
                    HashMap<String, List<RegistryObject<SlabBlock>>> slabMap = SSW_SET_SLABS.getOrDefault("lmd-color", new HashMap<>());
                    List<RegistryObject<SlabBlock>> slabList = slabMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    slabList.add(register(blockType.getKey() + "_" + color.getName() + "_" + lmdType + "_slab", () -> new SlabBlock(blockType.getValue())));
                    slabMap.put(blockType.getKey(), slabList);
                    SSW_SET_SLABS.put("lmd-color", slabMap);

                    Supplier<Block> stairBlock = null;
                    HashMap<String, List<RegistryObject<Block>>> blockMap = SSW_SET_BLOCKS.getOrDefault("lmd-color", new HashMap<>());
                    List<RegistryObject<Block>> blockList = blockMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    stairBlock = () -> new Block(blockType.getValue());
                    blockList.add(register(blockType.getKey() + "_" + color.getName() + "_" + lmdType, () -> new Block(blockType.getValue())));
                    blockMap.put(blockType.getKey(), blockList);
                    SSW_SET_BLOCKS.put("lmd-color", blockMap);

                    HashMap<String, List<RegistryObject<StairsBlock>>> stairMap = SSW_SET_STAIRS.getOrDefault("lmd-color", new HashMap<>());
                    List<RegistryObject<StairsBlock>> stairList = stairMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    Supplier<Block> finalStairBlock = stairBlock;
                    stairList.add(register(blockType.getKey() + "_" + color.getName() + "_" + lmdType + "_stairs", () -> new StairsBlock(() -> finalStairBlock.get().defaultBlockState(), blockType.getValue())));
                    stairMap.put(blockType.getKey(), stairList);
                    SSW_SET_STAIRS.put("lmd-color", stairMap);

                    HashMap<String, List<RegistryObject<WallBlock>>> wallMap = SSW_SET_WALLS.getOrDefault("lmd-color", new HashMap<>());
                    List<RegistryObject<WallBlock>> wallList = wallMap.getOrDefault(blockType.getKey(), new ArrayList<>());
                    wallList.add(register(blockType.getKey() + "_" + color.getName() + "_" + lmdType + "_wall", () -> new WallBlock(blockType.getValue())));
                    wallMap.put(blockType.getKey(), wallList);
                    SSW_SET_WALLS.put("lmd-color", wallMap);
                }

            }
        }
    }

    public static final RegistryObject<Block> STONE_WALL = register("stone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GLASS_STAIRS = register("glass_stairs", () -> new StairsBlock(Blocks.GLASS.defaultBlockState(), Block.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> GLASS_SLAB = register("glass_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GLASS)));
    public static final RegistryObject<Block> GLASS_WALL = register("glass_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GLASS)));
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
    public static final RegistryObject<Block> GRASS_SLAB = register("grass_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> DIRT_SLAB = register("dirt_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<CarpetBlock> FIBER_CARPET_RED_SAND = register("fiber_carpet_red_sand", () -> new CarpetBlock(DyeColor.BLACK, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<CarpetBlock> FIBER_CARPET_SAND = register("fiber_carpet_sand", () -> new CarpetBlock(DyeColor.BLACK, AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLACK).strength(0.1F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> TERRACOTTA_BUTTON = register("terracotta_button", () -> new StoneButtonBlock(AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));
    public static final RegistryObject<Block> TERRACOTTA_SLAB = register("terracotta_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));
    public static final RegistryObject<Block> TERRACOTTA_PRESSURE_PLATE = register("terracotta_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));
    public static final RegistryObject<Block> TERRACOTTA_STAIRS = register("terracotta_stairs", () -> new StairsBlock(() -> Blocks.TERRACOTTA.defaultBlockState(), AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));
    public static final RegistryObject<Block> TERRACOTTA_WALL = register("terracotta_wall", () -> new WallBlock(AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));

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
