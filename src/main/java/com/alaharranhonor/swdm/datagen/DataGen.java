package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();

        // Client Data
        gen.addProvider(includeClient, new EnUsLanguageGen(output, "en_us"));
        gen.addProvider(includeClient, new BlockStateGen(output, existingFileHelper));
        gen.addProvider(includeClient, new ItemModelGen(output, existingFileHelper));
        gen.addProvider(includeClient, new SpriteSourceGen(output, registries, existingFileHelper));

        // Server Data
        BlockTagGen blockTags = gen.addProvider(includeServer, new BlockTagGen(output, registries, existingFileHelper));
        gen.addProvider(includeServer, new ItemTagGen(output, registries, blockTags, existingFileHelper));
		gen.addProvider(includeServer, new RecipeGen(output, registries));
        gen.addProvider(includeServer, (DataProvider.Factory<LootTableProvider>) o -> new LootTableProvider(o, Set.of(), List.of(
            new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), registries));
    }
}
