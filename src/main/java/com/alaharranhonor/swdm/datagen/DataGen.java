package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.ModRef;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean isClient = event.includeClient();
        boolean isServer = event.includeServer();

        // Client Data
        data.addProvider(isClient, new EnUsLanguageGen(output, "en_us"));
        data.addProvider(isClient, new BlockStateGen(output, event.getExistingFileHelper()));
        data.addProvider(isClient, new ItemModelGen(output, event.getExistingFileHelper()));
        data.addProvider(isClient, new SpriteSourceGen(output, event.getExistingFileHelper()));

        // Server Data
        BlockTagGen blockTags = data.addProvider(isServer, new BlockTagGen(output, lookup, helper));
        data.addProvider(isServer, new ItemTagGen(output, lookup, blockTags, event.getExistingFileHelper()));
		data.addProvider(isServer, new RecipeGen(output));
		data.addProvider(isServer, new LootTableGen(
            output,
            Collections.emptySet(),
            List.of(new LootTableProvider.SubProviderEntry(LootTableGen.BlockLoot::new, LootContextParamSets.BLOCK))));
    }
}
