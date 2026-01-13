package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.PaintingSetup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PaintingVariantTagGen extends PaintingVariantTagsProvider {
    public PaintingVariantTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, ModRef.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(PaintingVariantTags.PLACEABLE)
            .add(
                PaintingSetup.MIRROR_ACACIA_16X16,
                PaintingSetup.MIRROR_ACACIA_16X32,
                PaintingSetup.MIRROR_ACACIA_32X16,
                PaintingSetup.MIRROR_ACACIA_32X32,
                PaintingSetup.MIRROR_ACACIA_64X32,
                PaintingSetup.MIRROR_ACACIA_64X48,
                PaintingSetup.MIRROR_ACACIA_64X64,
                PaintingSetup.MIRROR_BIRCH_16X16,
                PaintingSetup.MIRROR_BIRCH_16X32,
                PaintingSetup.MIRROR_BIRCH_32X16,
                PaintingSetup.MIRROR_BIRCH_32X32,
                PaintingSetup.MIRROR_BIRCH_64X32,
                PaintingSetup.MIRROR_BIRCH_64X48,
                PaintingSetup.MIRROR_BIRCH_64X64,
                PaintingSetup.MIRROR_DARK_OAK_16X16,
                PaintingSetup.MIRROR_DARK_OAK_16X32,
                PaintingSetup.MIRROR_DARK_OAK_32X16,
                PaintingSetup.MIRROR_DARK_OAK_32X32,
                PaintingSetup.MIRROR_DARK_OAK_64X32,
                PaintingSetup.MIRROR_DARK_OAK_64X48,
                PaintingSetup.MIRROR_DARK_OAK_64X64,
                PaintingSetup.MIRROR_JUNGLE_16X16,
                PaintingSetup.MIRROR_JUNGLE_16X32,
                PaintingSetup.MIRROR_JUNGLE_32X16,
                PaintingSetup.MIRROR_JUNGLE_32X32,
                PaintingSetup.MIRROR_JUNGLE_64X32,
                PaintingSetup.MIRROR_JUNGLE_64X48,
                PaintingSetup.MIRROR_JUNGLE_64X64,
                PaintingSetup.MIRROR_OAK_16X16,
                PaintingSetup.MIRROR_OAK_16X32,
                PaintingSetup.MIRROR_OAK_32X16,
                PaintingSetup.MIRROR_OAK_32X32,
                PaintingSetup.MIRROR_OAK_64X32,
                PaintingSetup.MIRROR_OAK_64X48,
                PaintingSetup.MIRROR_OAK_64X64,
                PaintingSetup.MIRROR_SPRUCE_16X16,
                PaintingSetup.MIRROR_SPRUCE_16X32,
                PaintingSetup.MIRROR_SPRUCE_32X16,
                PaintingSetup.MIRROR_SPRUCE_32X32,
                PaintingSetup.MIRROR_SPRUCE_64X32,
                PaintingSetup.MIRROR_SPRUCE_64X48,
                PaintingSetup.MIRROR_SPRUCE_64X64
            );
    }
}
