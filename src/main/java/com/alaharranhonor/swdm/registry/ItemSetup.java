package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.item.InvisibleItemFrameItem;
import com.alaharranhonor.swdm.item.MirrorPaintingItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemSetup {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID);

    public static final RegistryObject<Item> DECO_WORKSHOP = REGISTRY.register("deco_workshop", () -> new BlockItem(BlockSetup.DECO_WORKSHOP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHANGE_TOOL = REGISTRY.register("change_tool", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INVISIBLE_ITEM_FRAME = REGISTRY.register("invisible_item_frame", () -> new InvisibleItemFrameItem(new Item.Properties()));
    public static final RegistryObject<Item> MIRROR_PAINTING = REGISTRY.register("mirror_painting", () -> new MirrorPaintingItem(new Item.Properties()));

    public static void init(IEventBus modBus) {
        BlockSetup.MANUAL_BLOCKS.values().forEach(block -> {
            REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        });

        REGISTRY.register(modBus);
    }
}
