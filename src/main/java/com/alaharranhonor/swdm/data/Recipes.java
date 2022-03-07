package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
	public Recipes(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> p_200404_1_) {
		super.buildShapelessRecipes(p_200404_1_);

		// Get vanilla item by calling ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", "block_name"));

		for (int i = 0; i < DyeColor.values().length; i++) {
			DyeColor color = DyeColor.byId(i);
			stairs(p_200404_1_, BlockInit.WOOL_STAIRS.get(i).get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool")),"wool_stairs", "has_wool");
		}

	}


	private static void stairs(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider resultItem, IItemProvider craftingItem, String group, String unlockRecipe) {
		ShapedRecipeBuilder.shaped(resultItem, 4).define('#', craftingItem).pattern("#  ").pattern("## ").pattern("###").group(group).unlockedBy(unlockRecipe, has(craftingItem)).save(p_240480_0_);
	}
}
