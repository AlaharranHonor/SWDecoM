package com.alaharranhonor.swdm.old.data;

import com.alaharranhonor.swdm.old.SWDM;
import com.alaharranhonor.swdm.old.util.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class Languages extends LanguageProvider {
    public Languages(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.swdmtab", "com.alaharranhonor.swdm.SWDM");
        //this.add("item.swdm.tatch", "Thatch");
        //this.add("item.swdm.acacia_stick", "Acacia Stick");
        //this.add("item.swdm.birch_stick", "Birch Stick");
        //this.add("item.swdm.dark_oak_stick", "Dark Oak Stick");
        //this.add("item.swdm.jungle_stick", "Jungle Stick");
        //this.add("item.swdm.oak_stick", "Oak Stick");
        //this.add("item.swdm.spruce_stick", "Spruce Stick");
        //this.add("block.swdm.whitewash_ladder", "Whitewash Ladder");
        //this.add("block.swdm.whitewash_plank_wall", "Whitewash Wall");
        //this.add("block.swdm.whitewash_log_wall", "Whitewash Log Wall");
        //this.add("block.swdm.whitewash_plank_beam", "Whitewash Beam");
        //this.add("block.swdm.whitewash_log_beam", "Whitewash Log Beam");
        //this.add("block.swdm.meter_point_red_sandstone", "Meter Point Red Sandstone");

        this.add(BlockInit.CHANGE_TOOL.get(), "Change Tool");

        for (String tone : SWDM.NATURAL_TONES) {
            String[] nameParts = tone.split("_");

            String finalString = "";
            for (int i = 0; i < nameParts.length; i++) {
                String part = nameParts[i];
                String firstLetter = part.substring(0, 1);

                finalString += firstLetter.toUpperCase() + part.substring(1) + (i == nameParts.length - 1 ? "" : " ");
            }

            this.add("block.swdm.meter_point_" + tone, "Meter Point " + finalString);
        }


        ForgeRegistries.BLOCKS.getValues().forEach((b) -> {
            if (b.getRegistryName().getNamespace().equals(SWDM.MOD_ID)) {
                String[] nameParts = b.getRegistryName().getPath().split("_");

                String finalString = "";
                for (int i = 0; i < nameParts.length; i++) {
                    String part = nameParts[i];
                    String firstLetter = part.substring(0, 1);

                    finalString += firstLetter.toUpperCase() + part.substring(1) + (i == nameParts.length - 1 ? "" : " ");
                }

                if (!b.getRegistryName().getPath().equals("thatch_wall_sign") && !b.getRegistryName().getPath().equals("bamboo_wall_sign")) {
                    this.add(b, finalString);
                }
            }
        });


    }
}
