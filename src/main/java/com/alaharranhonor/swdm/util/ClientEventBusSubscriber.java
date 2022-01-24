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
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_LEAVES_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_LEAVES_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_LEAVES_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BLACK_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLUE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BROWN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CYAN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GRAY_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GREEN_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_BLUE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_GRAY_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIME_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.MAGENTA_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.ORANGE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PINK_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PURPLE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.RED_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.WHITE_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.YELLOW_STAINED_GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLACK_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLUE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BROWN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CYAN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GRAY_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GREEN_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_BLUE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_GRAY_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIME_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.MAGENTA_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.ORANGE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PINK_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PURPLE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.RED_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.WHITE_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.YELLOW_STAINED_GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLACK_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLUE_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BROWN_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CYAN_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GRAY_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GREEN_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_BLUE_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_GRAY_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIME_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.MAGENTA_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.ORANGE_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PINK_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PURPLE_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.RED_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.WHITE_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.YELLOW_STAINED_GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_LADDER.get(), RenderType.cutout());
    }
}
