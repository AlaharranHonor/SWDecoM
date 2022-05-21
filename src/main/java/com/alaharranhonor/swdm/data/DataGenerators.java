package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator data = event.getGenerator();

		if (event.includeServer()) {
			data.addProvider(new Recipes(data));
			data.addProvider(new LootTables(data));
			data.addProvider(new BlockTags(data, SWDM.MOD_ID, event.getExistingFileHelper()));
		}

		if (event.includeClient()) {
			data.addProvider(new Languages(data, SWDM.MOD_ID, "en_us"));
			data.addProvider(new BlockStates(data, SWDM.MOD_ID, event.getExistingFileHelper()));
			data.addProvider(new Items(data, SWDM.MOD_ID, event.getExistingFileHelper()));
		}
	}
}
