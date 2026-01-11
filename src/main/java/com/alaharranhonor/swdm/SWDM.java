package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.compat.SWLMCompat;
import com.alaharranhonor.swdm.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ModRef.ID)
public class SWDM {

    public SWDM(ModContainer container, IEventBus modBus) {
        SetSetup.init();
        BlockSetup.init(modBus);
        ItemSetup.init(modBus);
        CreativeTabSetup.init(modBus);
        EntitySetup.init(modBus);
        RecipeSetup.init(modBus);
        MenuSetup.init(modBus);
        PaintingSetup.init(modBus);

        SWLMCompat.init();
    }
}
