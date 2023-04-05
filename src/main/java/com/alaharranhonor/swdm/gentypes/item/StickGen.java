package com.alaharranhonor.swdm.gentypes.item;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StickGen extends BasicItemGen<Item> {

    public StickGen(Supplier<Block> baseBlock) {
        super(baseBlock);
    }

    @Override
    protected Item generate() {
        return new Item(new Item.Properties().tab(SWDM.TAB));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        ResourceLocation basePath = this.generated.getRegistryName();
        gen.withExistingParent(this.generated.getRegistryName().getPath(), "item/handheld").texture("layer0", textures.get("item", basePath));
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> recipe) {
        ShapedRecipeBuilder.shaped(this.generated, 4)
            .pattern("P")
            .pattern("P")
            .define('P', this.baseBlock.get())
            .unlockedBy("has_" + this.baseBlock.get().getRegistryName().getPath(), gen.hasItem(this.baseBlock.get()))
            .save(recipe);
    }

    @Override
    public void addItemTags(ItemTags gen) {
        gen.tag(Tags.Items.RODS_WOODEN).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_stick";
    }
}
