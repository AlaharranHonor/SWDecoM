package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.SWDM;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodSetup {

    public static final WoodType WHITEWASH = WoodType.register(new WoodType(ModRef.res("whitewash").toString(), BlockSetType.OAK));
    //public static final WoodType BAMBOO = WoodType.register(new WoodType(ModRef.res("bamboo").toString(), BlockSetType.OAK));
    public static final WoodType THATCH = WoodType.register(new WoodType(ModRef.res("thatch").toString(), BlockSetType.OAK));

    public static void init() {}

    public static WoodType fromName(String name) {
        if (WHITEWASH.name().contains(name)) {
            return WHITEWASH;
        //} else if (BAMBOO.name().contains(name)) {
        //    return BAMBOO;
        } else if (THATCH.name().contains(name)) {
            return THATCH;
        }

        return WoodType.values()
            .filter(type -> type.name().contains(name))
            .findFirst()
            .orElse(null);
    }

    public static String strippedName(WoodType type) {
        if (!type.name().contains(":")) {
            return type.name();
        }

        return type.name().substring(type.name().indexOf(':') + 1);
    }


}
