package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.gentypes.block.MeterPointGen;
import com.alaharranhonor.swdm.registry.MenuSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.workshop.DecoWorkshopScreen;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModBusHandler {

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            MeterPointGen.METER_POINT_FLATTENABLES.entrySet().forEach(entry -> {
                ShovelItem.FLATTENABLES.put(entry.getKey().get(), entry.getValue().get().defaultBlockState());
            });
        }
    }

    @Mod.EventBusSubscriber(modid = ModRef.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusHandler {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuSetup.DECO_WORKSHOP.get(), DecoWorkshopScreen::new);
        }

        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors colors = event.getBlockColors();
            SetSetup.SETS.forEach(set -> {
                if (set.getBlockColors() != null) {
                    set.genTypes.forEach(type -> type.registerBlockColors(colors, set.getBlockColors()));
                }
            });
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            ItemColors colors = event.getItemColors();
            SetSetup.SETS.forEach(set -> {
                if (set.getBlockColors() != null) {
                    set.genTypes.forEach(type -> type.registerItemColors(colors, set.getItemColors()));
                }
            });
        }
    }
}
