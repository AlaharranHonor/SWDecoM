package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.util.ColourUtil;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.data.*;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
	public Recipes(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> p_200404_1_) {
		super.buildShapelessRecipes(p_200404_1_);

		// Make all the basic blocks first, and then all their respective items.

		for (DyeColor color : DyeColor.values()) {
			ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("color").get("dark_prismarine").get(color.getId()).get()).requires(Blocks.DARK_PRISMARINE).requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).group("dark_prismarine").unlockedBy("has_dark_prismarine", has(Blocks.DARK_PRISMARINE)).save(p_200404_1_);
			ShapedRecipeBuilder.shaped(BlockInit.STONE_SET_BLOCKS.get("color").get("glass_bricks").get(color.getId()).get(), 8).define('x', Blocks.BRICKS).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_stained_glass"))).pattern("yyy").pattern("yxy").pattern("yyy").group("glass_bricks").unlockedBy("has_bricks", has(Blocks.BRICKS)).save(p_200404_1_);

			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color").get("metal_roof").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', Items.IRON_INGOT).pattern("yyy").pattern("yxy").pattern("yyy").group("metal_roof").unlockedBy("has_iron_ingots", has(Items.IRON_INGOT)).save(p_200404_1_);
			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color").get("tile_roof").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', Items.CLAY_BALL).pattern("yyy").pattern("yxy").pattern("yyy").group("tile_roof").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(p_200404_1_);
			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color").get("shingle_roof").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ItemTags.PLANKS).pattern("yyy").pattern("yxy").pattern("yyy").group("shingle_roof").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(p_200404_1_);
			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color").get("pastel_wool").get(color.getId()).get(), 8).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool"))).pattern("yyy").pattern("yxy").pattern("yyy").group("pastel_wool").unlockedBy("has_pastel_wool", has(ItemTags.WOOL)).save(p_200404_1_);
			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color").get("tinted_wool").get(color.getId()).get(), 8).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool"))).pattern("yyy").pattern("yxy").pattern("yyy").group("tinted_wool").unlockedBy("has_tinted_wool", has(ItemTags.WOOL)).save(p_200404_1_);

			ShapedRecipeBuilder.shaped(BlockInit.COATED_CHAINS.get(color.getId()).get(), 6).define('N', Items.IRON_NUGGET).define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).pattern("N").pattern("D").pattern("N").group("coated_chain").unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET)).save(p_200404_1_);
		}

		for (int i = 0; i < SWDM.STONE_TYPES.size(); i++) {
			String type = SWDM.STONE_TYPES.get(i);
			int finalI = i;
			SWDM.STONE_SETS.get("standard").forEach((stone, block) -> {
				if (type.equals("mossy")) {
					ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(finalI).get()).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(Blocks.STONE)).save(p_200404_1_);
				} else if (type.equals("cracked")) {
					SingleItemRecipeBuilder.stonecutting(Ingredient.of(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))), BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(finalI).get());
				} else if (type.equals("more_mossy")) {
					ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(finalI).get()).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))).requires(Blocks.VINE).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone)))).save(p_200404_1_);
					ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(finalI).get()).requires(BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(0).get()).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(Blocks.STONE)).save(p_200404_1_, new ResourceLocation("swdm", BlockInit.STONE_SET_BLOCKS.get("standard").get(stone).get(finalI).get().getRegistryName().getPath() + "_by_swdm_block"));
				}
			});
		}

		for (int i = 0; i < SWDM.LMD_TYPES.size(); i++) {
			String lmd = SWDM.LMD_TYPES.get(i);
			Item dyeItem = lmd.equals("light") ? Items.WHITE_DYE : lmd.equals("medium") ? Items.GRAY_DYE : Items.BLACK_DYE;
			ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd").get("brick").get(i).get()).requires(Blocks.BRICKS).requires(dyeItem).group(lmd + "_brick").unlockedBy("has_bricks", has(Blocks.BRICKS));
			ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd").get("stone_brick").get(i).get()).requires(Blocks.STONE_BRICKS).requires(dyeItem).group(lmd + "_stone_brick").unlockedBy("has_stone_bricks", has(Blocks.STONE_BRICKS));

			ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("lmd").get("screen").get(i).get(), 8).define('x', Items.STRING).define('y', dyeItem).pattern("yyy").pattern("yxy").pattern("yyy").group("screen").unlockedBy("has_string", has(Items.STRING)).save(p_200404_1_);
		}

		BlockInit.STONE_SET_SLABS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_SLABS.get(key).keySet().forEach((key2) -> {
				for (int i = 0; i < BlockInit.STONE_SET_SLABS.get(key).get(key2).size(); i++) {
					RegistryObject<SlabBlock> rb = BlockInit.STONE_SET_SLABS.get(key).get(key2).get(i);
					Block craftingItem;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
					} else {
						craftingItem = BlockInit.STONE_SET_BLOCKS.get(key).get(key2).get(i).get();
					}
					stoneSlab(p_200404_1_, rb.get(), craftingItem, key2);

				}

			});
		});

		BlockInit.STONE_SET_WALLS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_WALLS.get(key).keySet().forEach((key2) -> {
				for (int i = 0; i < BlockInit.STONE_SET_WALLS.get(key).get(key2).size(); i++) {
					RegistryObject<WallBlock> rb = BlockInit.STONE_SET_WALLS.get(key).get(key2).get(i);
					Block craftingItem;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
					} else {
						craftingItem = BlockInit.STONE_SET_BLOCKS.get(key).get(key2).get(i).get();
					}
					stoneWall(p_200404_1_, rb.get(), craftingItem, key2);

				}
			});
		});

		BlockInit.STONE_SET_STAIRS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_STAIRS.get(key).keySet().forEach((key2) -> {
				for (int i = 0; i < BlockInit.STONE_SET_STAIRS.get(key).get(key2).size(); i++) {
					RegistryObject<StairsBlock> rb = BlockInit.STONE_SET_STAIRS.get(key).get(key2).get(i);
					Block craftingItem;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
					} else {
						craftingItem = BlockInit.STONE_SET_BLOCKS.get(key).get(key2).get(i).get();
					}
					stoneStairs(p_200404_1_, rb.get(), craftingItem, key2);
				}
			});
		});


		BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
			BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
				for (int i = 0; i < BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2).size(); i++) {
					RegistryObject<PressurePlateBlock> rb = BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2).get(i);
					Block craftingItem;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 15)));
					} else {
						craftingItem = BlockInit.STONE_SET_BLOCKS.get(key).get(key2).get(i).get();
					}
					stonePressurePlate(p_200404_1_, rb.get(), craftingItem, key2);

				}
			});
		});

		BlockInit.STONE_SET_BUTTONS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_BUTTONS.get(key).keySet().forEach((key2) -> {
				for (int i = 0; i < BlockInit.STONE_SET_BUTTONS.get(key).get(key2).size(); i++) {
					RegistryObject<StoneButtonBlock> rb = BlockInit.STONE_SET_BUTTONS.get(key).get(key2).get(i);
					Block craftingItem;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("custom_color"))) {
						craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
					} else {
						craftingItem = BlockInit.STONE_SET_BLOCKS.get(key).get(key2).get(i).get();
					}
					stoneButton(p_200404_1_, rb.get(), craftingItem, key2);

				}
			});
		});


		BlockInit.SSW_SET_SLABS.get("color").keySet().forEach((key) -> {
			for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("color").get(key).size(); i++) {
				RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("color").get(key).get(i);
				Block craftingItem;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
				} else {
					craftingItem = BlockInit.SSW_SET_BLOCKS.get("color").get(key).get(i).get();
				}
				slab(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("color").get(key).size(); i++) {
				RegistryObject<WallBlock> rb = BlockInit.SSW_SET_WALLS.get("color").get(key).get(i);
				Block craftingItem;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
				} else {
					craftingItem = BlockInit.SSW_SET_BLOCKS.get("color").get(key).get(i).get();
				}
				wall(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("color").get(key).size(); i++) {
				RegistryObject<StairsBlock> rb = BlockInit.SSW_SET_STAIRS.get("color").get(key).get(i);
				Block craftingItem;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
				} else {
					craftingItem = BlockInit.SSW_SET_BLOCKS.get("color").get(key).get(i).get();
				}
				stairs(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			if (key.equals("pastel_wool") || key.equals("tinted_wool")) {
				for (RegistryObject<CarpetBlock> rb : BlockInit.SSW_SET_CARPETS.get("color").get(key)) {
					carpet(p_200404_1_, rb.get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("swdm", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7))), key + "_carpet");
				}
			}

		});

		BlockInit.SSW_SET_SLABS.get("wv").keySet().forEach((key) -> {
			for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("wv").get(key).size(); i++) {
				RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("wv").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

				slab(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}

			for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("wv").get(key).size(); i++) {
				RegistryObject<WallBlock> rb = BlockInit.SSW_SET_WALLS.get("wv").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

				wall(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("wv").get(key).size(); i++) {
				RegistryObject<StairsBlock> rb = BlockInit.SSW_SET_STAIRS.get("wv").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));

				stairs(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}

		});

		BlockInit.SSW_SET_SLABS.get("wv-whitewash").keySet().forEach((key) -> {
			for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("wv-whitewash").get(key).size(); i++) {
				RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("wv-whitewash").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

				slab(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}

			for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("wv-whitewash").get(key).size(); i++) {
				RegistryObject<WallBlock> rb = BlockInit.SSW_SET_WALLS.get("wv-whitewash").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

				wall(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get(key).size(); i++) {
				RegistryObject<StairsBlock> rb = BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get(key).get(i);
				Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));

				stairs(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}

		});


		BlockInit.SSW_SET_SLABS.get("lmd").keySet().forEach((key) -> {
			for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("lmd").get(key).size(); i++) {
				RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("lmd").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd").get(key).get(i).get();

				slab(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}

			for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("lmd").get(key).size(); i++) {
				RegistryObject<WallBlock> rb = BlockInit.SSW_SET_WALLS.get("lmd").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd").get(key).get(i).get();

				wall(p_200404_1_, rb.get(), craftingItem, key, "has_planks");
			}


			for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("lmd").get(key).size(); i++) {
				RegistryObject<StairsBlock> rb = BlockInit.SSW_SET_STAIRS.get("lmd").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd").get(key).get(i).get();

				stairs(p_200404_1_, rb.get(), craftingItem, key, "has_planks");

			}


			for (RegistryObject<PaneBlock> rb : BlockInit.SSW_SET_GLASS_PANES.get("lmd").get(key)) {


				glassPane(p_200404_1_, rb.get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("swdm", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 11))), key + "_glass_pane");
			}


		});

		for (int i = 0; i < SWDM.NATURAL_TONES.size(); i++) {
			String natural = SWDM.NATURAL_TONES.get(i);
			ShapelessRecipeBuilder sand = ShapelessRecipeBuilder.shapeless(BlockInit.SAND_BLOCKS.get(i).get()).requires(Blocks.SAND).group("sand").unlockedBy("has_sand", has(Blocks.SAND));
			for (DyeColor color : ColourUtil.NATURAL_TO_DYE.get(natural)) {
				sand.requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")));
			}
			sand.save(p_200404_1_);

			ShapedRecipeBuilder.shaped(BlockInit.SANDSTONE_BLOCKS.get(i).get(), 4).define('#', BlockInit.SAND_BLOCKS.get(i).get()).pattern("##").pattern("##").group("sand").unlockedBy("has_sand", has(Blocks.SAND)).save(p_200404_1_);

			ShapedRecipeBuilder fiberCarpet = ShapedRecipeBuilder.shaped(BlockInit.FIBER_CARPETS.get(i).get(), 8).group("fiber_carpets").unlockedBy("has_carpet", has(ItemTags.CARPETS));
			fiberCarpet.define('#', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", ColourUtil.NATURAL_TO_DYE.get(natural).get(0).getName() + "_carpet")));
			if (ColourUtil.NATURAL_TO_DYE.get(natural).size() > 1) {
				fiberCarpet.define('X', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", ColourUtil.NATURAL_TO_DYE.get(natural).get(1).getName() + "_dye")));
				fiberCarpet.pattern("###").pattern("#X#").pattern("###");
			} else {
				fiberCarpet.pattern("###").pattern("# #").pattern("###");
			}
			fiberCarpet.save(p_200404_1_);
		}

		for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd-color").get("siding")) {
			String[] parts = rb.get().getRegistryName().getPath().split("_");

			ShapedRecipeBuilder siding = ShapedRecipeBuilder.shaped(rb.get(), 8).define('P', ItemTags.WOODEN_SLABS).define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", parts[1] + "_dye"))).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS));
			if (parts[2].equals("light")) {
				siding.define('T', Items.WHITE_DYE);
				siding.pattern("PT").pattern("PD").pattern("P ");
				siding.save(p_200404_1_);

				ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(SWDM.MOD_ID, parts[0] + "_" + parts[1] + "_medium"))).requires(Items.WHITE_DYE).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS)).save(p_200404_1_, rb.get().getRegistryName().getPath() + "_from_medium");
			} else if (parts[2].equals("medium")) {
				siding.pattern("P ").pattern("PD").pattern("P ");
				siding.save(p_200404_1_);
			} else if (parts[2].equals("dark")) {
				siding.define('T', Items.BLACK_DYE);
				siding.pattern("PT").pattern("PD").pattern("P ");
				siding.save(p_200404_1_);

				ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(SWDM.MOD_ID, parts[0] + "_" + parts[1] + "_medium"))).requires(Items.BLACK_DYE).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS)).save(p_200404_1_, rb.get().getRegistryName().getPath() + "_from_medium");
			}
		}

		for (RegistryObject<Block> rb : BlockInit.STONE_SET_BLOCKS.get("color_custom").get("terracotta")) {
			ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(Blocks.TERRACOTTA).group("terracotta").unlockedBy("has_terracotta", has(Blocks.TERRACOTTA));
			for (DyeColor color : ColourUtil.CUSTOM_COLORS_TO_DYE.get(rb.get().getRegistryName().getPath().split("_")[0])) {
				shapeless.requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")));
			}
			shapeless.save(p_200404_1_);
		}

		for (int i = 0; i < SWDM.LMD_TYPES.size(); i++) {
			String lmd = SWDM.LMD_TYPES.get(i);
			String dye = lmd.equals("light") ? "white" : lmd.equals("medium") ? "gray" : "black";
			ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd-only").get("stone").get(i).get(), 8).requires(Blocks.STONE).requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", dye + "_dye"))).group("stone").unlockedBy("has_stone", has(Blocks.STONE)).save(p_200404_1_);
		}

		BlockInit.SSW_SET_SLABS.get("lmd-color").keySet().forEach((key) -> {
			for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("lmd-color").get(key).size(); i++) {
				RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("lmd-color").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd-color").get(key).get(i).get();
				slab(p_200404_1_, rb.get(), craftingItem, key, "has_" + craftingItem.getRegistryName().getPath().split("_")[0]);

			}


			for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("lmd-color").get(key).size(); i++) {
				RegistryObject<WallBlock> rb = BlockInit.SSW_SET_WALLS.get("lmd-color").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd-color").get(key).get(i).get();
				wall(p_200404_1_, rb.get(), craftingItem, key, "has_" + craftingItem.getRegistryName().getPath().split("_")[0]);

			}


			for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("lmd-color").get(key).size(); i++) {
				RegistryObject<StairsBlock> rb = BlockInit.SSW_SET_STAIRS.get("lmd-color").get(key).get(i);
				Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd-color").get(key).get(i).get();
				stairs(p_200404_1_, rb.get(), craftingItem, key, "has_" + craftingItem.getRegistryName().getPath().split("_")[0]);
			}

		});

	}


	private static void stairs(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockedBy) {
		ShapedRecipeBuilder.shaped(resultItem, 4).define('#', craftingItem).pattern("#  ").pattern("## ").pattern("###").group(group + "_stairs").unlockedBy(unlockedBy, has(craftingItem)).save(p_240480_0_);
	}

	private static void stoneStairs(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		stairs(p_240480_0_, resultItem, craftingItem, group, "has_stone");
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem).unlocks("has_stone", has(craftingItem)).save(p_240480_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
	}

	private static void slab(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockedBy) {
		ShapedRecipeBuilder.shaped(resultItem, 6).define('#', craftingItem).pattern("###").group(group + "_slab").unlockedBy(group, has(craftingItem)).save(p_240479_0_);
		ShapedRecipeBuilder.shaped(craftingItem).define('#', resultItem).pattern("#").pattern("#").unlockedBy(unlockedBy, has(resultItem)).save(p_240479_0_, new ResourceLocation(SWDM.MOD_ID, craftingItem.asItem() + "_from_slabs")); // Two slabs to block
	}

	private static void stoneSlab(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		slab(p_240479_0_, resultItem, craftingItem, group, "has_stone");
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem, 2).unlocks("has_stone", has(craftingItem)).save(p_240479_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
	}

	private static void wall(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockedBy) {
		ShapedRecipeBuilder.shaped(resultItem, 6).define('#', craftingItem).pattern("###").pattern("###").group(group + "_wall").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
	}

	private static void stoneWall(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		wall(p_240479_0_, resultItem, craftingItem, group, "has_stone");
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem).unlocks("has_stone", has(craftingItem)).save(p_240479_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
	}

	private static void pressurePlate(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockedBy) {
		ShapedRecipeBuilder.shaped(resultItem, 1).define('#', craftingItem).pattern("##").group(group + "_pressure_plate").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
	}

	private static void stonePressurePlate(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		pressurePlate(p_240479_0_, resultItem, craftingItem, group, "has_stone");
	}

	private static void button(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockedBy) {
		ShapelessRecipeBuilder.shapeless(resultItem, 1).requires(craftingItem).group(group + "_button").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
	}

	private static void stoneButton(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		button(p_240479_0_, resultItem, craftingItem, group, "has_stone");
	}

	private static void carpet(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		ShapedRecipeBuilder.shaped(resultItem, 3).define('#', craftingItem).pattern("##").group(group).unlockedBy("has_something", has(craftingItem)).save(p_240479_0_);
	}

	private static void glassPane(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider resultItem, IItemProvider craftingItem, String group) {
		ShapelessRecipeBuilder.shapeless(resultItem, 16).requires(craftingItem).group(group).unlockedBy("has_something", has(craftingItem)).save(p_240479_0_);
	}
}