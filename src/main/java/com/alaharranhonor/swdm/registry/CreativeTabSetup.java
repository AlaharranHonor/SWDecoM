package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabSetup {

    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModRef.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = REGISTRY.register("main", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.swdm.main"))
            .icon(() -> new ItemStack(ItemSetup.CHANGE_TOOL.get()))
            .backgroundTexture(ModRef.res("textures/gui/container/creative_inventory/tab_main.png"))
            .withSearchBar(55)
            .displayItems(((pParameters, pOutput) -> {
                ItemSetup.REGISTRY.getEntries().forEach(item -> pOutput.accept(item.get()));
            }))
            .build());

    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);
    }
}
