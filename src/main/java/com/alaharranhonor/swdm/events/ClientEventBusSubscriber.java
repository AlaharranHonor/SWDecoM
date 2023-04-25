package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.entity.InvisibleItemFrameRenderer;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.EntitySetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.registry.WoodSetup;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClientEventBusSubscriber.setRenderLayers();
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(GenType::setupClient));

        Sheets.addWoodType(WoodSetup.WHITEWASH);
        Sheets.addWoodType(WoodSetup.BAMBOO);
        Sheets.addWoodType(WoodSetup.THATCH);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockSetup.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        event.registerEntityRenderer(EntitySetup.INVISIBLE_ITEM_FRAME.get(), InvisibleItemFrameRenderer::new);
        event.registerEntityRenderer(EntitySetup.MIRROR_PAINTING.get(), PaintingRenderer::new);
    }

    public static void setRenderLayers() {
        SetSetup.SETS.forEach(set -> set.genTypes.forEach(type -> type.setRenderType(set.getRenderType().get())));
    }
}
