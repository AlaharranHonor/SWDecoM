package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.compat.SWLMCompat;
import com.alaharranhonor.swdm.registry.*;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModRef.ID)
public class SWDM {

    public SWDM() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
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
