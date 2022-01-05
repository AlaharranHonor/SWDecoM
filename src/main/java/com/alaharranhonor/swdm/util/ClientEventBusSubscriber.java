package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_ACACIA_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BIRCH_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_DARK_OAK_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_JUNGLE_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_OAK_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_SPRUCE_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_ACACIA_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BIRCH_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_DARK_OAK_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_JUNGLE_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_OAK_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_SPRUCE_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_ACACIA_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BIRCH_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_DARK_OAK_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_JUNGLE_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_OAK_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_SPRUCE_LEAVES_SLAB.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BLACK_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BLUE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BROWN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_CYAN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_GRAY_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_GREEN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIGHT_BLUE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIGHT_GRAY_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIME_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_MAGENTA_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_ORANGE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_PINK_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_PURPLE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_RED_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_WHITE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_YELLOW_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BLACK_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BLUE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_BROWN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_CYAN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_GRAY_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_GREEN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIGHT_BLUE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIGHT_GRAY_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_LIME_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_MAGENTA_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_ORANGE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_PINK_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_PURPLE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_RED_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_WHITE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SWDM_YELLOW_STAINED_GLASS_STAIRS.get(), RenderType.translucent());

    }
}
