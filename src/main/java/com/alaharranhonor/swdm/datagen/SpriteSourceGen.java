package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

public class SpriteSourceGen extends SpriteSourceProvider {

    public SpriteSourceGen(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper, ModRef.ID);
    }

    @Override
    protected void addSources() {
        this.atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("entity/signs", "entity/signs/"));
    }
}
