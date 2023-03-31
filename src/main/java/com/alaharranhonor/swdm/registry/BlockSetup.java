package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockSetup {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWDM.MOD_ID);

    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static RegistryObject<Block> register(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
