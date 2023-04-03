package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    public LootTables(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
            Pair.of(ModLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {

    }

    public static class ModLootTables extends BlockLoot {

        @Override
        protected void addTables() {
            BlockSetup.BLOCKS_BY_NAME.values().forEach(block -> {
                this.add(block.get(), this.simpleBlockDrop(block.get()));
            });

            for (GenSet set : SetSetup.SETS) {
                set.genTypes.forEach(genType -> {
                    genType.addLootTables(this);
                });
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockSetup.BLOCKS.getEntries().stream().flatMap(RegistryObject::stream)::iterator;
        }

        public void registerSimpleBlockDrop(BiConsumer<ResourceLocation, LootTable.Builder> register, RegistryObject<? extends Block> block) {
            register.accept(block.getId(), simpleBlockDrop(block.get()));
        }

        public LootTable.Builder simpleBlockDrop(Block block) {
            return LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .name(block.getRegistryName().getPath())
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block))
            );
        }

        public LootTable.Builder leavesLootTable(Block rb) {
            return LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .name(rb.getRegistryName().getPath())
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(rb).when( // Regular block LT
                            AlternativeLootItemCondition.alternative(
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
                            )
                        ),
                        LootItem.lootTableItem(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.getRegistryName().getPath().split("_")[0] + "_sapling"))).when( // Sapling LT
                            ExplosionCondition.survivesExplosion()
                        ).when(
                            BonusLevelTableCondition.bonusLevelFlatChance(
                                Enchantments.BLOCK_FORTUNE,
                                0.05f,
                                0.0625f,
                                0.083333336f,
                                0.1f
                            )
                        )
                    ))
            ).withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "stick")))
                            .when( // Conditions
                                BonusLevelTableCondition.bonusLevelFlatChance(
                                    Enchantments.BLOCK_FORTUNE,
                                    0.02f,
                                    0.022222223f,
                                    0.025f,
                                    0.033333335f,
                                    0.1f
                                )
                            )
                            .apply( // Functions
                                LimitCount.limitCount(IntRange.range(1, 2))
                            )
                            .apply(
                                ApplyExplosionDecay.explosionDecay()
                            )
                    )
                    .when(
                        InvertedLootItemCondition.invert(
                            MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
                                .or(
                                    MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(
                                        new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))
                                    ))
                                )
                        )
                    )
            ).withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(Items.APPLE).when(
                            ExplosionCondition.survivesExplosion()
                        ).when(
                            BonusLevelTableCondition.bonusLevelFlatChance(
                                Enchantments.BLOCK_FORTUNE,
                                0.005f,
                                0.0055555557f,
                                0.00625f,
                                0.008333334f,
                                0.025f
                            )
                        )
                    ).when(
                        InvertedLootItemCondition.invert(
                            AlternativeLootItemCondition.alternative(
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
                            )
                        )
                    )
            );
        }
    }
}
