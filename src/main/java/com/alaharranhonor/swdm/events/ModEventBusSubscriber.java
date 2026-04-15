package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.gentypes.block.MeterPointGen;
import com.alaharranhonor.swdm.multidoor.DoorHingeScreen;
import com.alaharranhonor.swdm.registry.MenuSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.workshop.DecoWorkshopScreen;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModEventBusSubscriber {

    @EventBusSubscriber
    public static class CommonModBusHandler {

        @SubscribeEvent
        private static void commonSetup(FMLCommonSetupEvent event) {
            MeterPointGen.METER_POINT_FLATTENABLES.entrySet().forEach(entry -> {
                ShovelItem.FLATTENABLES.put(entry.getKey().get(), entry.getValue().get().defaultBlockState());
            });
        }
    }
}
