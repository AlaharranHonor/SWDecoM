package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Tables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

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
			for (DyeColor color : DyeColor.values()) {
				register.accept(new ResourceLocation(SWDM.MOD_ID, color.getName() + "_wool_stairs"),
					LootTable.lootTable().withPool(
						LootPool.lootPool()
							.name(color.getName() + "_wool_stairs")
							.setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(BlockInit.WOOL_STAIRS.get(color.getId()).get()))
					)

				);
			}
		}
	}
}
