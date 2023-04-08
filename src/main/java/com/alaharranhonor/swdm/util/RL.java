package com.alaharranhonor.swdm.util;

import net.minecraft.resources.ResourceLocation;

public class RL {

    public static ResourceLocation suffix(ResourceLocation base, String suffix) {
        return new ResourceLocation(base.getNamespace(), base.getPath() + suffix);
    }

    public static ResourceLocation prefix(ResourceLocation base, String prefix) {
        return new ResourceLocation(base.getNamespace(), prefix + base.getPath());
    }

    public static ResourceLocation withNamespace(ResourceLocation base, String namespace) {
        return new ResourceLocation(namespace, base.getPath());
    }

    public static ResourceLocation withPath(ResourceLocation base, String path) {
        return new ResourceLocation(base.getNamespace(), path);
    }
}
