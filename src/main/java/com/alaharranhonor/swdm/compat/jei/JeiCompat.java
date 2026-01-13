package com.alaharranhonor.swdm.compat.jei;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.RecipeSetup;
import com.alaharranhonor.swdm.workshop.DecoWorkshopScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;

import java.util.Objects;

@JeiPlugin
public class JeiCompat implements IModPlugin {

    private DecoWorkshopRecipeCategory decoWorkshopCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        this.decoWorkshopCategory = new DecoWorkshopRecipeCategory(guiHelper);

        registration.addRecipeCategories(this.decoWorkshopCategory);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        registration.addRecipes(this.decoWorkshopCategory.getRecipeType(), recipeManager.getAllRecipesFor(RecipeSetup.DECO_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(BlockSetup.DECO_WORKSHOP,
            this.decoWorkshopCategory.getRecipeType()
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DecoWorkshopScreen.class, 5, 5, 50, 10, this.decoWorkshopCategory.getRecipeType());
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ModRef.res("jei_plugin");
    }
}
