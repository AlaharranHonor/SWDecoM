package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTagGen extends BlockTagsProvider {
    public BlockTagGen(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(pPackOutput, lookup, ModRef.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addBlockTags(this);
                set.getBlockTags().forEach(tag -> {
                    genType.subTypes()
                        .filter(sub -> sub instanceof Block)
                        .map(Block.class::cast)
                        .forEach(subBlock -> this.tag(tag).add(subBlock));
                });
            });
        }

        this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
            BlockSetup.BAMBOO_PLANKS.get(),
            BlockSetup.BAMBOO_LOG.get(),
            BlockSetup.BAMBOO_STRIPPED_LOG.get(),
            BlockSetup.WHITEWASH_PLANKS.get(),
            BlockSetup.WHITEWASH_LOG.get(),
            BlockSetup.WHITEWASH_STRIPPED_LOG.get(),
            BlockSetup.THATCH_PLANKS.get(),
            BlockSetup.THATCH_LOG.get(),
            BlockSetup.THATCH_STRIPPED_LOG.get(),
            BlockSetup.DECO_WORKSHOP.get()
        );

        this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE).add(
            BlockSetup.THATCH.get()
        );

        this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
            BlockSetup.SMOOTH_STONE_BORDERLESS.get(),
            BlockSetup.DECO_WORKSHOP.get()
        );
    }

    // Set to public
    @Override
    public IntrinsicTagAppender<Block> tag(TagKey<Block> pTag) {
        return super.tag(pTag);
    }
}
