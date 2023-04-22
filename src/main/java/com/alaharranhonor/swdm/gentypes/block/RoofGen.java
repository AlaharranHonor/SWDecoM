package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.Recipes;
import com.alaharranhonor.swdm.registry.BlockSetup;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class RoofGen extends BlockGen {

    public RoofGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        Block base = BlockSetup.get("roof_" + roofType() + "_white");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), this.generated)
            .unlockedBy("has_block", gen.hasItem(base))
            .save(builder);
    }

    protected abstract String roofType();

    public static class Shingle extends RoofGen {

        public Shingle(GenSet set, Supplier<Block> baseBlock) {
            super(set, baseBlock);
        }

        @Override
        protected String roofType() {
            return "shingle";
        }
    }

    public static class Metal extends RoofGen {

        public Metal(GenSet set, Supplier<Block> baseBlock) {
            super(set, baseBlock);
        }

        @Override
        protected String roofType() {
            return "metal";
        }
    }

    public static class Tile extends RoofGen {

        public Tile(GenSet set, Supplier<Block> baseBlock) {
            super(set, baseBlock);
        }

        @Override
        protected String roofType() {
            return "tile";
        }
    }
}
