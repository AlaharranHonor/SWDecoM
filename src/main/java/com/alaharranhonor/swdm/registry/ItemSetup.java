package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWDM.MOD_ID);

    public static final RegistryObject<Item> CHANGE_TOOL = ITEMS.register("change_tool", () -> new Item(new Item.Properties().tab(SWDM.TAB)));

    public static void init(IEventBus modBus) {
        BlockSetup.MANUAL_BLOCKS.values().forEach(block -> {
            ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(SWDM.TAB)));
        });

        ITEMS.register(modBus);
    }
}
