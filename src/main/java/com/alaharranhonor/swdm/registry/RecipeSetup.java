package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.workshop.DecoRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSetup {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, SWDM.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SWDM.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DecoRecipe>> DECO_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("deco", () -> new SingleItemRecipe.Serializer<>(DecoRecipe::new));
    public static final RegistryObject<RecipeType<DecoRecipe>> DECO_RECIPE_TYPE = RECIPE_TYPES.register("deco", () -> new RecipeType<>() {});

    public static void init(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
        RECIPE_TYPES.register(modBus);
    }

}
