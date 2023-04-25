package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagSetup {

    public static final TagKey<Item> STATE_CYCLER = ItemTags.create(SWDM.res("state_cycler"));

    public static void init() {}

}
