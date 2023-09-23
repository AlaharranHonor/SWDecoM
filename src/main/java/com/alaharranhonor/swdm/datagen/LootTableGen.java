package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class LootTableGen extends LootTableProvider {

    public LootTableGen(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders) {
        super(pOutput, pRequiredTables, pSubProviders);
    }

    public static class BlockLoot extends BlockLootSubProvider {

        public BlockLoot() {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
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
            return BlockSetup.REGISTRY.getEntries().stream().flatMap(RegistryObject::stream)::iterator;
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
}
