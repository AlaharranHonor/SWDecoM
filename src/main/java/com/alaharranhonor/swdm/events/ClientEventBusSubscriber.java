package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.entity.InvisibleItemFrameRenderer;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.EntitySetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.registry.WoodSetup;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event) {
        ClientEventBusSubscriber.setRenderLayers();
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(GenType::setupClient));

        Sheets.addWoodType(WoodSetup.WHITEWASH);
        Sheets.addWoodType(WoodSetup.THATCH);
        //Sheets.addWoodType(WoodSetup.BAMBOO);
    }

    @SubscribeEvent
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockSetup.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        event.registerEntityRenderer(EntitySetup.INVISIBLE_ITEM_FRAME.get(), InvisibleItemFrameRenderer::new);
        //event.registerEntityRenderer(EntitySetup.MIRROR_PAINTING.get(), PaintingRenderer::new);
    }

    public static void setRenderLayers() {
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(type -> type.setRenderType(set.getRenderType().get())));
    }
}
