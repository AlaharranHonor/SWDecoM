package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWDMBlockEntities;
import com.alaharranhonor.swdm.util.init.SWDMPaintings;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Mod("swdm")
public class SWDM
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "swdm";
    public static WoodType THATCH_WT;
    public static WoodType BAMBOO_WT;
    public static final List<String> STONE_TYPES = new ArrayList(Arrays.asList("mossy", "cracked", "more_mossy"));
    public static final List<String> LMD_TYPES = new ArrayList(Arrays.asList("light", "medium", "dark"));
    public static final List<String> CUSTOM_COLORS = new ArrayList<>(Arrays.asList("sage", "golden"));
    public static final List<String> NATURAL_TONES = new ArrayList<>(Arrays.asList("dark_brown", "brown", "muted_brown", "vivid_red", "tuscan", "golden", "pale", "white", "dusted_gray", "light_gray", "blue_gray", "gray", "black"));
    public static final HashMap<String, HashMap<String, Block.Properties>> STONE_SETS = new HashMap() {{
        put("color", new HashMap() {{
            put("dark_prismarine", Block.Properties.copy(Blocks.PRISMARINE_BRICKS));
            put("glass_bricks", Block.Properties.copy(Blocks.GLASS));
            put("concrete", Block.Properties.copy(Blocks.BLACK_CONCRETE));
            put("terracotta", Block.Properties.copy(Blocks.TERRACOTTA));
        }});
        put("color_custom", new HashMap() {{
            put("terracotta", Block.Properties.copy(Blocks.TERRACOTTA));
        }});
        put("standard", new HashMap() {{
            put("stone", Block.Properties.copy(Blocks.STONE));
            put("andesite", Block.Properties.copy(Blocks.ANDESITE));
            put("granite", Block.Properties.copy(Blocks.GRANITE));
            put("diorite", Block.Properties.copy(Blocks.DIORITE));
        }});
        put("lmd", new HashMap() {{
            put("brick", Block.Properties.copy(Blocks.BRICKS));
            put("stone_brick", Block.Properties.copy(Blocks.BRICKS));
        }});
        put("lmd-only", new HashMap() {{
            put("stone", Block.Properties.copy(Blocks.STONE));
        }});
    }};

    public static final HashMap<String, List<String>> WOOD_SETS = new HashMap() {{

    }};

    // Block, Stair, Slab, Wall.
    public static final HashMap<String, HashMap<String, Block.Properties>> SSW_SETS = new HashMap() {{
        put("color", new HashMap() {{
            put("metal_roof", Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).sound(SoundType.METAL)); //.harvestTool(ToolType.PICKAXE)
            put("tile_roof", Block.Properties.copy(Blocks.CLAY));
            put("shingle_roof", Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(2.0F, 3.0F).sound(SoundType.STONE)); //.harvestTool(ToolType.AXE)
            put("stained_glass", Block.Properties.copy(Blocks.BLACK_STAINED_GLASS));
            put("wool", Block.Properties.copy(Blocks.BLACK_WOOL));
            put("pastel_wool", Block.Properties.copy(Blocks.BLACK_WOOL));
            put("tinted_wool", Block.Properties.copy(Blocks.BLACK_WOOL));
        }});
        put("wv", new HashMap() {{
            put("log", Block.Properties.copy(Blocks.OAK_SLAB));
            put("stripped_log", Block.Properties.copy(Blocks.OAK_SLAB));
        }});
        put("wv-whitewash", new HashMap() {{
            put("leaves", Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((p_235441_0_, p_235441_1_, p_235441_2_, p_235441_3_) -> p_235441_3_ == EntityType.OCELOT || p_235441_3_ == EntityType.PARROT).isSuffocating((p, p1, p2) -> false).isViewBlocking((p, p1, p2) -> false));
        }});
        put("lmd", new HashMap() {{
           put("screen", Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).noOcclusion()); //.harvestTool(ToolType.PICKAXE)
        }});
        put("lmd-color", new HashMap() {{
            put("siding", Block.Properties.copy(Blocks.OAK_PLANKS));
        }});
    }};

    public SWDM() {
        THATCH_WT = WoodType.register(WoodType.create("swdm:thatch"));
        BAMBOO_WT = WoodType.register(WoodType.create("swdm:bamboo"));
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        BlockInit.init();
        SWDMBlockEntities.init(modEventBus);
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

            if (ModList.get().isLoaded("swem")) {
                for (int i = 0; i < NATURAL_TONES.size(); i++) {
                    ShovelItem.FLATTENABLES.put(BlockInit.SANDSTONE_BLOCKS.get(i).get(), SWEMInit.METER_POINTS.get(i).defaultBlockState());
                }
                ShovelItem.FLATTENABLES.put(Blocks.RED_SANDSTONE, SWEMInit.METER_POINT_RED_SANDSTONE.defaultBlockState());
            }

        });
    }

    public static final CreativeModeTab SWDMTAB = new CreativeModeTab("swdmtab") {
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
