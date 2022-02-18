package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.client.renderers.ClockTileRenderer;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SWEMonClientSetup(event);

        Atlases.addWoodType(SWDM.THATCH_WT);
        Atlases.addWoodType(SWDM.BAMBOO_WT);
        ClientRegistry.bindTileEntityRenderer(SWDMTileEntities.SWDM_SIGN.get(), SignTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(SWDMTileEntities.CLOCK.get(), ClockTileRenderer::new);

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
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_WALL.get(), RenderType.translucent());
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
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OAK_TRAPDOOR_SPRUCE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_ACACIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_BIRCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_DARK_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_JUNGLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_TRAPDOOR_OAK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_SCREEN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_SCREEN_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_SCREEN_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_SCREEN_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_SCREEN_GLASS_PANE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MEDIUM_SCREEN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MEDIUM_SCREEN_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MEDIUM_SCREEN_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MEDIUM_SCREEN_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MEDIUM_SCREEN_GLASS_PANE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_SCREEN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_SCREEN_SLAB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_SCREEN_STAIRS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_SCREEN_WALL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_SCREEN_GLASS_PANE.get(), RenderType.cutout());
    }

    public static void SWEMonClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("swem")) {
            RenderTypeLookup.setRenderLayer(SWEMInit.WHITEWASH_LADDER, RenderType.cutout());
        }
    }
}
