package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.registry.TagSetup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {
    public ItemTagGen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookup, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookup, pBlockTagsProvider.contentsGetter(), ModRef.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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

    // Set to public
    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> pTag) {
        return super.tag(pTag);
    }
}
