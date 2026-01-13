package com.alaharranhonor.swdm.compat.jei;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.workshop.DecoRecipe;
import com.mojang.serialization.Codec;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class DecoWorkshopRecipeCategory extends AbstractRecipeCategory<RecipeHolder<DecoRecipe>> {
    public static final Component TITLE = Component.translatable("container.deco_workshop");
    public static final RecipeType<RecipeHolder<DecoRecipe>> TYPE = RecipeType.createRecipeHolderType(ModRef.res("deco"));

    public static final int WIDTH = 82;
    public static final int HEIGHT = 34;

    public DecoWorkshopRecipeCategory(IGuiHelper guiHelper) {
        super(
            TYPE,
            TITLE,
            guiHelper.createDrawableItemLike(BlockSetup.DECO_WORKSHOP),
            WIDTH,
            HEIGHT
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DecoRecipe> recipeHolder, IFocusGroup focuses) {
        DecoRecipe recipe = recipeHolder.value();

        builder.addInputSlot(1, 9)
            .setStandardSlotBackground()
            .addIngredients(recipe.getIngredients().getFirst());

        builder.addOutputSlot(61,  9)
            .setOutputSlotBackground()
            .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<DecoRecipe> recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(26, 9);
    }

    /*@Override
    public boolean isHandled(RecipeHolder<DecoRecipe> recipeHolder) {
        DecoRecipe recipe = recipeHolder.value();
        return !recipe.isSpecial();
    }*/

    @Override
    public ResourceLocation getRegistryName(RecipeHolder<DecoRecipe> recipe) {
        return recipe.id();
    }

    @Override
    public Codec<RecipeHolder<DecoRecipe>> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return codecHelper.getRecipeHolderCodec();
    }
}
