package com.alaharranhonor.swdm;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRef {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ID = "swdm";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }

}
