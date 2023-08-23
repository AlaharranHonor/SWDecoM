package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabSetup {

    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModRef.ID);

    public static final RegistryObject<CreativeModeTab> TAB = REGISTRY.register("main", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.swdm.main"))
            .icon(() -> new ItemStack(ItemSetup.CHANGE_TOOL.get()))
            .withBackgroundLocation(ModRef.res("textures/gui/container/creative_inventory/tab_main.png"))
            .withSearchBar(55)
            .displayItems(((pParameters, pOutput) -> {
                ItemSetup.REGISTRY.getEntries().forEach(item -> pOutput.accept(item.get()));
            }))
            .build());

    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);
    }
}
