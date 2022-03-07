package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.client.renderers.ClockTileRenderer;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWDMTileEntities;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SWEMonClientSetup(event);


        Atlases.addWoodType(SWDM.THATCH_WT);
        Atlases.addWoodType(SWDM.BAMBOO_WT);
        ClientRegistry.bindTileEntityRenderer(SWDMTileEntities.SWDM_SIGN.get(), SignTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(SWDMTileEntities.CLOCK.get(), ClockTileRenderer::new);

        ClientEventBusSubscriber.setRenderLayers();

    }

    public static void setRenderLayers() {
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GLASS_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BAMBOO_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.THATCH_TRAPDOOR.get(), RenderType.cutout());
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
        RenderTypeLookup.setRenderLayer(BlockInit.BLACK_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BLUE_GRAY_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BROWN_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DARK_BROWN_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DUSTED_GRAY_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.GOLDEN_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.GRAY_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_GRAY_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MUTED_BROWN_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.VIVID_RED_FIBER_CARPET.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WHITE_FIBER_CARPET.get(), RenderType.cutout());

        List<WoodType> woodTypes = WoodType.values().collect(Collectors.toList());
        // 6 includes oak, spruce, birch, acacia, jungle and dark_oak

        for (int i = 0; i < 6; i++) {
            RenderTypeLookup.setRenderLayer(BlockInit.LEAVES_SLABS.get(i).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.LEAVES_STAIRS.get(i).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.LEAVES_WALLS.get(i).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.LEAVES_TRAPDOORS.get(i).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.LADDERS.get(i).get(), RenderType.cutout());
        }

        // Dye Colours
        for (int i = 0; i < DyeColor.values().length; i++) {
            RenderTypeLookup.setRenderLayer(BlockInit.STAINED_GLASS_SLABS.get(i).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(BlockInit.STAINED_GLASS_STAIRS.get(i).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(BlockInit.STAINED_GLASS_WALLS.get(i).get(), RenderType.translucent());
        }
    }

    public static void SWEMonClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("swem")) {
            RenderTypeLookup.setRenderLayer(SWEMInit.WHITEWASH_LADDER, RenderType.cutout());
        }
    }
}
