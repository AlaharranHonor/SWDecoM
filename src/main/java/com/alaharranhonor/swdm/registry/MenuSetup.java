package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.workshop.DecoWorkshopMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuSetup {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModRef.ID);

    public static final RegistryObject<MenuType<DecoWorkshopMenu>> DECO_WORKSHOP = MENUS.register("deco_workshop", () -> IForgeMenuType.create((id, inv, data) -> new DecoWorkshopMenu(id, inv)));

    public static void init(IEventBus modBus) {
        MENUS.register(modBus);
    }
}
