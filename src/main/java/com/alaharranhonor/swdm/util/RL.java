package com.alaharranhonor.swdm.util;

import net.minecraft.resources.ResourceLocation;

public class RL {

    public static ResourceLocation suffix(ResourceLocation base, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(base.getNamespace(), base.getPath() + suffix);
    }

    public static ResourceLocation prefix(ResourceLocation base, String prefix) {
        return ResourceLocation.fromNamespaceAndPath(base.getNamespace(), prefix + base.getPath());
    }

    public static ResourceLocation withNamespace(ResourceLocation base, String namespace) {
        return ResourceLocation.fromNamespaceAndPath(namespace, base.getPath());
    }

    public static ResourceLocation withPath(ResourceLocation base, String path) {
        return ResourceLocation.fromNamespaceAndPath(base.getNamespace(), path);
    }
}
