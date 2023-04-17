package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SWDM.MOD_ID)
public class SWDM {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "swdm";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("swdmtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImage(new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tab_item_search.png"));

    public SWDM() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        SetSetup.init();
        BlockSetup.init(modBus);
        ItemSetup.init(modBus);
        RecipeSetup.init(modBus);
        MenuSetup.init(modBus);
    }
}
