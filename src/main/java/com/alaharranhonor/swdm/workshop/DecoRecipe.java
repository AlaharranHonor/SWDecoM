package com.alaharranhonor.swdm.workshop;

import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.RecipeSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class DecoRecipe extends SingleItemRecipe {
    public DecoRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult) {
        super(RecipeSetup.DECO_RECIPE_TYPE.get(), RecipeSetup.DECO_RECIPE_SERIALIZER.get(), pId, pGroup, pIngredient, pResult);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(Container pInv, Level pLevel) {
        return this.ingredient.test(pInv.getItem(0));
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(BlockSetup.DECO_WORKSHOP.get());
    }
}
