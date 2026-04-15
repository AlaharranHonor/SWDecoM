package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.network.SBCreateMultiDoorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class PacketSetup {

    @SubscribeEvent
    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        // Serverbound
        registrar.playToServer(SBCreateMultiDoorItem.TYPE, SBCreateMultiDoorItem.STREAM_CODEC, SBCreateMultiDoorItem::handle);

        // Clientbound
    }

}
