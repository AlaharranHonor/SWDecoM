package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.CoatedChain;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public LootTables(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
        this.generator = p_i50789_1_;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
            Pair.of(ModLootTables::new, LootTable.DEFAULT_PARAM_SET)
        );
    }

    /**
     * Performs this provider's action.
     *
     * @param pCache
     */
    @Override
    public void run(HashCache pCache) {
        Path outputFolder = this.generator.getOutputFolder();
        this.getTables().forEach((key) -> {
            key.getFirst().get().accept((p_218437_2_, p_218437_3_) -> {
                Path path = outputFolder.resolve("data/" + p_218437_2_.getNamespace() + "/loot_tables/" + p_218437_2_.getPath() + ".json");
                try {
                    DataProvider.save(GSON, pCache, net.minecraft.world.level.storage.loot.LootTables.serialize(p_218437_3_.build()), path);
                } catch (IOException e) {
                    SWDM.LOGGER.error("Couldn't write loot table {}", path, (Object) e);
                }
            });
        });
    }

    public class ModLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        /**
         * Performs this operation on the given argument.
         *
         * @param register the input argument
         */
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> register) {
            // Singles
            registerSimpleBlockDrop(register, BlockInit.STONE_WALL);
            registerSimpleBlockDrop(register, BlockInit.CLAY_BLOCK_LIGHT);
            registerSimpleBlockDrop(register, BlockInit.CLAY_BLOCK_MEDIUM);
            registerSimpleBlockDrop(register, BlockInit.CLAY_BLOCK_DARK);
            registerSimpleBlockDrop(register, BlockInit.GRASS_SLAB);
            registerSimpleBlockDrop(register, BlockInit.DIRT_SLAB);

            // Sets
            BlockInit.COATED_CHAINS.values().forEach(rb -> register.accept(rb.getId(), simpleBlockDrop(rb.get())));

            BlockInit.STONE_SET_SLABS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.STONE_SET_STAIRS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.STONE_SET_WALLS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.STONE_SET_BLOCKS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.STONE_SET_BUTTONS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.STONE_SET_PRESSURE_PLATES.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));

            BlockInit.SSW_SET_BLOCKS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.SSW_SET_SLABS.cellSet().forEach((cell) -> {
                cell.getValue().forEach(rb -> {
                    if (cell.getColumnKey().equals("leaves")) {
                        register.accept(rb.get().getRegistryName(), leavesLootTable(rb.get()));
                    } else {
                        register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get()));
                    }
                });
            });
            BlockInit.SSW_SET_STAIRS.cellSet().forEach((cell) -> {
                cell.getValue().forEach((rb) -> {
                    if (cell.getColumnKey().equals("leaves")) {
                        register.accept(rb.get().getRegistryName(), leavesLootTable(rb.get()));
                    } else {
                        register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get()));
                    }
                });
            });
            BlockInit.SSW_SET_WALLS.cellSet().forEach((cell) -> {
                cell.getValue().forEach((rb) -> {
                    if (cell.getColumnKey().equals("leaves")) {
                        register.accept(rb.get().getRegistryName(), leavesLootTable(rb.get()));
                    } else {
                        register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get()));
                    }
                });
            });

            BlockInit.SSW_SET_CARPETS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.SSW_SET_GLASS_PANES.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.SSW_SET_BEAMS.items().forEach((rb) -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.SAND_BLOCKS.values().forEach(rb -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.SANDSTONE_BLOCKS.values().forEach(rb -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
            BlockInit.FIBER_CARPETS.values().forEach(rb -> register.accept(rb.get().getRegistryName(), simpleBlockDrop(rb.get())));
        }

        private static void registerSimpleBlockDrop(BiConsumer<ResourceLocation, LootTable.Builder> register, RegistryObject<? extends Block> block) {
            register.accept(block.getId(), simpleBlockDrop(block.get()));
        }

        private static LootTable.Builder simpleBlockDrop(Block block) {
            return LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .name(block.getRegistryName().getPath())
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block))
            );
        }

        private static LootTable.Builder leavesLootTable(Block rb) {
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
