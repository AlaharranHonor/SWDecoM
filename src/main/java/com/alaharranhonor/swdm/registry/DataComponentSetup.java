package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.multidoor.HingeData;
import com.alaharranhonor.swdm.multidoor.MultiDoorData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentSetup {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ModRef.ID);

   public static final DeferredHolder<DataComponentType<?>, DataComponentType<MultiDoorData>> MULTI_DOOR = REGISTRY.registerComponentType("multi_door", builder -> builder.persistent(MultiDoorData.CODEC).networkSynchronized(MultiDoorData.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<HingeData>> HINGE_SELECTION = REGISTRY.registerComponentType("hinge", builder -> builder.persistent(HingeData.CODEC).networkSynchronized(HingeData.STREAM_CODEC));

    public static void init(IEventBus bus) {
        REGISTRY.register(bus);
    }

}
