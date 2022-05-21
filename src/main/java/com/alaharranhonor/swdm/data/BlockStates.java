package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.CoatedChain;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class BlockStates extends BlockStateProvider {


	public BlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

		for (DyeColor color : DyeColor.values()) {
			coatedChain(color);
		}

		BlockInit.STONE_SET_BLOCKS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_BLOCKS.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<Block> rb : BlockInit.STONE_SET_BLOCKS.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || rb.get().getRegistryName().getPath().contains("terracotta")) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					} else {
						texture = modLoc("blocks/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 6));
					}
					this.simpleBlock(rb.get());
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				};
			});
		});

		BlockInit.STONE_SET_SLABS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_SLABS.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<SlabBlock> rb : BlockInit.STONE_SET_SLABS.get(key).get(key2)) {

					ResourceLocation doubleSlab;
					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						doubleSlab =  mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					} else {
						doubleSlab = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
						texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					}
					this.slabBlock(rb.get(), doubleSlab, texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				};
			});
		});

		BlockInit.STONE_SET_WALLS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_WALLS.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<WallBlock> rb : BlockInit.STONE_SET_WALLS.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					} else {
						texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					}
					this.wallBlock(rb.get(), texture);
					models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
				};
			});
		});

		BlockInit.STONE_SET_STAIRS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_STAIRS.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<StairBlock> rb : BlockInit.STONE_SET_STAIRS.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
					} else {
						texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
					}
					this.stairsBlock(rb.get(), texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				};
			});
		});

		BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
			BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<PressurePlateBlock> rb : BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 15));
					} else {
						texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 15));
					}
					this.pressurePlate(rb.get(), texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				};
			});
		});

		BlockInit.STONE_SET_BUTTONS.keySet().forEach((key) -> {
			BlockInit.STONE_SET_BUTTONS.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<StoneButtonBlock> rb : BlockInit.STONE_SET_BUTTONS.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !key.equals("color_custom"))) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
					} else {
						texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
					}
					this.button(rb.get(), texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
				};
			});
		});

		BlockInit.SSW_SET_SLABS.get("color").keySet().forEach((key) -> {
			for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("color").get(key)) {
				ResourceLocation doubleSlab;
				ResourceLocation texture;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					doubleSlab =  mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				} else {
					doubleSlab = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				}
				this.slabBlock(rb.get(), doubleSlab, texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model

			}

			for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("color").get(key)) {
				ResourceLocation texture;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				} else {
					texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				}
				this.wallBlock(rb.get(), texture);
				this.models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
			}

			if (!key.equals("wool") && !key.equals("stained_glass")) {
				for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("color").get(key)) {
					ResourceLocation texture;

					texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					this.simpleBlock(rb.get());
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				}
			}


			for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("color").get(key)) {
				ResourceLocation texture;
				if (key.equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
					texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
				} else {
					texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
				}
				this.stairsBlock(rb.get(), texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}

			if (key.equals("pastel_wool") || key.equals("tinted_wool")) {
				for (RegistryObject<CarpetBlock> rb : BlockInit.SSW_SET_CARPETS.get("color").get(key)) {
					ResourceLocation texture;

					texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));

					this.carpet(rb.get(), texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				}
			}

		});

		BlockInit.SSW_SET_SLABS.get("wv").keySet().forEach((key) -> {
			for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("wv").get(key)) {
				ResourceLocation doubleSlab;
				ResourceLocation texture;
				doubleSlab =  mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));

				this.slabBlock(rb.get(), doubleSlab, texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model

			}

			for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("wv").get(key)) {
				ResourceLocation texture;
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));

				this.wallBlock(rb.get(), texture);
				this.models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
			}


			for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("wv").get(key)) {
				ResourceLocation texture;
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
				this.stairsBlock(rb.get(), texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}

		});

		BlockInit.SSW_SET_SLABS.get("wv-whitewash").keySet().forEach((key) -> {
			for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("wv-whitewash").get(key)) {
				ResourceLocation doubleSlab;
				ResourceLocation texture;
				doubleSlab =  mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));



				this.slabBlock(rb.get(), models().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/slab"))
					.texture("side", texture)
					.texture("bottom", texture)
					.texture("top", texture),
					models().withExistingParent(rb.get().getRegistryName().getPath() + "_top", modLoc("block/slab_top"))
						.texture("side", texture)
						.texture("bottom", texture)
						.texture("top", texture), models().getExistingFile(doubleSlab));
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model

			}

			for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("wv-whitewash").get(key)) {
				ResourceLocation texture;
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));

				if (key.equals("leaves")) {
					wallBlock(rb.get(), models().singleTexture(rb.get().getRegistryName().getPath() + "_post",  modLoc("block/template_wall_post"), "wall", texture), models().singleTexture(rb.get().getRegistryName().getPath() + "_side", modLoc("block/template_wall_side"), "wall", texture), models().singleTexture(rb.get().getRegistryName().getPath() + "_side_tall",  modLoc("block/template_wall_side_tall"), "wall", texture));
					this.models().singleTexture(rb.get().getRegistryName().getPath() + "_inventory", modLoc("block/wall_inventory"), "wall", texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
				} else {
					this.wallBlock(rb.get(), texture);
					this.models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
				}
			}


			for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get(key)) {
				ResourceLocation texture;
				texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));

				ModelFile stairs = models().withExistingParent(rb.get().getRegistryName().toString(), modLoc("block/stairs"))
					.texture("side", texture)
					.texture("bottom", texture)
					.texture("top", texture);
				ModelFile stairsInner = models().withExistingParent(rb.get().getRegistryName().toString() + "_inner", modLoc("block/inner_stairs"))
					.texture("side", texture)
					.texture("bottom", texture)
					.texture("top", texture);
				ModelFile stairsOuter = models().withExistingParent(rb.get().getRegistryName().toString() + "_outer", modLoc("block/outer_stairs"))
					.texture("side", texture)
					.texture("bottom", texture)
					.texture("top", texture);

				this.stairsBlock(rb.get(), stairs, stairsInner, stairsOuter);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}

		});


		BlockInit.SSW_SET_SLABS.get("lmd").keySet().forEach((key) -> {
			for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("lmd").get(key)) {
				ResourceLocation doubleSlab;
				ResourceLocation texture;

				doubleSlab = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));

				this.slabBlock(rb.get(), doubleSlab, texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model

			}

			for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("lmd").get(key)) {
				ResourceLocation texture;

				texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				this.wallBlock(rb.get(), texture);
				this.models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
			}

			for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd").get(key)) {
				ResourceLocation texture;

				texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				this.simpleBlock(rb.get());
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}


			for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("lmd").get(key)) {
				ResourceLocation texture;

				texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));

				this.stairsBlock(rb.get(), texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}

			for (RegistryObject<StainedGlassPaneBlock> rb : BlockInit.SSW_SET_GLASS_PANES.get("lmd").get(key)) {
				ResourceLocation texture;

				texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 11));

				this.paneBlock(rb.get(), texture, texture);
				this.itemModels().singleTexture(rb.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", texture); // Item model
			}

		});


		BlockInit.SSW_SET_SLABS.get("lmd-color").keySet().forEach((key) -> {
			for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd-color").get(key)) {
				ResourceLocation texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				this.simpleBlock(rb.get());
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}

			for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("lmd-color").get(key)) {
				ResourceLocation texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				this.slabBlock(rb.get(), texture, texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model

			}

			for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("lmd-color").get(key)) {
				ResourceLocation texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
				this.wallBlock(rb.get(), texture);
				this.models().wallInventory(rb.get().getRegistryName().getPath() + "_inventory", texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath() + "_inventory")); // Item model
			}

			for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("lmd-color").get(key)) {
				ResourceLocation texture = modLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7));
				this.stairsBlock(rb.get(), texture);
				this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
			}
		});



		/*BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
			BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<PressurePlateBlock> rb : BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.get().getRegistryName().getPath().contains("concrete") || rb.get().getRegistryName().getPath().contains("terracotta")) {
						texture = mcLoc("block/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					} else {
						texture = modLoc("blocks/" + rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5));
					}

					this.getVariantBuilder(rb.get())
						.addModels(
						this.getVariantBuilder(rb.get()).partialState().with(PressurePlateBlock.POWERED, false),
								models().singleTexture(rb.get().getRegistryName().getPath(), mcLoc("block/pressure_plate_up"), texture)
				);



					this.itemModels().withExistingParent(rb.get().getRegistryName().getPath(), modLoc("block/" + rb.get().getRegistryName().getPath())); // Item model
				};
			});
		});*/

		for (String stoneType : SWDM.STONE_TYPES) {

			for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("standard").entrySet()) {

			}

			for (String lmdType : SWDM.LMD_TYPES) {
				for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd").entrySet()) {

				}
			}

		}

		for (DyeColor color : DyeColor.values()) {

			for (Map.Entry<String, Block.Properties> blockType : SWDM.SSW_SETS.get("color").entrySet()) {

			}

			for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("color").entrySet()) {

			}


		}

		WoodType.values().forEach((wood) -> {

			if (!wood.name().equals("crimson") && !wood.name().equals("warped") && !wood.name().contains("swdm:")) {

				for (Map.Entry<String, Block.Properties> blockType : SWDM.SSW_SETS.get("wv").entrySet()) {
					String typeName;
					if (blockType.getKey().equals("stripped_log")) {
						typeName = "stripped_"+wood.name()+"_log";
					} else {
						typeName = wood.name() + "_" + blockType.getKey();
					}

				}

				for (Map.Entry<String, Block.Properties> blockType : SWDM.SSW_SETS.get("wv-whitewash").entrySet()) {
					if (wood.name().contains("swem:")) break;


				}
			}

			if (wood.name().equals("crimson") || wood.name().equals("warped") || wood.name().contains("swdm:") || wood.name().contains("swem:")) {

			} else {
				//LEAVES_TRAPDOORS.add(register(wood.name() + "_leaves_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).sound(SoundType.GRASS).noOcclusion())));
				//LADDERS.add(register(wood.name() + "_ladder", () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER))));

			}
			// All other vanilla wood types.

		});

		SWDM.LMD_TYPES.forEach((lmdType) -> {
			for (Map.Entry<String, Block.Properties> blockType : SWDM.SSW_SETS.get("lmd").entrySet()) {
			}

			for (Map.Entry<String, Block.Properties> blockType : SWDM.STONE_SETS.get("lmd-only").entrySet()) {

			}
		});

		for (int i = 0; i < SWDM.NATURAL_TONES.size(); i++) {
			String natural = SWDM.NATURAL_TONES.get(i);

			this.simpleBlock(BlockInit.SAND_BLOCKS.get(i).get());
			itemModels().withExistingParent(BlockInit.SAND_BLOCKS.get(i).get().getRegistryName().getPath(), modLoc("block/" + BlockInit.SAND_BLOCKS.get(i).get().getRegistryName().getPath()));

			this.simpleBlock(BlockInit.SANDSTONE_BLOCKS.get(i).get(), models().cubeBottomTop(BlockInit.SANDSTONE_BLOCKS.get(i).get().getRegistryName().getPath(), modLoc("block/sandstone_" + natural), modLoc("block/sandstone_bottom_" + natural), modLoc("block/sandstone_top_" + natural)));
			itemModels().withExistingParent(BlockInit.SANDSTONE_BLOCKS.get(i).get().getRegistryName().getPath(), modLoc("block/" + BlockInit.SANDSTONE_BLOCKS.get(i).get().getRegistryName().getPath()));

			this.carpet(BlockInit.FIBER_CARPETS.get(i).get(), modLoc("block/" + BlockInit.FIBER_CARPETS.get(i).get().getRegistryName().getPath()));
			this.itemModels().withExistingParent(BlockInit.FIBER_CARPETS.get(i).get().getRegistryName().getPath(), modLoc("block/" + BlockInit.FIBER_CARPETS.get(i).get().getRegistryName().getPath()));
		}
	}

	public void carpet(CarpetBlock block, ResourceLocation wool) {
		ModelFile carpetModel = models().carpet(block.getRegistryName().toString(), wool);
		this.getVariantBuilder(block)
			.addModels(this.getVariantBuilder(block).partialState(), new ConfiguredModel(carpetModel));
	}

	public void pressurePlate(PressurePlateBlock block, ResourceLocation texture) {
		ModelFile pressurePlateUp = models().singleTexture(block.getRegistryName().toString(), mcLoc("block/pressure_plate_up"), texture);
		ModelFile pressurePlateDown = models().singleTexture(block.getRegistryName().toString() + "_down", mcLoc("block/pressure_plate_down"), texture);

		this.getVariantBuilder(block)
			.partialState().with(PressurePlateBlock.POWERED, true)
				.modelForState().modelFile(pressurePlateDown).addModel()
			.partialState().with(PressurePlateBlock.POWERED, false)
				.modelForState().modelFile(pressurePlateUp).addModel();

	}

	public void button(ButtonBlock block, ResourceLocation texture) {
		ModelFile button = models().singleTexture(block.getRegistryName().toString(), mcLoc("block/button"), texture);
		ModelFile buttonPressed = models().singleTexture(block.getRegistryName().toString() + "_pressed", mcLoc("block/button_pressed"), texture);
		models().singleTexture(block.getRegistryName().toString() + "_inventory", mcLoc("block/button_inventory"), texture);

		this.getVariantBuilder(block)
			.forAllStates((state) -> {
				int yRot = ((int) state.getValue(TrapDoorBlock.FACING).toYRot()) + 180;
				int xRot = 0;
				if (state.getValue(ButtonBlock.FACE) == AttachFace.CEILING) {
					xRot = 180;
				} else if (state.getValue(ButtonBlock.FACE) == AttachFace.WALL) {
					xRot = 90;
				}

				return ConfiguredModel.builder().modelFile(state.getValue(ButtonBlock.POWERED) ? buttonPressed : button)
					.rotationX(xRot)
					.rotationY(yRot)
					.build();
			});
	}

	public void coatedChain(DyeColor color) {
		CoatedChain coatedChain = BlockInit.COATED_CHAINS.get(color.getId()).get();
		ModelFile single = models().singleTexture(coatedChain.getRegistryName().getPath() + "_single", mcLoc("block/chain"), "all", modLoc("block/" + coatedChain.getRegistryName().getPath().substring(0, coatedChain.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_single_" + color.getName()));
		ModelFile edge = models().singleTexture(coatedChain.getRegistryName().getPath() + "_edge", mcLoc("block/chain"), "all", modLoc("block/" + coatedChain.getRegistryName().getPath().substring(0, coatedChain.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_edge_" + color.getName()));
		ModelFile middle = models().singleTexture(coatedChain.getRegistryName().getPath() + "_middle", mcLoc("block/chain"), "all", modLoc("block/" + coatedChain.getRegistryName().getPath().substring(0, coatedChain.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_middle_" + color.getName()));

		this.itemModels().singleTexture(coatedChain.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", new ResourceLocation("swdm", "items/" + coatedChain.getRegistryName().getPath()));

		this.getVariantBuilder(coatedChain).forAllStates((state) -> {
			ModelFile fileToUse = state.getValue(CoatedChain.PART) == SWDMBlockstateProperties.TwoWay.SINGLE
				? single
				: state.getValue(CoatedChain.PART) == SWDMBlockstateProperties.TwoWay.MIDDLE
				? middle
				: edge;

			return ConfiguredModel.builder().modelFile(fileToUse)
				.rotationX(state.getValue(CoatedChain.AXIS).isHorizontal() ? 90 : (state.getValue(CoatedChain.PART) == SWDMBlockstateProperties.TwoWay.RIGHT ? 180 : 0))
				.rotationY((state.getValue(CoatedChain.AXIS) == Direction.Axis.X ? 90 : 0) + (state.getValue(CoatedChain.PART) == SWDMBlockstateProperties.TwoWay.RIGHT && state.getValue(CoatedChain.AXIS) == Direction.Axis.X ? 180 : state.getValue(CoatedChain.PART) == SWDMBlockstateProperties.TwoWay.LEFT && state.getValue(CoatedChain.AXIS) == Direction.Axis.Z ? 180 : 0))
				.build();
		});
	}







		/*for (DyeColor color : DyeColor.values()) {



			//this.stairsBlock(BlockInit.WOOL_STAIRS.get(color.getId()).get(), mcLoc("block/" + color.getName() + "_wool")); // Blockstate and models
			//this.models().stairs(color.getName() + "_wool_stairs", mcLoc("block/" + color.getName() + "_wool"), mcLoc("block/" + color.getName() + "_wool"), mcLoc("block/" + color.getName() + "_wool")); // Block Model
			this.itemModels().withExistingParent(color.getName() + "_wool_stairs", modLoc("block/" + color.getName() + "_wool_stairs")); // Item model
		}
	}*/




}
