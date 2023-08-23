package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.block.ShelfBlock;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod.EventBusSubscriber(modid = ModRef.ID)
public class ForgeEventBusSubscriber {

    @SubscribeEvent
    public static void cycleShelfBlock(PlayerInteractEvent.RightClickBlock event) {
        Player pPlayer = event.getEntity();
        InteractionHand pHand = event.getHand();
        if (!pPlayer.isShiftKeyDown() || !pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return;
        }

        BlockState pState = event.getLevel().getBlockState(event.getPos());
        if (!(pState.getBlock() instanceof ShelfBlock)) return;

        int stateOrdinal = pState.getValue(ShelfBlock.SHELF_TYPE).ordinal();
        int height = stateOrdinal / 3;
        int pos = stateOrdinal % 3;
        height = (height + 1) % 3;

        stateOrdinal = height * 3 + pos;
        stateOrdinal = stateOrdinal % SWDMBlockstateProperties.ShelfType.values().length;

        event.getLevel().setBlock(event.getPos(), pState.setValue(ShelfBlock.SHELF_TYPE, SWDMBlockstateProperties.ShelfType.values()[stateOrdinal]), 3);
    }

    @SubscribeEvent
    public static void remapBlocks(MissingMappingsEvent event) {
        event.getAllMappings(Registries.BLOCK).forEach(mapping -> {
            if (!mapping.getKey().getNamespace().equals(ModRef.ID) && !mapping.getKey().getNamespace().equals("swlm")) return;
            String key = mapping.getKey().getPath();

            for (String wood : SetSetup.ALL_WOODS) {
                if (remap(mapping, key, wood + "_beam", "beam_" + wood + "_planks")) return;
                if (remap(mapping, key, "stripped_" + wood + "_log_beam", "beam_" + wood + "_stripped_log")) return;
                if (remap(mapping, key, wood + "_log_beam", "beam_" + wood + "_log")) return;
                if (remap(mapping, key, wood + "_plank_wall", wood + "_planks_wall")) return;
            }

            for (String color : SetSetup.DYE_COLORS) {
                if (remap(mapping, key, "coated_chain_" + color, "chain_coated_" + color)) return;
                if (remap(mapping, key, color + "_dark_prismarine", "dark_prismarine_" + color)) return;
                if (remap(mapping, key, color + "_glass_bricks", "bricks_glass_" + color)) return;
                if (remap(mapping, key, color + "_pastel_wool", "wool_pastel_" + color)) return;
                if (remap(mapping, key, color + "_tinted_wool", "wool_tinted_" + color)) return;
                if (remap(mapping, key, color + "_shingle_roof", "roof_shingle_" + color)) return;
                if (remap(mapping, key, color + "_metal_roof", "roof_metal_" + color)) return;
                if (remap(mapping, key, color + "_tile_roof", "roof_tile_" + color)) return;

                for (String lmd : SetSetup.LMD_TYPES) {
                    if (remap(mapping, key, "siding_" + color + "_" + lmd, "siding_" + lmd + "_" + color)) return;
                }
            }

            for (String dye : SetSetup.CUSTOM_COLORS) {
                if (remap(mapping, key, dye + "_terracotta", "terracotta_" + dye)) return;
            }

            for (String tone : SetSetup.NATURAL_TONES) {
                if (remap(mapping, key, "fiber_carpet_" + tone, "fiber_carpet_light_" + tone)) return;
            }
            if (remap(mapping, key, "fiber_carpet_sand", "fiber_carpet_light_sand")) return;
            if (remap(mapping, key, "fiber_carpet_red_sand", "fiber_carpet_light_red_sand")) return;

            for (String lmd : SetSetup.LMD_TYPES) {
                if (remap(mapping, key, lmd + "_screen_glass_pane", "screen_" + lmd + "_pane")) return;
                if (remap(mapping, key, lmd + "_screen", "screen_" + lmd)) return;
                if (remap(mapping, key, "cracked_" + lmd + "_brick", "bricks_" + lmd + "_cracked")) return;
                if (remap(mapping, key, "mossy_" + lmd + "_brick", "bricks_" + lmd + "_mossy")) return;
                if (remap(mapping, key, "more_mossy_" + lmd + "_brick", "bricks_" + lmd + "_mossy_more")) return;
                if (remap(mapping, key, lmd + "_stone_brick", "stone_bricks_" + lmd)) return;
                if (remap(mapping, key, lmd + "_stone_bricks", "stone_bricks_" + lmd)) return;
                if (remap(mapping, key, "cracked_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_cracked")) return;
                if (remap(mapping, key, "mossy_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_mossy")) return;
                if (remap(mapping, key, "more_mossy_" + lmd + "_stone_brick", "stone_bricks_" + lmd + "_mossy_more"))
                    return;
                if (remap(mapping, key, lmd + "_clay_block", "clay_" + lmd)) return;
                for (String stone : SetSetup.STONES) {
                    if (remap(mapping, key, lmd + "_" + stone, stone + "_" + lmd)) return;
                }
                if (remap(mapping, key, lmd + "_brick", "bricks_" + lmd)) return;
                if (remap(mapping, key, lmd + "_bricks", "bricks_" + lmd)) return;
            }

            for (String stone : SetSetup.STONES) {
                if (remap(mapping, key, "cracked_" + stone, stone + "_cracked")) return;
                if (remap(mapping, key, "mossy_" + stone, stone + "_mossy")) return;
                if (remap(mapping, key, "more_mossy_" + stone, stone + "_mossy_more")) return;
            }

            if (remap(mapping, key, "thatch_block", "thatch")) return;
            if (remap(mapping, key, "bamboo_block", "bamboo_log")) return;
            if (remap(mapping, key, "bamboo", "bamboo_log")) return;
            if (remap(mapping, key, "borderless_smooth_stone", "smooth_stone_borderless")) return;
            if (remap(mapping, key, "thatch_door_bamboo", "bamboo_door_thatch")) return;
            if (remap(mapping, key, "bamboo_door_thatch", "thatch_door_bamboo")) return;
        });
    }


    @SubscribeEvent
    public static void remapEntities(MissingMappingsEvent event) {
        event.getMappings(Registries.PAINTING_VARIANT, ModRef.ID).forEach(mapping -> {
            String key = mapping.getKey().getPath();

            for (String wood : SetSetup.ALL_WOODS) {
                if (key.startsWith("mirror_" + wood)) {
                    mapping.remap(ForgeRegistries.PAINTING_VARIANTS.getValue(ModRef.res(key.replaceAll("mirror_" + wood, wood + "_mirror"))));
                }
            }
        });
    }

    private static boolean remap(MissingMappingsEvent.Mapping<Block> mapping, String key, String from, String to) {
        if (key.startsWith(from)) { // Using .contains() will result in blue/light_blue collision. eg. light_blue_dark_prismarine -> light_dark_prismarine_blue
            mapping.remap(getBlock(key.replaceAll(from, to)));
            return true;
        }
        return false;
    }

    private static Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(ModRef.res(name));
    }
}
