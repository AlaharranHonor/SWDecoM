package com.alaharranhonor.swdm.gentypes;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.datagen.Languages;
import com.alaharranhonor.swdm.datagen.LootTables;
import com.alaharranhonor.swdm.datagen.Recipes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BasicBlockGen<T extends Block> extends GenType<T> {

    public BasicBlockGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> recipe) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(this.baseBlock.get()), this.generated)
            .unlockedBy("has_block", gen.hasItem(this.baseBlock.get()))
            .save(recipe);
    }

    @Override
    public void register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        this.registeredName = name;
        blocks.register(name + this.getSuffix(), this);
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties().tab(SWDM.TAB)));
    }

    @Override
    public void addLang(Languages gen) {
        gen.add(this.generated, gen.sanitizedName(this.generated.getRegistryName().getPath()));
    }

    @Override
    public void addLootTables(LootTables.ModLootTables gen) {
        gen.dropSelf(this.generated);
    }
}
