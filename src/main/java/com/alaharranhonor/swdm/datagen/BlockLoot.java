package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.function.Function;

public class BlockLoot extends BlockLootSubProvider {

    public BlockLoot(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        BlockSetup.MANUAL_BLOCKS.values().forEach(block -> {
            this.dropSelf(block.get());
        });

        this.dropSelf(BlockSetup.DECO_WORKSHOP.get());

        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addLootTables(this);
            });
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockSetup.REGISTRY.getEntries().stream().map(db -> ((Block) db.get()))::iterator;
    }

    // Below methods are overridden to be set to public

    @Override
    public void dropSelf(Block pBlock) {
        super.dropSelf(pBlock);
    }

    @Override
    public void dropOther(Block pBlock, ItemLike pItem) {
        super.dropOther(pBlock, pItem);
    }

    @Override
    public LootTable.Builder createSlabItemTable(Block pBlock) {
        return super.createSlabItemTable(pBlock);
    }

    @Override
    public void add(Block pBlock, Function<Block, LootTable.Builder> pFactory) {
        super.add(pBlock, pFactory);
    }

    @Override
    public LootTable.Builder createDoorTable(Block pDoorBlock) {
        return super.createDoorTable(pDoorBlock);
    }
}

