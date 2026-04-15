package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.multidoor.DoorHingeMenu;
import com.alaharranhonor.swdm.workshop.DecoWorkshopMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuSetup {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ModRef.ID);

    public static final DeferredHolder<MenuType<?>, MenuType<DecoWorkshopMenu>> DECO_WORKSHOP = MENUS.register("deco_workshop", () -> IMenuTypeExtension.create((id, inv, data) -> new DecoWorkshopMenu(id, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<DoorHingeMenu>> DOOR_HINGE = MENUS.register("door_hinge", () -> IMenuTypeExtension.create(DoorHingeMenu::new));

    public static void init(IEventBus modBus) {
        MENUS.register(modBus);
    }
}
