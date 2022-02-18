package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.tileentity.ClockTE;
import com.alaharranhonor.swdm.tileentity.SWDMSignTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWDMTileEntities {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SWDM.MOD_ID);

    public static void init(IEventBus modBus) {
        TILE_ENTITY_TYPES.register(modBus);
    }

    public static final RegistryObject<TileEntityType<SWDMSignTE>> SWDM_SIGN = TILE_ENTITY_TYPES.register("swdm_sign", () -> TileEntityType.Builder.of(SWDMSignTE::new, BlockInit.THATCH_SIGN.get(), BlockInit.THATCH_WALL_SIGN.get(), BlockInit.BAMBOO_SIGN.get(), BlockInit.BAMBOO_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<ClockTE>> CLOCK = TILE_ENTITY_TYPES.register("clock", () -> TileEntityType.Builder.of(ClockTE::new, BlockInit.CLOCK.get()).build(null));
}
