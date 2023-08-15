package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.TagSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(TagSetup.STATE_CYCLER).add(ItemSetup.CHANGE_TOOL.get());

        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addItemTags(this);
                set.getItemTags().forEach(tag -> {
                    genType.subTypes()
                        .filter(sub -> sub instanceof Item)
                        .map(Item.class::cast)
                        .forEach(subItems -> this.tag(tag).add(subItems));
                });
            });
        }
    }

    @Override
    public TagAppender<Item> tag(TagKey<Item> pTag) {
        return super.tag(pTag);
    }
}
