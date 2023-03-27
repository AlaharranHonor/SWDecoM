package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.CoatedChain;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //super.addTags();

        this.tag(net.minecraft.tags.BlockTags.WALLS).add(Blocks.CHAIN);
        for (RegistryObject<CoatedChain> chain : BlockInit.COATED_CHAINS.values()) {
            this.tag(net.minecraft.tags.BlockTags.WALLS).add(chain.get());
        }

        this.tag(net.minecraft.tags.BlockTags.WALLS)
            .addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_log_wall"))
            .addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_plank_wall"));

        this.tag(net.minecraft.tags.BlockTags.WALLS)
            //.add(BlockInit.TERRACOTTA_WALL.get())
            .add(BlockInit.STONE_WALL.get());

        BlockInit.STONE_SET_WALLS.items().forEach(rb -> {
            this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get());
        });

        BlockInit.STONE_SET_TRAPDOORS.items().forEach(rb -> {
            this.tag(net.minecraft.tags.BlockTags.TRAPDOORS).add(rb.get());
        });

        BlockInit.SSWT_SET_WALLS.items().forEach(rb -> {
            this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get());
        });

        // TODO add blocks to 'mineable' tag
    }
}
