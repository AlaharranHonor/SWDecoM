package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.concurrent.CompletableFuture;

public class SpriteSourceGen extends SpriteSourceProvider {

    public SpriteSourceGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, registries, ModRef.ID, fileHelper);
    }

    @Override
    protected void gather() {
        this.atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("entity/signs", "entity/signs/"));
    }
}
