package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.entity.SWDMSignBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SWDMBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SWDM.MOD_ID);

    public static void init(IEventBus modBus) {
        TILE_ENTITY_TYPES.register(modBus);
    }

    public static final RegistryObject<BlockEntityType<SWDMSignBE>> SWDM_SIGN = TILE_ENTITY_TYPES.register("swdm_sign", () -> BlockEntityType.Builder.of(SWDMSignBE::new/*BlockInit.THATCH_SIGN.get(), BlockInit.THATCH_WALL_SIGN.get(), BlockInit.BAMBOO_SIGN.get(), BlockInit.BAMBOO_WALL_SIGN.get()*/).build(null));
}
