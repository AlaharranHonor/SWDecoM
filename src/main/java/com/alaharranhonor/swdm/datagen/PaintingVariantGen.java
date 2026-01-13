package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.registry.PaintingSetup;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingVariantGen {

    public static void bootstrap(BootstrapContext<PaintingVariant> ctx) {
        register(ctx, PaintingSetup.MIRROR_ACACIA_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_ACACIA_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_ACACIA_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_ACACIA_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_ACACIA_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_ACACIA_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_ACACIA_64X64, 4, 4);
        register(ctx, PaintingSetup.MIRROR_BIRCH_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_BIRCH_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_BIRCH_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_BIRCH_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_BIRCH_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_BIRCH_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_BIRCH_64X64, 4, 4);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_DARK_OAK_64X64, 4, 4);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_JUNGLE_64X64, 4, 4);
        register(ctx, PaintingSetup.MIRROR_OAK_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_OAK_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_OAK_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_OAK_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_OAK_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_OAK_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_OAK_64X64, 4, 4);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_16X16, 1, 1);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_16X32, 1, 2);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_32X16, 2, 1);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_32X32, 2, 2);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_64X32, 4, 2);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_64X48, 4, 3);
        register(ctx, PaintingSetup.MIRROR_SPRUCE_64X64, 4, 4);
    }

    private static void register(BootstrapContext<PaintingVariant> ctx, ResourceKey<PaintingVariant> key, int width, int height) {
        ctx.register(key, new PaintingVariant(width, height, key.location()));
    }

}
