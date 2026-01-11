package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.workshop.DecoRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeSetup {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ModRef.ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ModRef.ID);

    public static final DeferredHolder<RecipeSerializer<?>, DecoRecipe.Serializer> DECO_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("deco", () -> new DecoRecipe.Serializer(DecoRecipe::new));
    public static final DeferredHolder<RecipeType<?>, RecipeType<DecoRecipe>> DECO_RECIPE_TYPE = RECIPE_TYPES.register("deco", () -> new RecipeType<>() {});

    public static void init(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
        RECIPE_TYPES.register(modBus);
    }

}
