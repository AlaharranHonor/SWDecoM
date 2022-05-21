package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.client.renderers.ClockTileRenderer;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWDMBlockEntities;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SWEMonClientSetup(event);


        Sheets.addWoodType(SWDM.THATCH_WT);
        Sheets.addWoodType(SWDM.BAMBOO_WT);


        ClientEventBusSubscriber.setRenderLayers();

    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        if (ModList.get().isLoaded("geckolib3"))
            event.registerBlockEntityRenderer(ModEventBusSubscriber.CLOCK_TE, ClockTileRenderer::new);

        event.registerBlockEntityRenderer(SWDMBlockEntities.SWDM_SIGN.get(), SignRenderer::new);
    }

    public static void setRenderLayers() {
        SWDM.SSW_SETS.get("lmd").forEach((type, props) -> {
            BlockInit.SSW_SET_BLOCKS.get("lmd").get(type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_SLABS.get("lmd").get(type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_STAIRS.get("lmd").get(type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_WALLS.get("lmd").get(type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_GLASS_PANES.get("lmd").get(type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        });

        for (DyeColor color : DyeColor.values()) {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.COATED_CHAINS.get(color.getId()).get(), RenderType.cutout());
        }

        ItemBlockRenderTypes.setRenderLayer(BlockInit.GRASS_SLAB.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.GLASS_STAIRS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.GLASS_SLAB.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.GLASS_WALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.THATCH_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BAMBOO_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BAMBOO_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.THATCH_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BAMBOO_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.THATCH_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OAK_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OAK_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OAK_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OAK_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OAK_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_OAK.get(), RenderType.cutout());
        for (int i = 0; i < SWDM.NATURAL_TONES.size(); i++) {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.FIBER_CARPETS.get(i).get(), RenderType.cutout());
        }
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FIBER_CARPET_RED_SAND.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FIBER_CARPET_SAND.get(), RenderType.cutout());


        BlockInit.SSW_SET_SLABS.get("wv-whitewash").get("leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get("leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSW_SET_WALLS.get("wv-whitewash").get("leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));

        List<WoodType> woodTypes = WoodType.values().collect(Collectors.toList());
        // 6 includes oak, spruce, birch, acacia, jungle and dark_oak

        for (int i = 0; i < 6; i++) {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.LEAVES_TRAPDOORS.get(i).get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.LADDERS.get(i).get(), RenderType.cutout());
        }

        // Dye Colours
        for (int i = 0; i < DyeColor.values().length; i++) {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.SSW_SET_SLABS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.SSW_SET_STAIRS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.SSW_SET_WALLS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
        }
    }

    public static void SWEMonClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("swem")) {
            ItemBlockRenderTypes.setRenderLayer(SWEMInit.WHITEWASH_LADDER, RenderType.cutout());
        }
    }
}
