package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.BeamBlock;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SWEMInit {

    public static HashMap<String, Block> METER_POINTS = new HashMap<>();
    public static Block METER_POINT_RED_SANDSTONE;
    public static Block WHITEWASH_LADDER;
    public static Block WHITEWASH_PLANK_WALL;
    public static Block WHITEWASH_LOG_WALL;
    public static Block WHITEWASH_PLANK_BEAM;
    public static Block WHITEWASH_LOG_BEAM;
    public static Motive MIRROR_WHITEWASH_16x16;
    public static Motive MIRROR_WHITEWASH_16x32;
    public static Motive MIRROR_WHITEWASH_32x16;
    public static Motive MIRROR_WHITEWASH_32x32;
    public static Motive MIRROR_WHITEWASH_64x32;
    public static Motive MIRROR_WHITEWASH_64x48;
    public static Motive MIRROR_WHITEWASH_64x64;


    /* Blocks are intialized first.
     * Store the reference to the block, and then in the item initialization register the items and block items.
     */
    @SubscribeEvent
    public static void intializeModBlocks(RegistryEvent.Register<Block> item) {
        if (ModList.get().isLoaded("swem")) {
            for (String tone : SWDM.NATURAL_TONES) {
                Block meterPoint = new Block(Block.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_" + tone);
                item.getRegistry().register(meterPoint);
                METER_POINTS.put(tone, meterPoint);
            }
            METER_POINT_RED_SANDSTONE = new Block(Block.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_red_sandstone");
            item.getRegistry().register(METER_POINT_RED_SANDSTONE);
            // Breaks the mod, making it require swem
            WHITEWASH_LADDER = new LadderBlock(Block.Properties.copy(Blocks.LADDER)).setRegistryName("whitewash_ladder");
            item.getRegistry().register(WHITEWASH_LADDER);
            WHITEWASH_PLANK_WALL = new WallBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_plank_wall");
            item.getRegistry().register(WHITEWASH_PLANK_WALL);
            WHITEWASH_LOG_WALL = new WallBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_log_wall");
            item.getRegistry().register(WHITEWASH_LOG_WALL);
            WHITEWASH_PLANK_BEAM = new BeamBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_plank_beam");
            item.getRegistry().register(WHITEWASH_PLANK_BEAM);
            WHITEWASH_LOG_BEAM = new BeamBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_log_beam");
            item.getRegistry().register(WHITEWASH_LOG_BEAM);

        }
    }

    @SubscribeEvent
    public static void intializeModPaintings(RegistryEvent.Register<Motive> item) {
        if (ModList.get().isLoaded("swem")) {
            MIRROR_WHITEWASH_16x16 = new Motive(16, 16).setRegistryName("mirror_whitewash_16x16");
            item.getRegistry().register(MIRROR_WHITEWASH_16x16);
            MIRROR_WHITEWASH_16x32 = new Motive(16, 32).setRegistryName("mirror_whitewash_16x32");
            item.getRegistry().register(MIRROR_WHITEWASH_16x32);
            MIRROR_WHITEWASH_32x16 = new Motive(32, 16).setRegistryName("mirror_whitewash_32x16");
            item.getRegistry().register(MIRROR_WHITEWASH_32x16);
            MIRROR_WHITEWASH_32x32 = new Motive(32, 32).setRegistryName("mirror_whitewash_32x32");
            item.getRegistry().register(MIRROR_WHITEWASH_32x32);
            MIRROR_WHITEWASH_64x32 = new Motive(64, 32).setRegistryName("mirror_whitewash_64x32");
            item.getRegistry().register(MIRROR_WHITEWASH_64x32);
            MIRROR_WHITEWASH_64x48 = new Motive(64, 48).setRegistryName("mirror_whitewash_64x48");
            item.getRegistry().register(MIRROR_WHITEWASH_64x48);
            MIRROR_WHITEWASH_64x64 = new Motive(64, 64).setRegistryName("mirror_whitewash_64x64");
            item.getRegistry().register(MIRROR_WHITEWASH_64x64);
        }
    }

    @SubscribeEvent
    public static void intializeModItems(RegistryEvent.Register<Item> item) {
        if (ModList.get().isLoaded("swem")) {
            // Register stand alone items.
            //item.getRegistry().register(new MedicalItem(new Item.Properties().tab(SWDM.SWDMTAB), 20, 0).setRegistryName("test_item"));

            // Register block items.
            for (String tone : SWDM.NATURAL_TONES) {
                item.getRegistry().register(new BlockItem(METER_POINTS.get(tone), new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINTS.get(tone).getRegistryName()));
            }

            item.getRegistry().register(new BlockItem(METER_POINT_RED_SANDSTONE, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_RED_SANDSTONE.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LADDER, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LADDER.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_PLANK_WALL, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_PLANK_WALL.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LOG_WALL, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LOG_WALL.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_PLANK_BEAM, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_PLANK_BEAM.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LOG_BEAM, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LOG_BEAM.getRegistryName()));

        }
    }


}