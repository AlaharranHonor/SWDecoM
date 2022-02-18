package com.alaharranhonor.swdm.util;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusSubscriber {

	@SubscribeEvent
	public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (event.getPlayer().level.isClientSide()) return;
		if (event.getItemStack().getItem() != Items.CLOCK) return;
		int dayTime = (int) event.getPlayer().level.getDayTime() % 24000;

		event.getPlayer().sendMessage(new StringTextComponent(TimeUtil.getRealLifeMessage(dayTime)), Util.NIL_UUID);
	}
}
