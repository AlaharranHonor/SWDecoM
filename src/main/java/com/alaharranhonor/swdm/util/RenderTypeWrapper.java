package com.alaharranhonor.swdm.util;

import net.minecraft.client.renderer.RenderType;

@FunctionalInterface
public interface RenderTypeWrapper {

    RenderType get();

    static RenderTypeWrapper solid() {
        return RenderType::solid;
    }

    static RenderTypeWrapper cutout() {
        return RenderType::cutout;
    }

    static RenderTypeWrapper translucent() {
        return RenderType::translucent;
    }
}
