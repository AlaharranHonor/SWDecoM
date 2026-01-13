package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.DecoWorkshopBlock;
import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockSetup {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(ModRef.ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ModRef.ID);

    // Registered sign blocks are stored for the block set for sign block entity
    public static final List<DeferredBlock<? extends SignBlock>> SIGN_BLOCKS = new ArrayList<>();
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<SWDMSignBlockEntity>> SIGN_BLOCK_ENTITY;

    public static final Map<ResourceLocation, DeferredBlock<Block>> MANUAL_BLOCKS = new HashMap<>();

    public static final DeferredBlock<Block> WHITEWASH_PLANKS = register("whitewash_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<Block> WHITEWASH_LOG = register("whitewash_log", () -> log(MapColor.SNOW, MapColor.SNOW));
    public static final DeferredBlock<Block> WHITEWASH_STRIPPED_LOG = register("whitewash_stripped_log", () -> log(MapColor.SNOW, MapColor.SNOW));

    public static final DeferredBlock<Block> BAMBOO_PLANKS = register("bamboo_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_GREEN)));
    public static final DeferredBlock<Block> BAMBOO_LOG = register("bamboo_log", () -> log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));
    public static final DeferredBlock<Block> BAMBOO_STRIPPED_LOG = register("bamboo_stripped_log", () -> log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));

    public static final DeferredBlock<Block> THATCH = register("thatch", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredBlock<Block> THATCH_PLANKS = register("thatch_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> THATCH_LOG = register("thatch_log", () -> log(MapColor.COLOR_YELLOW, MapColor.COLOR_YELLOW));
    public static final DeferredBlock<Block> THATCH_STRIPPED_LOG = register("thatch_stripped_log", () -> log(MapColor.COLOR_YELLOW, MapColor.COLOR_YELLOW));

    public static final DeferredBlock<Block> SMOOTH_STONE_BORDERLESS = register("smooth_stone_borderless", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)));
    public static final DeferredBlock<Block> DECO_WORKSHOP = REGISTRY.register("deco_workshop", () -> new DecoWorkshopBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));


    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);
        BLOCK_ENTITIES.register(modBus);

        SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(SWDMSignBlockEntity::new, SIGN_BLOCKS.stream().map(DeferredHolder::get).toList().toArray(new Block[0])).build(null));

        remapBlocks(REGISTRY);
    }

    public static DeferredBlock<Block> register(String name, Supplier<Block> supplier) {
        DeferredBlock<Block> block = REGISTRY.register(name, supplier);
        MANUAL_BLOCKS.put(block.getId(), block);
        return block;
    }

    private static RotatedPillarBlock log(MapColor pTopColor, MapColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((state) -> {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    public static Block get(String name) {
        return REGISTRY.getEntries().stream().filter(rb -> rb.getId().getPath().equals(name)).findFirst().get().get();
    }

    public static void remapBlocks(DeferredRegister.Blocks registry) {
        BuiltInRegistries.BLOCK.keySet().forEach(mapping -> {
            if (!mapping.getNamespace().equals(ModRef.ID) && !mapping.getNamespace().equals("swlm")) return;
            String key = mapping.getPath();

            for (String wood : SetSetup.ALL_WOODS) {
                if (remap(registry, key, wood + "_beam", "beam_" + wood + "_planks")) return;
                if (remap(registry, key, "stripped_" + wood + "_log_beam", "beam_" + wood + "_stripped_log")) return;
                if (remap(registry, key, wood + "_log_beam", "beam_" + wood + "_log")) return;
                if (remap(registry, key, wood + "_plank_wall", wood + "_planks_wall")) return;
            }

            for (String color : SetSetup.DYE_COLORS) {
                if (remap(registry, key, "coated_chain_" + color, "chain_coated_" + color)) return;
                if (remap(registry, key, color + "_dark_prismarine", "dark_prismarine_" + color)) return;
                if (remap(registry, key, color + "_glass_bricks", "bricks_glass_" + color)) return;
                if (remap(registry, key, color + "_pastel_wool", "wool_pastel_" + color)) return;
                if (remap(registry, key, color + "_tinted_wool", "wool_tinted_" + color)) return;
                if (remap(registry, key, color + "_shingle_roof", "roof_shingle_" + color)) return;
                if (remap(registry, key, color + "_metal_roof", "roof_metal_" + color)) return;
                if (remap(registry, key, color + "_tile_roof", "roof_tile_" + color)) return;

                for (String lmd : SetSetup.LMD_TYPES) {
                    if (remap(registry, key, "siding_" + color + "_" + lmd, "siding_" + lmd + "_" + color)) return;
                }
            }

            for (String dye : SetSetup.CUSTOM_COLORS) {
                if (remap(registry, key, dye + "_terracotta", "terracotta_" + dye)) return;
            }

            for (String tone : SetSetup.NATURAL_TONES) {
                if (remap(registry, key, "fiber_carpet_" + tone, "fiber_carpet_light_" + tone)) return;
            }
            if (remap(registry, key, "fiber_carpet_sand", "fiber_carpet_light_sand")) return;
            if (remap(registry, key, "fiber_carpet_red_sand", "fiber_carpet_light_red_sand")) return;

            for (String lmd : SetSetup.LMD_TYPES) {
                if (remap(registry, key, lmd + "_screen_glass_pane", "screen_" + lmd + "_pane")) return;
                if (remap(registry, key, lmd + "_screen", "screen_" + lmd)) return;
                if (remap(registry, key, "cracked_" + lmd + "_brick", "bricks_" + lmd + "_cracked")) return;
                if (remap(registry, key, "mossy_" + lmd + "_brick", "bricks_" + lmd + "_mossy")) return;
                if (remap(registry, key, "more_mossy_" + lmd + "_brick", "bricks_" + lmd + "_mossy_more")) return;
                if (remap(registry, key, lmd + "_stone_brick", "stone_bricks_" + lmd)) return;
                if (remap(registry, key, lmd + "_stone_bricks", "stone_bricks_" + lmd)) return;
                if (remap(registry, key, "cracked_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_cracked")) return;
                if (remap(registry, key, "mossy_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_mossy")) return;
                if (remap(registry, key, "more_mossy_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_mossy_more"))
                    return;
                if (remap(registry, key, lmd + "_clay_block", "clay_" + lmd)) return;
                for (String stone : SetSetup.STONES) {
                    if (remap(registry, key, lmd + "_" + stone, stone + "_" + lmd)) return;
                }
                if (remap(registry, key, lmd + "_brick", "bricks_" + lmd)) return;
                if (remap(registry, key, lmd + "_bricks", "bricks_" + lmd)) return;
            }

            for (String stone : SetSetup.STONES) {
                if (remap(registry, key, "cracked_" + stone, stone + "_cracked")) return;
                if (remap(registry, key, "mossy_" + stone, stone + "_mossy")) return;
                if (remap(registry, key, "more_mossy_" + stone, stone + "_mossy_more")) return;
            }

            if (remap(registry, key, "thatch_block", "thatch")) return;
            if (remap(registry, key, "bamboo_block", "bamboo_log")) return;
            if (remap(registry, key, "bamboo", "bamboo_log")) return;
            if (remap(registry, key, "borderless_smooth_stone", "smooth_stone_borderless")) return;
            if (remap(registry, key, "thatch_door_bamboo", "bamboo_door_thatch")) return;
            if (remap(registry, key, "bamboo_door_thatch", "thatch_door_bamboo")) return;
        });
    }

    private static boolean remap(DeferredRegister<?> registry, String key, String from, String to) {
        if (key.startsWith(from)) { // Using .contains() will result in blue/light_blue collision. eg. light_blue_dark_prismarine -> light_dark_prismarine_blue
            registry.addAlias(ResourceLocation.parse(from), ResourceLocation.parse(to));
            return true;
        }
        return false;
    }
}
