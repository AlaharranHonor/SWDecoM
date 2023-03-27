package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.BeamBlock;
import com.alaharranhonor.swdm.block.CoatedChain;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;

public class BlockStates extends BlockStateProvider {


    public BlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.sets();
        this.misc();
    }

    private void misc() {
        this.simpleBlock(BlockInit.CLAY_BLOCK_LIGHT.get());
        this.simpleBlock(BlockInit.CLAY_BLOCK_MEDIUM.get());
        this.simpleBlock(BlockInit.CLAY_BLOCK_DARK.get());
        this.halfWallBlock(BlockInit.STONE_WALL.get(), this.mcLoc("block/stone"));
        this.models().wallInventory(BlockInit.STONE_WALL.getId().getPath() + "_inventory", this.mcLoc("block/stone"));
        this.slabBlock(BlockInit.GRASS_SLAB.get(), this.mcLoc("block/grass"), this.mcLoc("block/grass"));
        this.slabBlock(BlockInit.DIRT_SLAB.get(), this.mcLoc("block/grass"), this.mcLoc("block/grass"));

        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_LIGHT.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_LIGHT.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_MEDIUM.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_MEDIUM.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_DARK.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_DARK.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.STONE_WALL.getId().getPath(), this.modLoc("block/" + BlockInit.STONE_WALL.getId().getPath() + "_inventory"));
        this.itemModels().withExistingParent(BlockInit.GRASS_SLAB.getId().getPath(), this.modLoc("block/" + BlockInit.GRASS_SLAB.getId().getPath() + "_top"));
        this.itemModels().withExistingParent(BlockInit.DIRT_SLAB.getId().getPath(), this.modLoc("block/" + BlockInit.DIRT_SLAB.getId().getPath()));
    }

    private void sets() {

        for (DyeColor color : DyeColor.values()) {
            coatedChain(color);
        }

        BlockInit.SSW_SET_BEAMS.keySet().forEach((key) -> {
            BlockInit.SSW_SET_BEAMS.get(key).keySet().forEach((wood) -> {
                for (RegistryObject<Block> rb : BlockInit.SSW_SET_BEAMS.get(key).get(wood)) {
                    String name = rb.getId().getPath();
                    this.getVariantBuilder(rb.get()).forAllStates(state -> {
                        SWDMBlockstateProperties.Tileable tile = state.getValue(BeamBlock.TILE);
                        return ConfiguredModel.builder()
                            .modelFile(this.models().cubeColumn(
                                name + "_" + tile.getSerializedName(),
                                this.modLoc("block/" + name + "_" + tile.getSerializedName()),
                                this.modLoc("block/" + name + "_single")
                            )).build();
                    });

                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_single")); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_BLOCKS.keySet().forEach((key) -> {
            BlockInit.STONE_SET_BLOCKS.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<Block> rb : BlockInit.STONE_SET_BLOCKS.get(key).get(key2)) {

                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || rb.getId().getPath().contains("terracotta")) {
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    } else {
                        texture = modLoc("blocks/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 6));
                    }
                    this.simpleBlock(rb.get());
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_SLABS.keySet().forEach((key) -> {
            BlockInit.STONE_SET_SLABS.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<SlabBlock> rb : BlockInit.STONE_SET_SLABS.get(key).get(key2)) {

                    ResourceLocation doubleSlab;
                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !key.equals("color_custom"))) {
                        doubleSlab = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    } else {
                        doubleSlab = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                        texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    }
                    this.slabBlock(rb.get(), doubleSlab, texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_WALLS.keySet().forEach((key) -> {
            BlockInit.STONE_SET_WALLS.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<WallBlock> rb : BlockInit.STONE_SET_WALLS.get(key).get(key2)) {

                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !key.equals("color_custom"))) {
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    } else {
                        texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    }
                    this.wallBlock(rb.get(), texture);
                    models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_STAIRS.keySet().forEach((key) -> {
            BlockInit.STONE_SET_STAIRS.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<StairBlock> rb : BlockInit.STONE_SET_STAIRS.get(key).get(key2)) {

                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !key.equals("color_custom"))) {
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                    } else {
                        texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                    }
                    this.stairsBlock(rb.get(), texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
            BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<PressurePlateBlock> rb : BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2)) {

                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !key.equals("color_custom"))) {
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 15));
                    } else {
                        texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 15));
                    }
                    this.pressurePlate(rb.get(), texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
                ;
            });
        });

        BlockInit.STONE_SET_BUTTONS.keySet().forEach((key) -> {
            BlockInit.STONE_SET_BUTTONS.get(key).keySet().forEach((key2) -> {
                for (RegistryObject<StoneButtonBlock> rb : BlockInit.STONE_SET_BUTTONS.get(key).get(key2)) {

                    ResourceLocation texture;
                    if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !key.equals("color_custom"))) {
                        texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                    } else {
                        texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                    }
                    this.button(rb.get(), texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
                }
                ;
            });
        });

        BlockInit.SSW_SET_SLABS.get("color").keySet().forEach((key) -> {
            if (!key.equals("wool") && !key.equals("stained_glass")) {
                for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("color").get(key)) {
                    ResourceLocation texture;

                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    this.simpleBlock(rb.get());
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("color").get(key)) {
                ResourceLocation doubleSlab;
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    doubleSlab = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                } else {
                    doubleSlab = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                }
                this.slabBlock(rb.get(), doubleSlab, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("color").get(key)) {
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                }
                this.wallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("color").get(key)) {
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                }
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            if (key.equals("wool_pastel") || key.equals("wool_tinted")) {
                for (RegistryObject<CarpetBlock> rb : BlockInit.SSW_SET_CARPETS.get("color").get(key)) {
                    ResourceLocation texture;

                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));

                    this.carpet(rb.get(), texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
            }

        });

        BlockInit.SSW_SET_SLABS.get("wv").keySet().forEach((key) -> {
            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("wv").get(key)) {
                ResourceLocation doubleSlab;
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                doubleSlab = mcLoc("block/" + blockName);
                texture = mcLoc("block/" + blockName);

                this.slabBlock(rb.get(), doubleSlab, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model

            }

            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("wv").get(key)) {
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                texture = mcLoc("block/" + blockName);

                this.wallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }


            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("wv").get(key)) {
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                texture = mcLoc("block/" + blockName);
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

        });

        BlockInit.SSW_SET_SLABS.get("wv-whitewash").keySet().forEach((key) -> {
            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("wv-whitewash").get(key)) {
                ResourceLocation doubleSlab;
                ResourceLocation texture;
                doubleSlab = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));


                this.slabBlock(rb.get(), models().withExistingParent(rb.getId().getPath(), modLoc("block/slab"))
                        .texture("side", texture)
                        .texture("bottom", texture)
                        .texture("top", texture),
                    models().withExistingParent(rb.getId().getPath() + "_top", modLoc("block/slab_top"))
                        .texture("side", texture)
                        .texture("bottom", texture)
                        .texture("top", texture), models().getExistingFile(doubleSlab));
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model

            }

            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("wv-whitewash").get(key)) {
                ResourceLocation texture;
                texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));

                if (key.equals("leaves")) {
                    wallBlock(rb.get(), models().singleTexture(rb.getId().getPath() + "_post", modLoc("block/template_wall_post"), "wall", texture), models().singleTexture(rb.getId().getPath() + "_side", modLoc("block/template_wall_side"), "wall", texture), models().singleTexture(rb.getId().getPath() + "_side_tall", modLoc("block/template_wall_side_tall"), "wall", texture));
                    this.models().singleTexture(rb.getId().getPath() + "_inventory", modLoc("block/wall_inventory"), "wall", texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
                } else {
                    this.wallBlock(rb.get(), texture);
                    this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
                }
            }


            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("wv-whitewash").get(key)) {
                ResourceLocation texture;
                texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));

                ModelFile stairs = models().withExistingParent(rb.getId().toString(), modLoc("block/stairs"))
                    .texture("side", texture)
                    .texture("bottom", texture)
                    .texture("top", texture);
                ModelFile stairsInner = models().withExistingParent(rb.getId().toString() + "_inner", modLoc("block/inner_stairs"))
                    .texture("side", texture)
                    .texture("bottom", texture)
                    .texture("top", texture);
                ModelFile stairsOuter = models().withExistingParent(rb.getId().toString() + "_outer", modLoc("block/outer_stairs"))
                    .texture("side", texture)
                    .texture("bottom", texture)
                    .texture("top", texture);

                this.stairsBlock(rb.get(), stairs, stairsInner, stairsOuter);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

        });


        BlockInit.SSW_SET_SLABS.get("lmd").keySet().forEach((key) -> {
            for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd").get(key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.simpleBlock(rb.get());
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }


            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("lmd").get(key)) {
                ResourceLocation doubleSlab;
                ResourceLocation texture;

                doubleSlab = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));

                this.slabBlock(rb.get(), doubleSlab, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model

            }


            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("lmd").get(key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.wallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }


            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("lmd").get(key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));

                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<StainedGlassPaneBlock> rb : BlockInit.SSW_SET_GLASS_PANES.get("lmd").get(key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 11));

                this.paneBlock(rb.get(), texture, texture);
                this.itemModels().singleTexture(rb.getId().getPath(), mcLoc("item/generated"), "layer0", texture); // Item model
            }

        });


        BlockInit.SSW_SET_SLABS.get("lmd-color").keySet().forEach((key) -> {
            for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd-color").get(key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.simpleBlock(rb.get());
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("lmd-color").get(key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.slabBlock(rb.get(), texture, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model

            }

            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("lmd-color").get(key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.wallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("lmd-color").get(key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }
        });



		/*BlockInit.STONE_SET_PRESSURE_PLATES.keySet().forEach((key) -> {
			BlockInit.STONE_SET_PRESSURE_PLATES.get(key).keySet().forEach((key2) -> {
				for (RegistryObject<PressurePlateBlock> rb : BlockInit.STONE_SET_PRESSURE_PLATES.get(key).get(key2)) {

					ResourceLocation texture;
					if (rb.getId().getPath().contains("concrete") || rb.getId().getPath().contains("terracotta")) {
						texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
					} else {
						texture = modLoc("blocks/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
					}

					this.getVariantBuilder(rb.get())
						.addModels(
						this.getVariantBuilder(rb.get()).partialState().with(PressurePlateBlock.POWERED, false),
								models().singleTexture(rb.getId().getPath(), mcLoc("block/pressure_plate_up"), texture)
				);



					this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
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
                        typeName = "stripped_" + wood.name() + "_log";
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

        for (String tone : SWDM.NATURAL_TONES) {
            this.simpleBlock(BlockInit.SAND_BLOCKS.get(tone).get());
            itemModels().withExistingParent(BlockInit.SAND_BLOCKS.get(tone).getId().getPath(), modLoc("block/" + BlockInit.SAND_BLOCKS.get(tone).getId().getPath()));

            this.simpleBlock(BlockInit.SANDSTONE_BLOCKS.get(tone).get(), models().cubeBottomTop(BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath(), modLoc("block/sandstone_" + tone), modLoc("block/sandstone_bottom_" + tone), modLoc("block/sandstone_top_" + tone)));
            itemModels().withExistingParent(BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath(), modLoc("block/" + BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath()));

            for (String lmdType : SWDM.LMD_TYPES) {
                CarpetBlock carpet = BlockInit.FIBER_CARPETS.get(lmdType, tone).get();
                this.carpet(carpet, modLoc("block/" + carpet.getRegistryName().getPath()));
                this.itemModels().withExistingParent(carpet.getRegistryName().getPath(), modLoc("block/" + carpet.getRegistryName().getPath()));
            }
        }

        List.of("sand", "red_sand").forEach(sand -> {
            for (String lmdType : SWDM.LMD_TYPES) {
                CarpetBlock carpet = BlockInit.FIBER_CARPETS.get(lmdType, sand).get();
                this.carpet(carpet, modLoc("block/" + carpet.getRegistryName().getPath()));
                this.itemModels().withExistingParent(carpet.getRegistryName().getPath(), modLoc("block/" + carpet.getRegistryName().getPath()));
            }
        });

        BlockInit.SSW_SET_SLABS.get("standalone").keySet().forEach((key) -> {
            for (RegistryObject<SlabBlock> rb : BlockInit.SSW_SET_SLABS.get("standalone").get(key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.slabBlock(rb.get(), texture, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<WallBlock> rb : BlockInit.SSW_SET_WALLS.get("standalone").get(key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.wallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSW_SET_STAIRS.get("standalone").get(key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }
        });
    }

    public void halfWallBlock(HalfWallBlock block, ResourceLocation texture) {
        halfWallBlockInternal(block, block.getRegistryName().toString(), texture);
    }

    public void halfWallBlock(HalfWallBlock block, String name, ResourceLocation texture) {
        halfWallBlockInternal(block, name + "_wall", texture);
    }

    private void halfWallBlockInternal(HalfWallBlock block, String baseName, ResourceLocation texture) {
        halfWallBlock(block,
            wallPost(baseName + "_post", texture), wallSide(baseName + "_side", texture), wallSideTall(baseName + "_side_tall", texture),
            halfWallPost(baseName + "_half_post", texture), halfWallSide(baseName + "_half_side", texture)
        );
    }

    public void halfWallBlock(HalfWallBlock block,
                              ModelFile post, ModelFile side, ModelFile sideTall,
                              ModelFile halfPost, ModelFile halfSide
    ) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
            .part().modelFile(post).addModel()
                .condition(HalfWallBlock.UP, true)
                .condition(HalfWallBlock.WALL_TYPE, SWDMBlockstateProperties.WallType.FULL).end()
            .part().modelFile(halfPost).addModel()
                .condition(HalfWallBlock.UP, true)
                .condition(HalfWallBlock.WALL_TYPE, SWDMBlockstateProperties.WallType.LOWER).end()
            .part().modelFile(halfPost).rotationX(180).addModel()
                .condition(HalfWallBlock.UP, true)
                .condition(HalfWallBlock.WALL_TYPE, SWDMBlockstateProperties.WallType.UPPER).end();

        WALL_PROPS.entrySet().stream()
            .filter(e -> e.getKey().getAxis().isHorizontal())
            .forEach(entry -> {
                for (SWDMBlockstateProperties.WallType type : SWDMBlockstateProperties.WallType.values()) {
                    halfWallSidePart(builder, side, halfSide, entry, WallSide.LOW, type);
                    halfWallSidePart(builder, sideTall, halfSide, entry, WallSide.TALL, type);
                }
            });
    }

    private void halfWallSidePart(MultiPartBlockStateBuilder builder, ModelFile fullWall, ModelFile halfWall, Map.Entry<Direction, Property<WallSide>> entry, WallSide height, SWDMBlockstateProperties.WallType type) {
        builder.part()
            .modelFile(type == SWDMBlockstateProperties.WallType.FULL ? fullWall : halfWall)
            .rotationX(type == SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)
            .rotationY((((int) entry.getKey().toYRot()) + (type != SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), height)
            .condition(HalfWallBlock.WALL_TYPE, type);
    }

    public BlockModelBuilder wallPost(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_wall_post"), "wall", wall);
    }

    public BlockModelBuilder wallSide(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_wall_side"), "wall", wall);
    }

    public BlockModelBuilder wallSideTall(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_wall_side_tall"), "wall", wall);
    }

    public BlockModelBuilder halfWallPost(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_half_wall_post"), "wall", wall);
    }

    public BlockModelBuilder halfWallSide(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_half_wall_side"), "wall", wall);
    }

    /*public BlockModelBuilder halfWallSideTall(String name, ResourceLocation wall) {
        return models().singleTexture(name, this.modLoc("block/template_half_wall_side_tall"), "wall", wall);
    }*/

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
