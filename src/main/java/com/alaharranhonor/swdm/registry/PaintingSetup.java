package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingSetup {
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_16X16 = create("mirror_acacia_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_16X32 = create("mirror_acacia_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_32X16 = create("mirror_acacia_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_32X32 = create("mirror_acacia_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_64X32 = create("mirror_acacia_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_64X48 = create("mirror_acacia_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_ACACIA_64X64 = create("mirror_acacia_64x64");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_16X16 = create("mirror_birch_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_16X32 = create("mirror_birch_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_32X16 = create("mirror_birch_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_32X32 = create("mirror_birch_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_64X32 = create("mirror_birch_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_64X48 = create("mirror_birch_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_BIRCH_64X64 = create("mirror_birch_64x64");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_16X16 = create("mirror_dark_oak_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_16X32 = create("mirror_dark_oak_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_32X16 = create("mirror_dark_oak_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_32X32 = create("mirror_dark_oak_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_64X32 = create("mirror_dark_oak_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_64X48 = create("mirror_dark_oak_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_DARK_OAK_64X64 = create("mirror_dark_oak_64x64");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_16X16 = create("mirror_jungle_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_16X32 = create("mirror_jungle_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_32X16 = create("mirror_jungle_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_32X32 = create("mirror_jungle_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_64X32 = create("mirror_jungle_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_64X48 = create("mirror_jungle_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_JUNGLE_64X64 = create("mirror_jungle_64x64");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_16X16 = create("mirror_oak_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_16X32 = create("mirror_oak_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_32X16 = create("mirror_oak_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_32X32 = create("mirror_oak_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_64X32 = create("mirror_oak_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_64X48 = create("mirror_oak_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_OAK_64X64 = create("mirror_oak_64x64");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_16X16 = create("mirror_spruce_16x16");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_16X32 = create("mirror_spruce_16x32");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_32X16 = create("mirror_spruce_32x16");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_32X32 = create("mirror_spruce_32x32");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_64X32 = create("mirror_spruce_64x32");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_64X48 = create("mirror_spruce_64x48");
    public static final ResourceKey<PaintingVariant> MIRROR_SPRUCE_64X64 = create("mirror_spruce_64x64");

    private static ResourceKey<PaintingVariant> create(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, ModRef.res(name));
    }
}
