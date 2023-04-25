package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.block.ShelfBlock;
import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SWDM.MOD_ID)
public class ForgeEventBusSubscriber {

    @SubscribeEvent
    public static void cycleShelfBlock(PlayerInteractEvent.RightClickBlock event) {
        Player pPlayer = event.getPlayer();
        InteractionHand pHand = event.getHand();
        if (!pPlayer.isShiftKeyDown() || !pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return;
        }

        BlockState pState = event.getWorld().getBlockState(event.getPos());
        int stateOrdinal = pState.getValue(ShelfBlock.SHELF_TYPE).ordinal();
        int height = stateOrdinal / 3;
        int pos = stateOrdinal % 3;
        height = (height + 1) % 3;

        stateOrdinal = height * 3 + pos;
        stateOrdinal = stateOrdinal % SWDMBlockstateProperties.ShelfType.values().length;

        event.getWorld().setBlock(event.getPos(), pState.setValue(ShelfBlock.SHELF_TYPE, SWDMBlockstateProperties.ShelfType.values()[stateOrdinal]), 3);
    }
}
