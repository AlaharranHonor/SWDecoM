package com.alaharranhonor.swdm.events;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.block.ShelfBlock;
import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class ForgeEventBusSubscriber {

    @SubscribeEvent
    private static void cycleShelfBlock(PlayerInteractEvent.RightClickBlock event) {
        Player pPlayer = event.getEntity();
        InteractionHand pHand = event.getHand();
        if (!pPlayer.isShiftKeyDown() || !pPlayer.getItemInHand(pHand).is(TagSetup.STATE_CYCLER)) {
            return;
        }

        BlockState pState = event.getLevel().getBlockState(event.getPos());
        if (!(pState.getBlock() instanceof ShelfBlock)) return;

        int stateOrdinal = pState.getValue(ShelfBlock.SHELF_TYPE).ordinal();
        int height = stateOrdinal / 3;
        int pos = stateOrdinal % 3;
        height = (height + 1) % 3;

        stateOrdinal = height * 3 + pos;
        stateOrdinal = stateOrdinal % SWDMBlockstateProperties.ShelfType.values().length;

        event.getLevel().setBlock(event.getPos(), pState.setValue(ShelfBlock.SHELF_TYPE, SWDMBlockstateProperties.ShelfType.values()[stateOrdinal]), 3);
    }
}
