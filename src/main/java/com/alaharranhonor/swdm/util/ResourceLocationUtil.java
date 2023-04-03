package com.alaharranhonor.swdm.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtil {

    public static ResourceLocation suffix(ResourceLocation base, String suffix) {
        return new ResourceLocation(base.getNamespace(), base.getPath() + suffix);
    }

    public static ResourceLocation prefix(ResourceLocation base, String prefix) {
        return new ResourceLocation(base.getNamespace(), prefix + base.getPath());
    }
}
