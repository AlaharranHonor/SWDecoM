package com.alaharranhonor.swdm.workshop;

import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.RecipeSetup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class DecoRecipe extends SingleItemRecipe {
    public DecoRecipe(String group, Ingredient ingredient, ItemStack result) {
        super(RecipeSetup.DECO_RECIPE_TYPE.get(), RecipeSetup.DECO_RECIPE_SERIALIZER.get(), group, ingredient, result);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(BlockSetup.DECO_WORKSHOP.get());
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends SingleItemRecipe.Serializer<DecoRecipe> {

        public Serializer(Factory<DecoRecipe> factory) {
            super(factory);
        }
    }
}
