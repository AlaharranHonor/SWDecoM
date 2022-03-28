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

        if (ModList.get().isLoaded("geckolib3"))
            ClientRegistry.bindTileEntityRenderer(ModEventBusSubscriber.CLOCK_TE, ClockTileRenderer::new);

        ClientEventBusSubscriber.setRenderLayers();

    }

    public static void setRenderLayers() {
        SWDM.SSW_SETS.get("lmd").forEach((type, props) -> {
            BlockInit.SSW_SET_BLOCKS.get("lmd").get(type).forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_SLABS.get("lmd").get(type).forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_STAIRS.get("lmd").get(type).forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_WALLS.get("lmd").get(type).forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
            BlockInit.SSW_SET_GLASS_PANES.get("lmd").get(type).forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        });

        for (DyeColor color : DyeColor.values()) {
            RenderTypeLookup.setRenderLayer(BlockInit.COATED_CHAINS.get(color.getId()).get(), RenderType.cutout());
        }

        RenderTypeLookup.setRenderLayer(BlockInit.GRASS_SLAB.get(), RenderType.cutoutMipped());

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
        for (int i = 0; i < SWDM.NATURAL_TONES.size(); i++) {
            RenderTypeLookup.setRenderLayer(BlockInit.FIBER_CARPETS.get(i).get(), RenderType.cutout());
        }
        RenderTypeLookup.setRenderLayer(BlockInit.FIBER_CARPET_RED_SAND.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.FIBER_CARPET_SAND.get(), RenderType.cutout());


        BlockInit.SSW_SET_SLABS.get("wv-whitewash").get("leaves").forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get("leaves").forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        BlockInit.SSW_SET_WALLS.get("wv-whitewash").get("leaves").forEach((b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));

        List<WoodType> woodTypes = WoodType.values().collect(Collectors.toList());
        // 6 includes oak, spruce, birch, acacia, jungle and dark_oak

        for (int i = 0; i < 6; i++) {
            RenderTypeLookup.setRenderLayer(BlockInit.LEAVES_TRAPDOORS.get(i).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.LADDERS.get(i).get(), RenderType.cutout());
        }

        // Dye Colours
        for (int i = 0; i < DyeColor.values().length; i++) {
            RenderTypeLookup.setRenderLayer(BlockInit.SSW_SET_SLABS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(BlockInit.SSW_SET_STAIRS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(BlockInit.SSW_SET_WALLS.get("color").get("stained_glass").get(i).get(), RenderType.translucent());
        }
    }

    public static void SWEMonClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("swem")) {
            RenderTypeLookup.setRenderLayer(SWEMInit.WHITEWASH_LADDER, RenderType.cutout());
        }
    }
}
