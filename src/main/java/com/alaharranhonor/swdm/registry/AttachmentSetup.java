package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;

public class AttachmentSetup {

    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ModRef.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MultiDoorData>> MULTI_DOOR = REGISTRY.register("multi_door", () -> AttachmentType.builder(() -> new MultiDoorData(1, 1, Collections.emptyList())).serialize(MultiDoorData.CODEC).sync(MultiDoorData.STREAM_CODEC).build());

    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);
    }

}
