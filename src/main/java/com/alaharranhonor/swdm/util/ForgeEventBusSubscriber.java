package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusSubscriber {

	@SubscribeEvent
	public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (event.getPlayer().level.isClientSide()) return;
		if (event.getItemStack().getItem() != Items.CLOCK) return;
		int dayTime = (int) event.getPlayer().level.getDayTime() % 24000;

		event.getPlayer().sendMessage(new TextComponent(TimeUtil.getRealLifeMessage(dayTime)), Util.NIL_UUID);
	}

	@SubscribeEvent
	public static void onBlockMissingMappings(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equalsIgnoreCase("swdm")) {

				String missingMapping = mapping.key.getPath();
				if (missingMapping.contains("light_bricks") || missingMapping.contains("light_stone_bricks") || missingMapping.contains("medium_bricks") || missingMapping.contains("medium_stone_bricks") || missingMapping.contains("dark_bricks") || missingMapping.contains("dark_stone_bricks")) {
					mapping.remap(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("swdm", missingMapping.replaceAll("bricks", "brick"))));
				}

			}
		}
	}
}
