package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.block.ShelfBlock;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID)
public class ForgeEventBusSubscriber {

    @SubscribeEvent
    public static void cycleShelfBlock(PlayerInteractEvent.RightClickBlock event) {
        Player pPlayer = event.getPlayer();
        InteractionHand pHand = event.getHand();
        if (!pPlayer.isShiftKeyDown() || !pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return;
        }

        BlockState pState = event.getWorld().getBlockState(event.getPos());
        if (!(pState.getBlock() instanceof ShelfBlock)) return;

        int stateOrdinal = pState.getValue(ShelfBlock.SHELF_TYPE).ordinal();
        int height = stateOrdinal / 3;
        int pos = stateOrdinal % 3;
        height = (height + 1) % 3;

        stateOrdinal = height * 3 + pos;
        stateOrdinal = stateOrdinal % SWDMBlockstateProperties.ShelfType.values().length;

        event.getWorld().setBlock(event.getPos(), pState.setValue(ShelfBlock.SHELF_TYPE, SWDMBlockstateProperties.ShelfType.values()[stateOrdinal]), 3);
    }

    @SubscribeEvent
    public static void remapBlocks(RegistryEvent.MissingMappings<Block> event) {
        event.getAllMappings().forEach(mapping -> {
            if (!mapping.key.getNamespace().equals(SWDM.MOD_ID) && !mapping.key.getNamespace().equals("swlm")) return;
            String key = mapping.key.getPath();

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
    public static void remapEntities(RegistryEvent.MissingMappings<Motive> event) {
        event.getMappings(SWDM.MOD_ID).forEach(mapping -> {
            String key = mapping.key.getPath();

            for (String wood : SetSetup.ALL_WOODS) {
                if (key.startsWith("mirror_" + wood)) {
                    mapping.remap(ForgeRegistries.PAINTING_TYPES.getValue(SWDM.res(key.replaceAll("mirror_" + wood, wood + "_mirror"))));
                }
            }
        });
    }

    private static boolean remap(RegistryEvent.MissingMappings.Mapping<Block> mapping, String key, String from, String to) {
        if (key.startsWith(from)) { // Using .contains() will result in blue/light_blue collision. eg. light_blue_dark_prismarine -> light_dark_prismarine_blue
            mapping.remap(getBlock(key.replaceAll(from, to)));
            return true;
        }
        return false;
    }

    private static Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(SWDM.res(name));
    }
}
