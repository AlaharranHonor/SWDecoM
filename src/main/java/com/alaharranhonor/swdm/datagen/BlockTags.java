package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addBlockTags(this);
            });
        }
    }

    @Override
    public TagAppender<Block> tag(TagKey<Block> pTag) {
        return super.tag(pTag);
    }

    /*private void walls() {
        BlockInit.COATED_CHAINS.values().forEach(chain -> this.tag(net.minecraft.tags.BlockTags.WALLS).add(chain.get()));
        BlockInit.PYLONS.values().forEach(pylon -> this.tag(net.minecraft.tags.BlockTags.WALLS).add(pylon.get()));
        BlockInit.STONE_SET_WALLS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get()));
        BlockInit.SSWT_SET_WALLS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get()));

        this.tag(net.minecraft.tags.BlockTags.WALLS)
            .add(Blocks.CHAIN)
            .add(BlockInit.STONE_WALL.get())
            .addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_log_wall"))
            .addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_plank_wall"));
    }

    private void trapdoors() {
        BlockInit.STONE_SET_TRAPDOORS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.TRAPDOORS).add(rb.get()));
    }

    private void miningTool() {
        BlockInit.STONE_SET_BLOCKS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_STAIRS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_SLABS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_WALLS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_TRAPDOORS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_BUTTONS.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));
        BlockInit.STONE_SET_PRESSURE_PLATES.items().forEach(rb -> this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get()));

        Consumer<Map.Entry<String, List<RegistryObject<Block>>>> func = entry -> {
            if (entry.getKey().contains("roof")) {
                entry.getValue().forEach(rb -> {
                    this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(rb.get());
                });
            } else if (entry.getKey().contains("wool")) {
                entry.getValue().forEach(rb -> {
                    this.tag(net.minecraft.tags.BlockTags.WOOL).add(rb.get());
                });
            }
        };

        //this.miningSet(BlockInit.SSWT_SET_BLOCKS, "color", func);
        //this.miningSet(BlockInit.SSWT_SET_STAIRS, "color", func);
        //this.miningSet(BlockInit.SSWT_SET_SLABS, "color", func);
        //this.miningSet(BlockInit.SSWT_SET_WALLS, "color", func);
        //this.miningSet(BlockInit.SSWT_SET_TRAPDOORS, "color", func);
    }

    private <T extends Block> void miningSet(MultiTable<String, String, RegistryObject<T>> table, String row, Consumer<Map.Entry<String, List<RegistryObject<T>>>> function) {
        table.row(row).entrySet().forEach(function::accept);
    }*/
}
