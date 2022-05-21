package com.alaharranhonor.swdm.util.init;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SWDMPaintings {

    public static final DeferredRegister<Motive> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, SWDM.MOD_ID);

    public static void init(IEventBus modBus) {PAINTINGS.register(modBus);}

    public static final RegistryObject<Motive> MIRROR_ACACIA_16X16 = PAINTINGS.register("mirror_acacia_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_ACACIA_16X32 = PAINTINGS.register("mirror_acacia_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_ACACIA_32X16 = PAINTINGS.register("mirror_acacia_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_ACACIA_32X32 = PAINTINGS.register("mirror_acacia_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_ACACIA_64X32 = PAINTINGS.register("mirror_acacia_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_ACACIA_64X48 = PAINTINGS.register("mirror_acacia_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_ACACIA_64X64 = PAINTINGS.register("mirror_acacia_64x64", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> MIRROR_BIRCH_16X16 = PAINTINGS.register("mirror_birch_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_BIRCH_16X32 = PAINTINGS.register("mirror_birch_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_BIRCH_32X16 = PAINTINGS.register("mirror_birch_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_BIRCH_32X32 = PAINTINGS.register("mirror_birch_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_BIRCH_64X32 = PAINTINGS.register("mirror_birch_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_BIRCH_64X48 = PAINTINGS.register("mirror_birch_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_BIRCH_64X64 = PAINTINGS.register("mirror_birch_64x64", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_16X16 = PAINTINGS.register("mirror_dark_oak_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_16X32 = PAINTINGS.register("mirror_dark_oak_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_32X16 = PAINTINGS.register("mirror_dark_oak_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_32X32 = PAINTINGS.register("mirror_dark_oak_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_64X32 = PAINTINGS.register("mirror_dark_oak_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_64X48 = PAINTINGS.register("mirror_dark_oak_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_DARK_OAK_64X64 = PAINTINGS.register("mirror_dark_oak_64x64", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_16X16 = PAINTINGS.register("mirror_jungle_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_16X32 = PAINTINGS.register("mirror_jungle_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_32X16 = PAINTINGS.register("mirror_jungle_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_32X32 = PAINTINGS.register("mirror_jungle_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_64X32 = PAINTINGS.register("mirror_jungle_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_64X48 = PAINTINGS.register("mirror_jungle_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_JUNGLE_64X64 = PAINTINGS.register("mirror_jungle_64x64", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> MIRROR_OAK_16X16 = PAINTINGS.register("mirror_oak_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_OAK_16X32 = PAINTINGS.register("mirror_oak_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_OAK_32X16 = PAINTINGS.register("mirror_oak_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_OAK_32X32 = PAINTINGS.register("mirror_oak_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_OAK_64X32 = PAINTINGS.register("mirror_oak_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_OAK_64X48 = PAINTINGS.register("mirror_oak_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_OAK_64X64 = PAINTINGS.register("mirror_oak_64x64", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_16X16 = PAINTINGS.register("mirror_spruce_16x16", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_16X32 = PAINTINGS.register("mirror_spruce_16x32", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_32X16 = PAINTINGS.register("mirror_spruce_32x16", () -> new Motive(32, 16));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_32X32 = PAINTINGS.register("mirror_spruce_32x32", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_64X32 = PAINTINGS.register("mirror_spruce_64x32", () -> new Motive(64, 32));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_64X48 = PAINTINGS.register("mirror_spruce_64x48", () -> new Motive(64, 48));
    public static final RegistryObject<Motive> MIRROR_SPRUCE_64X64 = PAINTINGS.register("mirror_spruce_64x64", () -> new Motive(64, 64));

}
