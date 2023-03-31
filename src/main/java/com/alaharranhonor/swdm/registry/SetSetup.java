package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.gentypes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SetSetup {

    public static final List<String> STONE_TYPES = List.of("mossy", "cracked", "mossy_more");
    public static final List<String> LMD_TYPES = List.of("light", "medium", "dark");
    public static final List<String> CUSTOM_COLORS = List.of("sage", "golden");
    public static final List<String> NATURAL_TONES = List.of("tuscan", "peach", "thistle", "dark_brown", "brown", "mahogany", "muted_brown", "vivid_red", "orange", "golden", "pale", "yellow", "white", "pearl", "dusted_gray", "light_gray", "slate", "blue_gray", "gray", "royal_gray", "black");
    public static final List<String> DYE_COLORS = Arrays.stream(DyeColor.values()).map(color -> color.getSerializedName()).collect(Collectors.toList());

    public static final List<GenSet> SETS = new ArrayList<>();

    public static void init() {
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE).withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.STONE_BRICKS, "stone_brick").withBase(BlockGen::new).sets(LMD_TYPES, STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.DIORITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.GRANITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.ANDESITE).withBase(BlockGen::new).sets(STONE_TYPES).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.DARK_PRISMARINE).withBase(BlockGen::new).sets(DYE_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.TERRACOTTA).withBase(BlockGen::new).sets(CUSTOM_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.OAK_PLANKS, "siding").withBase(BlockGen::new).sets(LMD_TYPES, DYE_COLORS).types(stoneTypes()).build());
        SETS.add(GenSet.builder(() -> Blocks.GLASS, "bricks_glass").withBase(BlockGen::new).sets(DYE_COLORS).types(stoneTypes()).build());

        for (DyeColor color : DyeColor.values()) {
            Block concrete = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(color.getSerializedName() + "_concrete"));
            Block terracotta = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(color.getSerializedName() + "_terracotta"));
            SETS.add(GenSet.builder(() -> concrete).types(stoneTypes()).build());
            SETS.add(GenSet.builder(() -> terracotta).types(stoneTypes()).build());
        }

        for (GenSet set : SETS) {
            set.register(BlockSetup.BLOCKS, ItemSetup.ITEMS);
        }
    }

    private static List<Function<Supplier<Block>, GenType<?>>> stoneTypes() {
        return List.of(StairGen::new, SlabGen::new, HalfWallGen::new, TrapDoorGen::new, b -> new ButtonGen(b, true), b -> new PressurePlateGen(b, PressurePlateBlock.Sensitivity.MOBS));
    }
}
