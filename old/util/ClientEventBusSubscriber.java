package com.alaharranhonor.swdm.old.util;

import com.alaharranhonor.swdm.old.SWDM;
import com.alaharranhonor.swdm.old.util.init.BlockInit;
import com.alaharranhonor.swdm.old.util.init.SWDMBlockEntities;
import com.alaharranhonor.swdm.old.util.init.SWEMInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
        if (ModList.get().isLoaded("geckolib3")) {
            //event.registerBlockEntityRenderer(ModEventBusSubscriber.CLOCK_TE, ClockTileRenderer::new);
        }

        event.registerBlockEntityRenderer(SWDMBlockEntities.SWDM_SIGN.get(), SignRenderer::new);
    }

    public static void setRenderLayers() {
        SWDM.SSWT_SETS.get("lmd").forEach((type, props) -> {
            BlockInit.SSWT_SET_BLOCKS.get("lmd", type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSWT_SET_SLABS.get("lmd", type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSWT_SET_STAIRS.get("lmd", type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSWT_SET_WALLS.get("lmd", type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSWT_SET_GLASS_PANES.get("lmd", type).forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        });

        BlockInit.COATED_CHAINS.values().forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.cutout()));
        BlockInit.PYLONS.values().forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.cutout()));
        BlockInit.FIBER_CARPETS.values().forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.cutout()));

        BlockInit.SSWT_SET_SLABS.get("wv-extra", "leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_STAIRS.get("wv-extra", "leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_WALLS.get("wv-extra", "leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_TRAPDOORS.get("wv-extra", "leaves").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));

        BlockInit.SSWT_SET_SLABS.get("color", "stained_glass").forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.translucent()));
        BlockInit.SSWT_SET_STAIRS.get("color", "stained_glass").forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.translucent()));
        BlockInit.SSWT_SET_WALLS.get("color", "stained_glass").forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.translucent()));
        BlockInit.SSWT_SET_TRAPDOORS.get("color", "stained_glass").forEach(rb -> ItemBlockRenderTypes.setRenderLayer(rb.get(), RenderType.translucent()));

        BlockInit.SSWT_SET_SLABS.get("standalone", "glass").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_STAIRS.get("standalone", "glass").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_WALLS.get("standalone", "glass").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSWT_SET_TRAPDOORS.get("standalone", "glass").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));

        BlockInit.SSWT_SET_SLABS.get("standalone", "grass_block").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped()));
        BlockInit.SSWT_SET_STAIRS.get("standalone", "grass_block").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped()));
        BlockInit.SSWT_SET_WALLS.get("standalone", "grass_block").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped()));
        BlockInit.SSWT_SET_TRAPDOORS.get("standalone", "grass_block").forEach((b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped()));
    }

    public static void SWEMonClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("swem")) {
            ItemBlockRenderTypes.setRenderLayer(SWEMInit.WHITEWASH_LADDER, RenderType.cutout());
        }
    }
}
