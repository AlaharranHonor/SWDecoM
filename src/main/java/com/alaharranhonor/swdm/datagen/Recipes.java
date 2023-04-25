package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.RecipeSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> builder) {
        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addRecipes(this, builder);
            });
        }

        ShapelessRecipeBuilder.shapeless(BlockSetup.get("roof_shingle_white"))
            .requires(Blocks.GRAVEL).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder, "roof_shingle_white_manual");

        ShapelessRecipeBuilder.shapeless(BlockSetup.get("roof_tile_white"))
            .requires(Blocks.CLAY).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder, "roof_tile_white_manual");

        ShapelessRecipeBuilder.shapeless(BlockSetup.get("roof_metal_white"))
            .requires(Tags.Items.NUGGETS_IRON).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder, "roof_metal_white_manual");

        ShapelessRecipeBuilder.shapeless(BlockSetup.get("siding_light_white"))
            .requires(Items.DRIED_KELP).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder, "siding_light_white_manual");

        ShapelessRecipeBuilder.shapeless(BlockSetup.WHITEWASH_PLANKS.get())
            .requires(Items.WHITE_DYE).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder, "whitewash_planks_manual");

        ShapelessRecipeBuilder.shapeless(BlockSetup.THATCH_BLOCK.get())
            .requires(Items.WHEAT).requires(ItemTags.PLANKS)
            .unlockedBy("has_planks", has(ItemTags.PLANKS))
            .save(builder);

        ShapedRecipeBuilder.shaped(BlockSetup.DECO_WORKSHOP.get())
            .pattern("SSS")
            .pattern("SCS")
            .pattern("SSS")
            .define('S', Blocks.STONE)
            .define('C', ItemSetup.CHANGE_TOOL.get())
            .unlockedBy("has_change_tool", has(ItemSetup.CHANGE_TOOL.get()))
            .save(builder);

        ShapedRecipeBuilder.shaped(ItemSetup.CHANGE_TOOL.get())
            .pattern(" NR")
            .pattern(" SN")
            .pattern("S  ")
            .define('S', Tags.Items.RODS_WOODEN)
            .define('N', Tags.Items.NUGGETS_IRON)
            .define('R', Tags.Items.DUSTS_REDSTONE)
            .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
            .save(builder);

        this.defaultDecoBench(builder, BlockSetup.THATCH_PLANKS.get(), BlockSetup.THATCH_BLOCK.get());
        this.defaultDecoBench(builder, BlockSetup.THATCH_LOG.get(), BlockSetup.THATCH_BLOCK.get());
        this.defaultDecoBench(builder, BlockSetup.THATCH_STRIPPED_LOG.get(), BlockSetup.THATCH_BLOCK.get());
        this.defaultDecoBench(builder, BlockSetup.BAMBOO_PLANKS.get(), Items.BAMBOO);
        this.defaultDecoBench(builder, BlockSetup.BAMBOO_LOG.get(), Items.BAMBOO);
        this.defaultDecoBench(builder, BlockSetup.BAMBOO_STRIPPED_LOG.get(), Items.BAMBOO);
        this.defaultDecoBench(builder, BlockSetup.WHITEWASH_LOG.get(), BlockSetup.WHITEWASH_PLANKS.get());
        this.defaultDecoBench(builder, BlockSetup.WHITEWASH_STRIPPED_LOG.get(), BlockSetup.WHITEWASH_PLANKS.get());
        this.defaultDecoBench(builder, BlockSetup.SMOOTH_STONE_BORDERLESS.get(), Blocks.SMOOTH_STONE);
        this.defaultDecoBench(builder, ItemSetup.INVISIBLE_ITEM_FRAME.get(), Items.ITEM_FRAME, 4);
        this.defaultDecoBench(builder, ItemSetup.MIRROR_PAINTING.get(), Items.PAINTING);
    }

    public void defaultDecoBench(Consumer<FinishedRecipe> builder, ItemLike output, ItemLike input, int amount) {
        new SingleItemRecipeBuilder(RecipeSetup.DECO_RECIPE_SERIALIZER.get(), Ingredient.of(input), output, amount)
            .unlockedBy("has_block", has(input))
            .save(builder);
    }

    public void defaultDecoBench(Consumer<FinishedRecipe> builder, ItemLike output, TagKey<Item> input, int amount) {
        new SingleItemRecipeBuilder(RecipeSetup.DECO_RECIPE_SERIALIZER.get(), Ingredient.of(input), output, amount)
            .unlockedBy("has_block", has(input))
            .save(builder);
    }

    public void defaultDecoBench(Consumer<FinishedRecipe> builder, ItemLike output, ItemLike input) {
        this.defaultDecoBench(builder, output, input, 1);
    }

    public void defaultDecoBench(Consumer<FinishedRecipe> builder, ItemLike output, TagKey<Item> input) {
        this.defaultDecoBench(builder, output, input, 1);
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item) {
        return has(item);
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(TagKey<Item> tag) {
        return has(tag);
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(MinMaxBounds.Ints range, ItemLike item) {
        return has(range, item);
    }
}
