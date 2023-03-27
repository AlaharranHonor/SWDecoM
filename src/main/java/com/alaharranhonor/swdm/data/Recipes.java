package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.util.ColourUtil;
import com.alaharranhonor.swdm.util.init.BlockInit;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipe) {
        super.buildCraftingRecipes(recipe);

        //this.sets(recipe);
        this.misc(recipe);
    }

    private void misc(Consumer<FinishedRecipe> recipe) {
        ShapedRecipeBuilder.shaped(BlockInit.CHANGE_TOOL.get()).define('N', Tags.Items.NUGGETS_IRON).define('S', Tags.Items.RODS_WOODEN).pattern("NN ").pattern(" SN").pattern(" S ").unlockedBy("has_iron_nugget", has(Tags.Items.NUGGETS_IRON));
    }

    /*
    private void sets(Consumer<FinishedRecipe> recipe) {
        for (DyeColor color : DyeColor.values()) {
            ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("color", "dark_prismarine").get(color.getId()).get()).requires(Blocks.DARK_PRISMARINE).requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).group("dark_prismarine").unlockedBy("has_dark_prismarine", has(Blocks.DARK_PRISMARINE)).save(recipe);
            ShapedRecipeBuilder.shaped(BlockInit.STONE_SET_BLOCKS.get("color", "bricks_glass").get(color.getId()).get(), 8).define('x', Blocks.BRICKS).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_stained_glass"))).pattern("yyy").pattern("yxy").pattern("yyy").group("bricks_glass").unlockedBy("has_bricks", has(Blocks.BRICKS)).save(recipe);

            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color", "roof_metal").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', Items.IRON_INGOT).pattern("yyy").pattern("yxy").pattern("yyy").group("metal_roof").unlockedBy("has_iron_ingots", has(Items.IRON_INGOT)).save(recipe);
            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color", "roof_tile").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', Items.CLAY_BALL).pattern("yyy").pattern("yxy").pattern("yyy").group("tile_roof").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(recipe);
            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color", "roof_shingle").get(color.getId()).get(), 16).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ItemTags.PLANKS).pattern("yyy").pattern("yxy").pattern("yyy").group("shingle_roof").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(recipe);
            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color", "wool_pastel").get(color.getId()).get(), 8).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool"))).pattern("yyy").pattern("yxy").pattern("yyy").group("wool_pastel").unlockedBy("has_pastel_wool", has(ItemTags.WOOL)).save(recipe);
            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("color", "wool_tinted").get(color.getId()).get(), 8).define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).define('y', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool"))).pattern("yyy").pattern("yxy").pattern("yyy").group("wool_tinted").unlockedBy("has_tinted_wool", has(ItemTags.WOOL)).save(recipe);

            ShapedRecipeBuilder.shaped(BlockInit.COATED_CHAINS.get(color.getId()).get(), 6).define('N', Items.IRON_NUGGET).define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye"))).pattern("N").pattern("D").pattern("N").group("coated_chain").unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET)).save(recipe);
        }

        for (int i = 0; i < SWDM.STONE_TYPES.size(); i++) {
            String type = SWDM.STONE_TYPES.get(i);
            int finalI = i;
            SWDM.STONE_SETS.get("standard").forEach((stone, block) -> {
                if (type.equals("mossy")) {
                    ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(finalI).get()).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(Blocks.STONE)).save(recipe);
                } else if (type.equals("cracked")) {
                    SingleItemRecipeBuilder.stonecutting(Ingredient.of(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))), BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(finalI).get());
                } else if (type.equals("more_mossy")) {
                    ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(finalI).get()).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone))).requires(Blocks.VINE).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", stone)))).save(recipe);
                    ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(finalI).get()).requires(BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(0).get()).requires(Blocks.VINE).group("mossy_variants").unlockedBy("has_stone", has(Blocks.STONE)).save(recipe, new ResourceLocation("swdm", BlockInit.STONE_SET_BLOCKS.get("standard", stone).get(finalI).get().getRegistryName().getPath() + "_by_swdm_block"));
                }
            });
        }

        for (int i = 0; i < SWDM.LMD_TYPES.size(); i++) {
            String lmd = SWDM.LMD_TYPES.get(i);
            Item dyeItem = lmd.equals("light") ? Items.WHITE_DYE : lmd.equals("medium") ? Items.GRAY_DYE : Items.BLACK_DYE;
            ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd", "brick").get(i).get()).requires(Blocks.BRICKS).requires(dyeItem).group(lmd + "_brick").unlockedBy("has_bricks", has(Blocks.BRICKS));
            ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd", "stone_brick").get(i).get()).requires(Blocks.STONE_BRICKS).requires(dyeItem).group(lmd + "_stone_brick").unlockedBy("has_stone_bricks", has(Blocks.STONE_BRICKS));

            ShapedRecipeBuilder.shaped(BlockInit.SSW_SET_BLOCKS.get("lmd", "screen").get(i).get(), 8).define('x', Items.STRING).define('y', dyeItem).pattern("yyy").pattern("yxy").pattern("yyy").group("screen").unlockedBy("has_string", has(Items.STRING)).save(recipe);
        }

        BlockInit.STONE_SET_SLABS.cellSet().forEach((cell) -> {
            cell.getValue().forEach(rb -> {
                Block craftingItem;
                if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                } else {
                    craftingItem = rb.get();
                }
                stoneSlab(recipe, rb.get(), craftingItem, cell.getColumnKey());
            });
        });

        BlockInit.STONE_SET_WALLS.cellSet().forEach((cell) -> {
            cell.getValue().forEach(rb -> {
                Block craftingItem;
                if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                } else {
                    craftingItem = rb.get();
                }
                stoneWall(recipe, rb.get(), craftingItem, cell.getColumnKey());
            });
        });

        BlockInit.STONE_SET_STAIRS.cellSet().forEach((cell) -> {
            cell.getValue().forEach(rb -> {
                Block craftingItem;
                if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                } else {
                    craftingItem = rb.get();
                }
                stoneStairs(recipe, rb.get(), craftingItem, cell.getColumnKey());
            });
        });


        BlockInit.STONE_SET_PRESSURE_PLATES.cellSet().forEach((cell) -> {
            cell.getValue().forEach(rb -> {
                Block craftingItem;
                if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !cell.getRowKey().equals("color_custom"))) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 15)));
                } else {
                    craftingItem = rb.get();
                }
                stonePressurePlate(recipe, rb.get(), craftingItem, cell.getColumnKey());
            });
        });

        BlockInit.STONE_SET_BUTTONS.cellSet().forEach((cell) -> {
            cell.getValue().forEach(rb -> {
                Block craftingItem;
                if (rb.get().getRegistryName().getPath().contains("concrete") || (rb.get().getRegistryName().getPath().contains("terracotta") && !cell.getRowKey().equals("custom_color"))) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                } else {
                    craftingItem = rb.get();
                }
                stoneButton(recipe, rb.get(), craftingItem, cell.getColumnKey());
            });
        });


        BlockInit.SSW_SET_SLABS.row("color").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem;
                if (entry.getKey().equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                } else {
                    craftingItem = rb.get();
                }
                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_SLABS.row("color").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem;
                if (entry.getKey().equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                } else {
                    craftingItem = rb.get();
                }
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_STAIRS.row("color").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem;
                if (entry.getKey().equals("wool") || rb.get().getRegistryName().getPath().contains("stained_glass")) {
                    craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                } else {
                    craftingItem = rb.get();
                }
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        Lists.newArrayList("wool_pastel", "wool_tinted").forEach(type -> {
            for (RegistryObject<CarpetBlock> rb : BlockInit.SSW_SET_CARPETS.row("color").get(type)) {
                carpet(recipe, rb.get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("swdm", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7))), type + "_carpet");
            }
        });

        BlockInit.SSW_SET_SLABS.row("wv").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                String blockName = rb.getId().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5);
                if (entry.getKey().contains("stripped_log")) {
                    blockName = "stripped_" + blockName.replaceAll("_stripped_log", "") + "_log";
                }
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", blockName));

                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_WALLS.row("wv").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_STAIRS.row("wv").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_SLABS.row("wv-whitewash").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_WALLS.row("wv-whitewash").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_STAIRS.row("wv-whitewash").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_SLABS.row("lmd").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });

        BlockInit.SSW_SET_WALLS.row("lmd").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<HalfWallBlock> rb = BlockInit.SSW_SET_WALLS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });

        BlockInit.SSW_SET_STAIRS.row("lmd").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<StairBlock> rb = BlockInit.SSW_SET_STAIRS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });


        BlockInit.SSW_SET_GLASS_PANES.row("lmd").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                glassPane(recipe, rb.get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("swdm", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 11))), entry.getKey() + "_glass_pane");
            });
        });

        for (String tone : SWDM.NATURAL_TONES) {
            ShapelessRecipeBuilder sand = ShapelessRecipeBuilder.shapeless(BlockInit.SAND_BLOCKS.get(tone).get()).requires(Blocks.SAND).group("sand").unlockedBy("has_sand", has(Blocks.SAND));
            for (DyeColor color : ColourUtil.NATURAL_TO_DYE.get(tone)) {
                sand.requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")));
            }
            sand.save(recipe);

            ShapedRecipeBuilder.shaped(BlockInit.SANDSTONE_BLOCKS.get(tone).get(), 4).define('#', BlockInit.SAND_BLOCKS.get(tone).get()).pattern("##").pattern("##").group("sand").unlockedBy("has_sand", has(Blocks.SAND)).save(recipe);

            // TODO add LMD
            ShapedRecipeBuilder fiberCarpet = ShapedRecipeBuilder.shaped(BlockInit.FIBER_CARPETS.get("medium", tone).get(), 8).group("fiber_carpets").unlockedBy("has_carpet", has(ItemTags.CARPETS));
            fiberCarpet.define('#', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", ColourUtil.NATURAL_TO_DYE.get(tone).get(0).getName() + "_carpet")));
            if (ColourUtil.NATURAL_TO_DYE.get(tone).size() > 1) {
                fiberCarpet.define('X', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", ColourUtil.NATURAL_TO_DYE.get(tone).get(1).getName() + "_dye")));
                fiberCarpet.pattern("###").pattern("#X#").pattern("###");
            } else {
                fiberCarpet.pattern("###").pattern("# #").pattern("###");
            }
            fiberCarpet.save(recipe);
        }

        for (RegistryObject<Block> rb : BlockInit.SSW_SET_BLOCKS.get("lmd-color", "siding")) {
            String[] parts = rb.get().getRegistryName().getPath().split("_");

            ShapedRecipeBuilder siding = ShapedRecipeBuilder.shaped(rb.get(), 8).define('P', ItemTags.WOODEN_SLABS).define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", parts[1] + "_dye"))).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS));
            if (parts[2].equals("light")) {
                siding.define('T', Items.WHITE_DYE);
                siding.pattern("PT").pattern("PD").pattern("P ");
                siding.save(recipe);

                ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(SWDM.MOD_ID, parts[0] + "_" + parts[1] + "_medium"))).requires(Items.WHITE_DYE).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS)).save(recipe, rb.get().getRegistryName().getPath() + "_from_medium");
            } else if (parts[2].equals("medium")) {
                siding.pattern("P ").pattern("PD").pattern("P ");
                siding.save(recipe);
            } else if (parts[2].equals("dark")) {
                siding.define('T', Items.BLACK_DYE);
                siding.pattern("PT").pattern("PD").pattern("P ");
                siding.save(recipe);

                ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(SWDM.MOD_ID, parts[0] + "_" + parts[1] + "_medium"))).requires(Items.BLACK_DYE).group("siding").unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS)).save(recipe, rb.get().getRegistryName().getPath() + "_from_medium");
            }
        }

        for (RegistryObject<Block> rb : BlockInit.STONE_SET_BLOCKS.get("color_custom", "terracotta")) {
            ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(rb.get(), 8).requires(Blocks.TERRACOTTA).group("terracotta").unlockedBy("has_terracotta", has(Blocks.TERRACOTTA));
            for (DyeColor color : ColourUtil.CUSTOM_COLORS_TO_DYE.get(rb.get().getRegistryName().getPath().split("_")[1])) {
                shapeless.requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")));
            }
            shapeless.save(recipe);
        }

        for (int i = 0; i < SWDM.LMD_TYPES.size(); i++) {
            String lmd = SWDM.LMD_TYPES.get(i);
            String dye = lmd.equals("light") ? "white" : lmd.equals("medium") ? "gray" : "black";
            ShapelessRecipeBuilder.shapeless(BlockInit.STONE_SET_BLOCKS.get("lmd-only", "stone").get(i).get(), 8).requires(Blocks.STONE).requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", dye + "_dye"))).group("stone").unlockedBy("has_stone", has(Blocks.STONE)).save(recipe);
        }

        BlockInit.SSW_SET_SLABS.row("lmd-color").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });

        BlockInit.SSW_SET_WALLS.row("lmd-color").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<HalfWallBlock> rb = BlockInit.SSW_SET_WALLS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });

        BlockInit.SSW_SET_STAIRS.row("lmd-color").entrySet().forEach((entry) -> {
            for (int i = 0; i < entry.getValue().size(); i++) {
                RegistryObject<StairBlock> rb = BlockInit.SSW_SET_STAIRS.get("lmd", entry.getKey()).get(i);
                Block craftingItem = BlockInit.SSW_SET_BLOCKS.get("lmd", entry.getKey()).get(i).get();
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            }
        });


        BlockInit.SSW_SET_SLABS.row("standalone").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                slab(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_WALLS.row("standalone").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));
                wall(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_STAIRS.row("standalone").entrySet().forEach((entry) -> {
            entry.getValue().forEach(rb -> {
                Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 7)));
                stairs(recipe, rb.get(), craftingItem, entry.getKey(), "has_planks");
            });
        });

        BlockInit.SSW_SET_SLABS.get("standalone").keySet().forEach((key) -> {
                for (int i = 0; i < BlockInit.SSW_SET_SLABS.get("standalone").get(key).size(); i++) {
                    RegistryObject<SlabBlock> rb = BlockInit.SSW_SET_SLABS.get("standalone").get(key).get(i);
                    Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

                    slab(recipe, rb.get(), craftingItem, key, "has_planks");

                }

                for (int i = 0; i < BlockInit.SSW_SET_WALLS.get("standalone").get(key).size(); i++) {
                    RegistryObject<HalfWallBlock> rb = BlockInit.SSW_SET_WALLS.get("standalone").get(key).get(i);
                    Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

                    wall(recipe, rb.get(), craftingItem, key, "has_planks");
                }


                for (int i = 0; i < BlockInit.SSW_SET_STAIRS.get("standalone").get(key).size(); i++) {
                    RegistryObject<StairBlock> rb = BlockInit.SSW_SET_STAIRS.get("standalone").get(key).get(i);
                    Block craftingItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", rb.get().getRegistryName().getPath().substring(0, rb.get().getRegistryName().getPath().length() - 5)));

                    stairs(recipe, rb.get(), craftingItem, key, "has_planks");
                }
            });

    }
    */

    private static void stairs(Consumer<FinishedRecipe> p_240480_0_, ItemLike resultItem, ItemLike craftingItem, String group, String unlockedBy) {
        ShapedRecipeBuilder.shaped(resultItem, 4).define('#', craftingItem).pattern("#  ").pattern("## ").pattern("###").group(group + "_stairs").unlockedBy(unlockedBy, has(craftingItem)).save(p_240480_0_);
    }

    private static void stoneStairs(Consumer<FinishedRecipe> p_240480_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        stairs(p_240480_0_, resultItem, craftingItem, group, "has_stone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem).unlockedBy("has_stone", has(craftingItem)).save(p_240480_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
    }

    private static void slab(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group, String unlockedBy) {
        ShapedRecipeBuilder.shaped(resultItem, 6).define('#', craftingItem).pattern("###").group(group + "_slab").unlockedBy(group, has(craftingItem)).save(p_240479_0_);
        ShapedRecipeBuilder.shaped(craftingItem).define('#', resultItem).pattern("#").pattern("#").unlockedBy(unlockedBy, has(resultItem)).save(p_240479_0_, new ResourceLocation(SWDM.MOD_ID, craftingItem.asItem() + "_from_slabs")); // Two slabs to block
    }

    private static void stoneSlab(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        slab(p_240479_0_, resultItem, craftingItem, group, "has_stone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem, 2).unlockedBy("has_stone", has(craftingItem)).save(p_240479_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
    }

    private static void wall(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group, String unlockedBy) {
        ShapedRecipeBuilder.shaped(resultItem, 6).define('#', craftingItem).pattern("###").pattern("###").group(group + "_wall").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
    }

    private static void stoneWall(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        wall(p_240479_0_, resultItem, craftingItem, group, "has_stone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(craftingItem), resultItem).unlockedBy("has_stone", has(craftingItem)).save(p_240479_0_, resultItem.asItem().getRegistryName().getPath() + "_from_" + craftingItem.asItem().getRegistryName().getPath() + "_stonecutting");
    }

    private static void pressurePlate(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group, String unlockedBy) {
        ShapedRecipeBuilder.shaped(resultItem, 1).define('#', craftingItem).pattern("##").group(group + "_pressure_plate").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
    }

    private static void stonePressurePlate(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        pressurePlate(p_240479_0_, resultItem, craftingItem, group, "has_stone");
    }

    private static void button(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group, String unlockedBy) {
        ShapelessRecipeBuilder.shapeless(resultItem, 1).requires(craftingItem).group(group + "_button").unlockedBy(unlockedBy, has(craftingItem)).save(p_240479_0_);
    }

    private static void stoneButton(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        button(p_240479_0_, resultItem, craftingItem, group, "has_stone");
    }

    private static void carpet(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        ShapedRecipeBuilder.shaped(resultItem, 3).define('#', craftingItem).pattern("##").group(group).unlockedBy("has_something", has(craftingItem)).save(p_240479_0_);
    }

    private static void glassPane(Consumer<FinishedRecipe> p_240479_0_, ItemLike resultItem, ItemLike craftingItem, String group) {
        ShapelessRecipeBuilder.shapeless(resultItem, 16).requires(craftingItem).group(group).unlockedBy("has_something", has(craftingItem)).save(p_240479_0_);
    }
}
