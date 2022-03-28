package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.blocks.CoatedChain;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.alaharranhonor.swdm.util.init.SWEMInit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Tables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.config.Registry;
import software.bernie.example.registry.BlockRegistry;

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
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
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
	public void run(DirectoryCache pCache) {
		Path outputFolder = this.generator.getOutputFolder();
		this.getTables().forEach((key) -> {
			key.getFirst().get().accept((p_218437_2_, p_218437_3_) -> {
				Path path = outputFolder.resolve("data/" + p_218437_2_.getNamespace() + "/loot_tables/" + p_218437_2_.getPath() + ".json");
				try {
					IDataProvider.save(GSON, pCache, LootTableManager.serialize(p_218437_3_.build()), path);
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

			for (RegistryObject<CoatedChain> chain : BlockInit.COATED_CHAINS) {
				LootTable.lootTable().withPool(
					LootPool.lootPool()
						.name(chain.get().getRegistryName().getPath())
						.setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(chain.get()))
				);
			}

			BlockInit.STONE_SET_SLABS.keySet().forEach((key) -> {
				BlockInit.STONE_SET_SLABS.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_SLABS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
					 	LootTable.lootTable().withPool(
					LootPool.lootPool()
								.name(rb.get().getRegistryName().getPath())
								.setRolls(ConstantRange.exactly(1))
								.add(ItemLootEntry.lootTableItem(rb.get()))
						  )
						);
					});
				});
			});
			BlockInit.STONE_SET_STAIRS.keySet().forEach((key) -> {
				BlockInit.STONE_SET_STAIRS.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_STAIRS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.STONE_SET_WALLS.keySet().forEach((key) -> {
				BlockInit.STONE_SET_WALLS.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_WALLS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.STONE_SET_BLOCKS.keySet().forEach((key) -> {
				BlockInit.STONE_SET_BLOCKS.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_BLOCKS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.STONE_SET_BUTTONS.keySet().forEach((key) -> {
				BlockInit.STONE_SET_BUTTONS.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_BUTTONS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
				BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
					BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.SSW_SET_BLOCKS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_BLOCKS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_BLOCKS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.SSW_SET_SLABS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_SLABS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_SLABS.get(key).get(key2).forEach((rb) -> {
						if (key2.equals("leaves")) {
							register.accept(rb.get().getRegistryName(), this.leavesLootTable(rb.get()));
						} else {
							register.accept(rb.get().getRegistryName(),
								LootTable.lootTable().withPool(
									LootPool.lootPool()
										.name(rb.get().getRegistryName().getPath())
										.setRolls(ConstantRange.exactly(1))
										.add(ItemLootEntry.lootTableItem(rb.get()))
								)
							);
						}
					});
				});
			});
			BlockInit.SSW_SET_STAIRS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_STAIRS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_STAIRS.get(key).get(key2).forEach((rb) -> {
						if (key2.equals("leaves")) {
							register.accept(rb.get().getRegistryName(), this.leavesLootTable(rb.get()));
						} else {
							register.accept(rb.get().getRegistryName(),
								LootTable.lootTable().withPool(
									LootPool.lootPool()
										.name(rb.get().getRegistryName().getPath())
										.setRolls(ConstantRange.exactly(1))
										.add(ItemLootEntry.lootTableItem(rb.get()))
								)
							);
						}
					});
				});
			});
			BlockInit.SSW_SET_WALLS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_WALLS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_WALLS.get(key).get(key2).forEach((rb) -> {
						if (key2.equals("leaves")) {
							register.accept(rb.get().getRegistryName(), this.leavesLootTable(rb.get()));
						} else {
							register.accept(rb.get().getRegistryName(),
								LootTable.lootTable().withPool(
									LootPool.lootPool()
										.name(rb.get().getRegistryName().getPath())
										.setRolls(ConstantRange.exactly(1))
										.add(ItemLootEntry.lootTableItem(rb.get()))
								)
							);
						}
					});
				});
			});
			BlockInit.SSW_SET_CARPETS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_CARPETS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_CARPETS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.SSW_SET_GLASS_PANES.keySet().forEach((key) -> {
				BlockInit.SSW_SET_GLASS_PANES.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_GLASS_PANES.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});
			BlockInit.SSW_SET_BEAMS.keySet().forEach((key) -> {
				BlockInit.SSW_SET_BEAMS.get(key).keySet().forEach((key2) -> {
					BlockInit.SSW_SET_BEAMS.get(key).get(key2).forEach((rb) -> {
						register.accept(rb.get().getRegistryName(),
							LootTable.lootTable().withPool(
								LootPool.lootPool()
									.name(rb.get().getRegistryName().getPath())
									.setRolls(ConstantRange.exactly(1))
									.add(ItemLootEntry.lootTableItem(rb.get()))
							)
						);
					});
				});
			});

			for (int i = 0; i < SWDM.NATURAL_TONES.size(); i++) {
				String natural = SWDM.NATURAL_TONES.get(i);

				register.accept(BlockInit.SAND_BLOCKS.get(i).get().getRegistryName(),
					LootTable.lootTable().withPool(
						LootPool.lootPool()
							.name(BlockInit.SAND_BLOCKS.get(i).get().getRegistryName().getPath())
							.setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(BlockInit.SAND_BLOCKS.get(i).get()))
					)
				);

				register.accept(BlockInit.SANDSTONE_BLOCKS.get(i).get().getRegistryName(),
					LootTable.lootTable().withPool(
						LootPool.lootPool()
							.name(BlockInit.SANDSTONE_BLOCKS.get(i).get().getRegistryName().getPath())
							.setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(BlockInit.SANDSTONE_BLOCKS.get(i).get()))
					)
				);

				register.accept(BlockInit.FIBER_CARPETS.get(i).get().getRegistryName(),
					LootTable.lootTable().withPool(
						LootPool.lootPool()
							.name(BlockInit.FIBER_CARPETS.get(i).get().getRegistryName().getPath())
							.setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(BlockInit.FIBER_CARPETS.get(i).get()))
					)
				);
			}
		}



		public LootTable.Builder leavesLootTable(Block rb) {
			return LootTable.lootTable().withPool(
				LootPool.lootPool()
					.name(rb.getRegistryName().getPath())
					.setRolls(ConstantRange.exactly(1))
					.add(AlternativesLootEntry.alternatives(
						ItemLootEntry.lootTableItem(rb).when( // Regular block LT
							Alternative.alternative(
								MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)),
								MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
							)
						),
						ItemLootEntry.lootTableItem(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.getRegistryName().getPath().split("_")[0] + "_sapling"))).when( // Sapling LT
							SurvivesExplosion.survivesExplosion()
						).when(
							TableBonus.bonusLevelFlatChance(
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
					.setRolls(ConstantRange.exactly(1))
					.add(
						ItemLootEntry.lootTableItem(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "stick")))
							.when( // Conditions
								TableBonus.bonusLevelFlatChance(
									Enchantments.BLOCK_FORTUNE,
									0.02f,
									0.022222223f,
									0.025f,
									0.033333335f,
									0.1f
								)
							)
							.apply( // Functions
								SetCount.setCount(RandomValueRange.between(1, 2))
							)
							.apply(
								ExplosionDecay.explosionDecay()
							)
					)
					.when(
						Inverted.invert(
							MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
								.or(
									MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(
										new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))
									))
								)
						)
					)
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantRange.exactly(1))
					.add(
						ItemLootEntry.lootTableItem(Items.APPLE).when(
							SurvivesExplosion.survivesExplosion()
						).when(
							TableBonus.bonusLevelFlatChance(
								Enchantments.BLOCK_FORTUNE,
								0.005f,
								0.0055555557f,
								0.00625f,
								0.008333334f,
								0.025f
							)
						)
					).when(
						Inverted.invert(
							Alternative.alternative(
								MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)),
								MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
							)
						)
					)
			);
		}
	}
}
