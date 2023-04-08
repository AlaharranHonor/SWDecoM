package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.datagen.Recipes;
import com.alaharranhonor.swdm.registry.BlockSetup;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SidingGen extends BlockGen {

    public SidingGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        Block base = BlockSetup.get("siding_light_white");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), this.generated)
            .unlockedBy("has_block", gen.hasItem(base))
            .save(builder);
    }
}
