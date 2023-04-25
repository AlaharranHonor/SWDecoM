package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.EntitySetup;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Languages extends LanguageProvider {
    public Languages(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.swdmtab", "SWDM");
        this.add("container.deco_workshop", "Deco Workshop");
        this.add(ItemSetup.CHANGE_TOOL.get(), "Change Tool");
        this.add(BlockSetup.DECO_WORKSHOP.get(), "Deco Workshop");
        this.add(ItemSetup.INVISIBLE_ITEM_FRAME.get(), "Invisible Item Frame");
        this.add(ItemSetup.MIRROR_PAINTING.get(), "Mirror");

        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addLang(this);
            });
        }

        BlockSetup.MANUAL_BLOCKS.values().forEach(block -> {
            this.add(block.get(), this.sanitizedName(block.getId().getPath()));
        });
    }

    public String sanitizedName(String name) {
        String[] nameParts = name.split("_");

        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < nameParts.length; i++) {
            String part = nameParts[i];
            String firstLetter = part.substring(0, 1);

            finalString.append(firstLetter.toUpperCase()).append(part.substring(1)).append(i == nameParts.length - 1 ? "" : " ");
        }

        return finalString.toString();
    }
}
