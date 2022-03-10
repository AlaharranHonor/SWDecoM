package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWDMPaintings;
import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Mod("swdm")
public class SWDM
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "swdm";
    public static WoodType THATCH_WT;
    public static WoodType BAMBOO_WT;
    public static final List<String> STONE_TYPES = new ArrayList(Arrays.asList("mossy", "cracked", "more_mossy"));
    public static final HashMap<String, List<String>> STONE_SETS = new HashMap() {{
        put("color", Arrays.asList("dark_prismarine", "glass_brick", "concrete", "terracotta"));
        put("with_default", Arrays.asList("light_bricks", "light_stone_bricks", "medium_bricks", "medium_stone_bricks", "dark_bricks", "dark_stone_bricks"));
        put("no_default", Arrays.asList("stone", "andesite", "granite", "diorite"));
    }};

    public static final HashMap<String, List<String>> WOOD_SETS = new HashMap() {{

    }};

    // Block, Stair, Slab, Wall.
    public static final HashMap<String, HashMap<String, AbstractBlock.Properties>> SSW_SETS = new HashMap() {{
        put("color", new HashMap() {{
            put("metal_roof", Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE));
            put("tile_roof", AbstractBlock.Properties.copy(Blocks.CLAY));
            put("shingle_roof", Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE));
            put("stained_glass", AbstractBlock.Properties.copy(Blocks.BLACK_STAINED_GLASS));
            put("wool", AbstractBlock.Properties.copy(Blocks.BLACK_WOOL));
            put("pastel_wool", AbstractBlock.Properties.copy(Blocks.BLACK_WOOL));
            put("tinted_wool", AbstractBlock.Properties.copy(Blocks.BLACK_WOOL));
        }});
        put("color_carpet", new HashMap() {{
            put("pastel_wool", AbstractBlock.Properties.copy(Blocks.BLACK_WOOL));
            put("tinted_wool", AbstractBlock.Properties.copy(Blocks.BLACK_WOOL));
        }});
        put("wv", new HashMap() {{
            put("log", AbstractBlock.Properties.copy(Blocks.OAK_LOG));
            put("stripped_log", AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG));
        }});
        put("wv_beam", new HashMap() {{
            put("log", AbstractBlock.Properties.copy(Blocks.OAK_LOG));
            put("stripped_log", AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG));
        }});
        put("wv-whitewash", new HashMap() {{

        }});
    }};

    public SWDM() {
        THATCH_WT = WoodType.register(WoodType.create("swdm:thatch"));
        BAMBOO_WT = WoodType.register(WoodType.create("swdm:bamboo"));
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        BlockInit.init();
        SWDMTileEntities.init(modEventBus);
        SWDMPaintings.init(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation("swdm", "acacia_leaves_slab")), 0.3F);
            /*ComposterBlock.COMPOSTABLES.put(BlockInit.ACACIA_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.ACACIA_LEAVES_WALL_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.BIRCH_LEAVES_SLAB_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.BIRCH_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.BIRCH_LEAVES_WALL_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.DARK_OAK_LEAVES_SLAB_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.DARK_OAK_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.DARK_OAK_LEAVES_WALL_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.JUNGLE_LEAVES_SLAB_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.JUNGLE_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.JUNGLE_LEAVES_WALL_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.OAK_LEAVES_SLAB_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.OAK_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.OAK_LEAVES_WALL_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.SPRUCE_LEAVES_SLAB_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.SPRUCE_LEAVES_STAIRS_ITEM.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(BlockInit.SPRUCE_LEAVES_WALL_ITEM.get(), 0.3F);*/

            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_BLACK.get(), SWEMInit.METER_POINT_BLACK.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_BLUE_GRAY.get(), SWEMInit.METER_POINT_BLUE_GRAY.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_BROWN.get(), SWEMInit.METER_POINT_BROWN.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_DARK_BROWN.get(), SWEMInit.METER_POINT_DARK_BROWN.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_DUSTED_GRAY.get(), SWEMInit.METER_POINT_DUSTED_GRAY.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_GOLDEN.get(), SWEMInit.METER_POINT_GOLDEN.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_GRAY.get(), SWEMInit.METER_POINT_GRAY.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_LIGHT_GRAY.get(), SWEMInit.METER_POINT_LIGHT_GRAY.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_MUTED_BROWN.get(), SWEMInit.METER_POINT_MUTED_BROWN.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_VIVID_RED.get(), SWEMInit.METER_POINT_VIVID_RED.defaultBlockState());
            ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_WHITE.get(), SWEMInit.METER_POINT_WHITE.defaultBlockState());
        });
    }

    public static final ItemGroup SWDMTAB = new ItemGroup("swdmtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockInit.STONE_WALL.get());
        }
        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImage(new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tab_item_search.png"));
}
