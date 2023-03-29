package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.BeamBlock;
import com.alaharranhonor.swdm.block.TwoWayBlock;
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
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
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

        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_LIGHT.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_LIGHT.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_MEDIUM.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_MEDIUM.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.CLAY_BLOCK_DARK.getId().getPath(), this.modLoc("block/" + BlockInit.CLAY_BLOCK_DARK.getId().getPath()));
        this.itemModels().withExistingParent(BlockInit.STONE_WALL.getId().getPath(), this.modLoc("block/" + BlockInit.STONE_WALL.getId().getPath() + "_inventory"));
    }

    private void sets() {

        for (DyeColor color : DyeColor.values()) {
            twoWayBlock(BlockInit.COATED_CHAINS.get(color.getName()).get(), color);
            twoWayBlock(BlockInit.PYLONS.get(color.getName()).get(), color);
        }

        BlockInit.STONE_SET_BLOCKS.items().forEach((rb) -> {
            ResourceLocation texture;
            if (rb.getId().getPath().contains("concrete") || rb.getId().getPath().contains("terracotta")) {
                texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
            } else {
                texture = modLoc("blocks/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 6));
            }
            this.simpleBlock(rb.get());
            this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
        });

        BlockInit.STONE_SET_STAIRS.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                ResourceLocation texture;
                if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                }
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            });
        });

        BlockInit.STONE_SET_SLABS.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                ResourceLocation doubleSlab;
                ResourceLocation texture;
                if (rb.getId().getPath().contains("concrete") || (rb.getId().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    doubleSlab = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                } else {
                    doubleSlab = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                }
                this.slabBlock(rb.get(), doubleSlab, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            });
        });

        BlockInit.STONE_SET_WALLS.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                String[] vanillaTypes = new String[] {"stone", "andesite", "granite", "diorite", "concrete"};
                boolean isVanilla = Arrays.stream(vanillaTypes).anyMatch(name::equals);
                ResourceLocation texture;
                if (isVanilla || name.contains("concrete") || name.contains("terracotta") && !cell.getRowKey().equals("color_custom")) {
                    texture = mcLoc("block/" + name);
                } else {
                    texture = modLoc("block/" + name);
                }
                this.halfWallBlock(rb.get(), texture);
                models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            });
        });

        BlockInit.STONE_SET_TRAPDOORS.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9);
                String[] vanillaTypes = new String[] {"stone", "andesite", "granite", "diorite", "concrete"};
                boolean isVanilla = Arrays.stream(vanillaTypes).anyMatch(name::equals);
                ResourceLocation texture;
                if (isVanilla || name.contains("concrete") || name.contains("terracotta") && !cell.getRowKey().equals("color_custom")) {
                    texture = mcLoc("block/" + name);
                } else {
                    texture = modLoc("block/" + name);
                }
                this.trapdoorBlock(rb.get(), texture, true);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            });
        });

        BlockInit.STONE_SET_PRESSURE_PLATES.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 15);
                String[] vanillaTypes = new String[] {"stone", "andesite", "granite", "diorite", "concrete"};
                boolean isVanilla = Arrays.stream(vanillaTypes).anyMatch(name::equals);
                ResourceLocation texture;
                if (isVanilla || name.contains("concrete") || name.contains("terracotta") && !cell.getRowKey().equals("color_custom")) {
                    texture = mcLoc("block/" + name);
                } else {
                    texture = modLoc("block/" + name);
                }
                this.pressurePlate(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            });
        });

        BlockInit.STONE_SET_BUTTONS.cellSet().forEach(cell -> {
            cell.getValue().forEach((rb) -> {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7);
                String[] vanillaTypes = new String[] {"stone", "andesite", "granite", "diorite", "concrete"};
                boolean isVanilla = Arrays.stream(vanillaTypes).anyMatch(name::equals);
                ResourceLocation texture;
                if (isVanilla || name.contains("concrete") || name.contains("terracotta") && !cell.getRowKey().equals("color_custom")) {
                    texture = mcLoc("block/" + name);
                } else {
                    texture = modLoc("block/" + name);
                }
                this.button(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            });
        });

        BlockInit.SSWT_SET_SLABS.row("color").keySet().forEach((key) -> {
            if (!key.equals("wool") && !key.equals("stained_glass")) {
                for (RegistryObject<Block> rb : BlockInit.SSWT_SET_BLOCKS.get("color", key)) {
                    ResourceLocation texture;

                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                    this.simpleBlock(rb.get());
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("color", key)) {
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                }
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("color", key)) {
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

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("color", key)) {
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                }
                this.halfWallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("color", key)) {
                ResourceLocation texture;
                if (key.equals("wool") || rb.getId().getPath().contains("stained_glass")) {
                    texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9));
                } else {
                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9));
                }
                this.trapdoorBlock(rb.get(), texture, true);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }


            if (key.equals("wool_pastel") || key.equals("wool_tinted")) {
                for (RegistryObject<CarpetBlock> rb : BlockInit.SSWT_SET_CARPETS.get("color", key)) {
                    ResourceLocation texture;

                    texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));

                    this.carpet(rb.get(), texture);
                    this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
                }
            }
        });

        BlockInit.SSWT_SET_SLABS.row("wv").keySet().forEach((key) -> {
            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("wv", key)) {
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

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("wv", key)) {
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                texture = mcLoc("block/" + blockName);

                this.halfWallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }


            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("wv", key)) {
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                texture = mcLoc("block/" + blockName);
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("wv", key)) {
                ResourceLocation texture;
                String blockName = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9);
                if (key.contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                texture = mcLoc("block/" + blockName);
                this.trapdoorBlock(rb.get(), texture, true);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }

        });

        BlockInit.SSWT_SET_SLABS.row("wv-extra").keySet().forEach((key) -> {
            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("wv-extra", key)) {
                ResourceLocation doubleSlab = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));

                this.tintedSlab(rb.get(), texture, doubleSlab);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("wv-extra", key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.tintedHalfWall(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("wv-extra", key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                this.tintedStairs(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("wv-extra", key)) {
                ResourceLocation texture = mcLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9));
                this.tintedTrapdoor(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }
        });

        BlockInit.SSWT_SET_SLABS.row("lmd").keySet().forEach((key) -> {
            for (RegistryObject<Block> rb : BlockInit.SSWT_SET_BLOCKS.get("lmd", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.simpleBlock(rb.get());
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("lmd", key)) {
                ResourceLocation doubleSlab = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));

                this.slabBlock(rb.get(), doubleSlab, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("lmd", key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.halfWallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }


            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("lmd", key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));

                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("lmd", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9));
                this.trapdoorBlock(rb.get(), texture, true);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }

            for (RegistryObject<StainedGlassPaneBlock> rb : BlockInit.SSWT_SET_GLASS_PANES.get("lmd", key)) {
                ResourceLocation texture;

                texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 11));

                this.paneBlock(rb.get(), texture, texture);
                this.itemModels().singleTexture(rb.getId().getPath(), mcLoc("item/generated"), "layer0", texture); // Item model
            }

        });


        BlockInit.SSWT_SET_SLABS.row("lmd-color").keySet().forEach((key) -> {
            for (RegistryObject<Block> rb : BlockInit.SSWT_SET_BLOCKS.get("lmd-color", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.simpleBlock(rb.get());
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("lmd-color", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.slabBlock(rb.get(), texture, texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("lmd-color", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5));
                this.halfWallBlock(rb.get(), texture);
                this.models().wallInventory(rb.getId().getPath() + "_inventory", texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("lmd-color", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7));
                this.stairsBlock(rb.get(), texture);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("lmd-color", key)) {
                ResourceLocation texture = modLoc("block/" + rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9));
                this.trapdoorBlock(rb.get(), texture, true);
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }
        });

        for (String tone : SWDM.NATURAL_TONES) {
            this.simpleBlock(BlockInit.SAND_BLOCKS.get(tone).get());
            itemModels().withExistingParent(BlockInit.SAND_BLOCKS.get(tone).getId().getPath(), modLoc("block/" + BlockInit.SAND_BLOCKS.get(tone).getId().getPath()));

            this.simpleBlock(BlockInit.SANDSTONE_BLOCKS.get(tone).get(), models().cubeBottomTop(BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath(), modLoc("block/sandstone_" + tone), modLoc("block/sandstone_bottom_" + tone), modLoc("block/sandstone_top_" + tone)));
            itemModels().withExistingParent(BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath(), modLoc("block/" + BlockInit.SANDSTONE_BLOCKS.get(tone).getId().getPath()));

            for (String lmdType : SWDM.LMD_TYPES) {
                CarpetBlock carpet = BlockInit.FIBER_CARPETS.get(lmdType, tone).get();
                this.horizontalCarpet(carpet, modLoc("block/" + carpet.getRegistryName().getPath()));
                this.itemModels().withExistingParent(carpet.getRegistryName().getPath(), modLoc("block/" + carpet.getRegistryName().getPath()));
            }
        }

        List.of("sand", "red_sand").forEach(sand -> {
            for (String lmdType : SWDM.LMD_TYPES) {
                CarpetBlock carpet = BlockInit.FIBER_CARPETS.get(lmdType, sand).get();
                this.horizontalCarpet(carpet, modLoc("block/" + carpet.getRegistryName().getPath()));
                this.itemModels().withExistingParent(carpet.getRegistryName().getPath(), modLoc("block/" + carpet.getRegistryName().getPath()));
            }
        });

        BlockInit.SSWT_SET_SLABS.row("standalone").keySet().forEach((key) -> {
            boolean isGrassyBlock = key.contains("grass_block") || key.contains("podzol") || key.contains("mycelium");

            for (RegistryObject<StairBlock> rb : BlockInit.SSWT_SET_STAIRS.get("standalone", key)) {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 7);
                if (isGrassyBlock) {
                    this.tintedStairs(rb.get(), mcLoc("block/" + name + "_side"), mcLoc("block/dirt"), mcLoc("block/" + name + "_top"));
                } else {
                    this.tintedStairs(rb.get(), mcLoc("block/" + name));
                }
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<SlabBlock> rb : BlockInit.SSWT_SET_SLABS.get("standalone", key)) {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                ResourceLocation blockModel = mcLoc("block/" + name);
                if (isGrassyBlock) {
                    this.tintedSlab(rb.get(), mcLoc("block/" + name + "_side"), mcLoc("block/dirt"), mcLoc("block/" + name + "_top"), blockModel);
                } else {
                    this.tintedSlab(rb.get(), blockModel, blockModel);
                }
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath())); // Item model
            }

            for (RegistryObject<HalfWallBlock> rb : BlockInit.SSWT_SET_WALLS.get("standalone", key)) {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 5);
                if (isGrassyBlock) {
                    this.tintedHalfWall(rb.get(), mcLoc("block/" + name + "_side"), mcLoc("block/dirt"), mcLoc("block/" + name + "_top"));
                } else {
                    this.tintedHalfWall(rb.get(), mcLoc("block/" + name));
                }
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_inventory")); // Item model
            }

            for (RegistryObject<TrapDoorBlock> rb : BlockInit.SSWT_SET_TRAPDOORS.get("standalone", key)) {
                String name = rb.getId().getPath().substring(0, rb.getId().getPath().length() - 9);
                if (isGrassyBlock) {
                    this.tintedTrapdoor(rb.get(), mcLoc("block/" + name + "_side"), mcLoc("block/dirt"), mcLoc("block/" + name + "_top"));
                } else {
                    this.tintedTrapdoor(rb.get(), mcLoc("block/" + name));
                }
                this.itemModels().withExistingParent(rb.getId().getPath(), modLoc("block/" + rb.getId().getPath() + "_bottom")); // Item model
            }
        });

        BlockInit.SSWT_SET_BEAMS.items().forEach((rb) -> {
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
        });
    }

    public void tintedStairs(StairBlock block, ResourceLocation texture) {
        this.tintedStairs(block, texture, texture, texture);
    }

    public void tintedStairs(StairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        ModelFile stairs = models().withExistingParent(block.getRegistryName().toString(), modLoc("block/stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile stairsInner = models().withExistingParent(block.getRegistryName().toString() + "_inner", modLoc("block/inner_stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile stairsOuter = models().withExistingParent(block.getRegistryName().toString() + "_outer", modLoc("block/outer_stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);

        this.stairsBlock(block, stairs, stairsInner, stairsOuter);
    }

    public void tintedSlab(SlabBlock block, ResourceLocation texture, ResourceLocation doubleSlab) {
        tintedSlab(block, texture, texture, texture, doubleSlab);
    }

    public void tintedSlab(SlabBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation doubleSlab) {
        this.slabBlock(block,
            models().withExistingParent(block.getRegistryName().getPath(), modLoc("block/slab"))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top),
            models().withExistingParent(block.getRegistryName().getPath() + "_top", modLoc("block/slab_top"))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top),
            models().getExistingFile(doubleSlab)
        );
    }

    public void tintedHalfWall(HalfWallBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.halfWallBlock(block, side, bottom, top);
        this.halfWallBlockItem(block, side, bottom, top);
    }

    public void tintedHalfWall(HalfWallBlock block, ResourceLocation texture) {
        this.halfWallBlock(block, texture);
        this.halfWallBlockItem(block, texture);
    }


    public void tintedTrapdoor(TrapDoorBlock block, ResourceLocation texture) {
        tintedTrapdoor(block, texture, texture, texture);
    }

    public void tintedTrapdoor(TrapDoorBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
        ModelFile openModel = models().withExistingParent(block.getRegistryName().toString() + "_open", modLoc("block/template_orientable_trapdoor_open"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile topModel = models().withExistingParent(block.getRegistryName().toString() + "top", modLoc("block/template_orientable_trapdoor_top"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile bottomModel = models().withExistingParent(block.getRegistryName().toString() + "_bottom", modLoc("block/template_orientable_trapdoor_bottom"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        this.trapdoorBlock(block, bottomModel, topModel, openModel, true);
    }

    public void halfWallBlockItem(HalfWallBlock block, ResourceLocation texture) {
        halfWallBlockItem(block, texture, texture, texture);
    }

    public void halfWallBlockItem(HalfWallBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.models().withExistingParent(block.getRegistryName().getPath() + "_inventory", modLoc("block/wall_inventory"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public void halfWallBlock(HalfWallBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        halfWallBlockInternal(block, block.getRegistryName().toString(), side, bottom, top);
    }

    public void halfWallBlock(HalfWallBlock block, ResourceLocation texture) {
        halfWallBlockInternal(block, block.getRegistryName().toString(), texture, texture, texture);
    }

    private void halfWallBlockInternal(HalfWallBlock block, String baseName, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        halfWallBlock(block,
            wallPost(baseName + "_post", side, bottom, top), wallSide(baseName + "_side", side, bottom, top), wallSideTall(baseName + "_side_tall", side, bottom, top),
            halfWallPost(baseName + "_half_post", side, bottom, top), halfWallSide(baseName + "_half_side", side, bottom, top)
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

    public BlockModelBuilder wallPost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder wallSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder wallSideTall(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_side_tall"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder halfWallPost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_wall_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder halfWallSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_wall_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }


    public void horizontalCarpet(CarpetBlock block, ResourceLocation wool) {
        ModelFile carpetModel = models().carpet(block.getRegistryName().toString(), wool);
        this.horizontalBlock(block, carpetModel);
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

    public void twoWayBlock(TwoWayBlock block, DyeColor color) {
        ModelFile single = models().singleTexture(block.getRegistryName().getPath() + "_single", mcLoc("block/chain"), "all", modLoc("block/" + block.getRegistryName().getPath().substring(0, block.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_single_" + color.getName()));
        ModelFile edge = models().singleTexture(block.getRegistryName().getPath() + "_edge", mcLoc("block/chain"), "all", modLoc("block/" + block.getRegistryName().getPath().substring(0, block.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_edge_" + color.getName()));
        ModelFile middle = models().singleTexture(block.getRegistryName().getPath() + "_middle", mcLoc("block/chain"), "all", modLoc("block/" + block.getRegistryName().getPath().substring(0, block.getRegistryName().getPath().length() - (color.getName().length() + 1)) + "_middle_" + color.getName()));

        this.itemModels().singleTexture(block.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", new ResourceLocation("swdm", "item/" + block.getRegistryName().getPath()));

        this.getVariantBuilder(block).forAllStates((state) -> {
            ModelFile fileToUse = state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.SINGLE
                ? single
                : state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.MIDDLE
                ? middle
                : edge;

            return ConfiguredModel.builder().modelFile(fileToUse)
                .rotationX(state.getValue(TwoWayBlock.AXIS).isHorizontal() ? 90 : (state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.RIGHT ? 180 : 0))
                .rotationY((state.getValue(TwoWayBlock.AXIS) == Direction.Axis.X ? 90 : 0) + (state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.RIGHT && state.getValue(TwoWayBlock.AXIS) == Direction.Axis.X ? 180 : state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.LEFT && state.getValue(TwoWayBlock.AXIS) == Direction.Axis.Z ? 180 : 0))
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
