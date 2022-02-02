package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.blocks.BeamBlock;
import com.alaharranhonor.swlm.SWLM;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SWEMInit {


    public static Block METER_POINT_BLACK;
    public static Block METER_POINT_BLUE_GRAY;
    public static Block METER_POINT_BROWN;
    public static Block METER_POINT_DARK_BROWN;
    public static Block METER_POINT_DUSTED_GRAY;
    public static Block METER_POINT_GOLDEN;
    public static Block METER_POINT_GRAY;
    public static Block METER_POINT_LIGHT_GRAY;
    public static Block METER_POINT_MUTED_BROWN;
    public static Block METER_POINT_VIVID_RED;
    public static Block METER_POINT_WHITE;
    public static Block WHITEWASH_LADDER;
    public static Block WHITEWASH_PLANK_WALL;
    public static Block WHITEWASH_LOG_WALL;
    public static Block WHITEWASH_PLANK_BEAM;
    public static Block WHITEWASH_LOG_BEAM;

    /* Blocks are intialized first.
     * Store the reference to the block, and then in the item initialization register the items and block items.
     */
    @SubscribeEvent
    public static void intializeModBlocks(RegistryEvent.Register<Block> item) {
        if (ModList.get().isLoaded("swem")) {

            METER_POINT_BLACK = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_black");
            item.getRegistry().register(METER_POINT_BLACK);
            METER_POINT_BLUE_GRAY = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_blue_gray");
            item.getRegistry().register(METER_POINT_BLUE_GRAY);
            METER_POINT_BROWN = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_brown");
            item.getRegistry().register(METER_POINT_BROWN);
            METER_POINT_DARK_BROWN = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_dark_brown");
            item.getRegistry().register(METER_POINT_DARK_BROWN);
            METER_POINT_DUSTED_GRAY = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_dusted_gray");
            item.getRegistry().register(METER_POINT_DUSTED_GRAY);
            METER_POINT_GOLDEN = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_golden");
            item.getRegistry().register(METER_POINT_GOLDEN);
            METER_POINT_GRAY = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_gray");
            item.getRegistry().register(METER_POINT_GRAY);
            METER_POINT_LIGHT_GRAY = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_light_gray");
            item.getRegistry().register(METER_POINT_LIGHT_GRAY);
            METER_POINT_MUTED_BROWN = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_muted_brown");
            item.getRegistry().register(METER_POINT_MUTED_BROWN);
            METER_POINT_VIVID_RED = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_vivid_red");
            item.getRegistry().register(METER_POINT_VIVID_RED);
            METER_POINT_WHITE = new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE)).setRegistryName("meter_point_white");
            item.getRegistry().register(METER_POINT_WHITE);
            /* Breaks the mod, making it require swem
            WHITEWASH_LADDER = new LadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER)).setRegistryName("whitewash_ladder");
            item.getRegistry().register(WHITEWASH_LADDER);
            WHITEWASH_PLANK_WALL = new WallBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_plank_wall");
            item.getRegistry().register(WHITEWASH_PLANK_WALL);
            WHITEWASH_LOG_WALL = new WallBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_log_wall");
            item.getRegistry().register(WHITEWASH_LOG_WALL); */
            WHITEWASH_PLANK_BEAM = new BeamBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_plank_beam");
            item.getRegistry().register(WHITEWASH_PLANK_BEAM);
            WHITEWASH_LOG_BEAM = new BeamBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setRegistryName("whitewash_log_beam");
            item.getRegistry().register(WHITEWASH_LOG_BEAM);

        }
    }

    @SubscribeEvent
    public static void intializeModItems(RegistryEvent.Register<Item> item) {
        if (ModList.get().isLoaded("swem")) {
            // Register stand alone items.
            //item.getRegistry().register(new MedicalItem(new Item.Properties().tab(SWDM.SWDMTAB), 20, 0).setRegistryName("test_item"));

            // Register block items.
            item.getRegistry().register(new BlockItem(METER_POINT_BLACK, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_BLACK.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_BLUE_GRAY, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_BLUE_GRAY.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_BROWN, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_BROWN.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_DARK_BROWN, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_DARK_BROWN.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_DUSTED_GRAY, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_DUSTED_GRAY.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_GOLDEN, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_GOLDEN.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_GRAY, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_GRAY.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_LIGHT_GRAY, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_LIGHT_GRAY.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_MUTED_BROWN, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_MUTED_BROWN.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_VIVID_RED, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_VIVID_RED.getRegistryName()));
            item.getRegistry().register(new BlockItem(METER_POINT_WHITE, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(METER_POINT_WHITE.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LADDER, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LADDER.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_PLANK_WALL, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_PLANK_WALL.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LOG_WALL, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LOG_WALL.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_PLANK_BEAM, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_PLANK_BEAM.getRegistryName()));
            item.getRegistry().register(new BlockItem(WHITEWASH_LOG_BEAM, new Item.Properties().tab(SWDM.SWDMTAB)).setRegistryName(WHITEWASH_LOG_BEAM.getRegistryName()));

        }
    }


}