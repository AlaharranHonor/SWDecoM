package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodSetup {

    public static final WoodType WHITEWASH = WoodType.register(WoodType.create(SWDM.res("whitewash").toString()));
    public static final WoodType BAMBOO = WoodType.register(WoodType.create(SWDM.res("bamboo").toString()));
    public static final WoodType THATCH = WoodType.register(WoodType.create(SWDM.res("thatch").toString()));

    public static void init() {}

    public static WoodType fromName(String name) {
        if (WHITEWASH.name().contains(name)) {
            return WHITEWASH;
        } else if (BAMBOO.name().contains(name)) {
            return BAMBOO;
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
