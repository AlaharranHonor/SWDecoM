package com.alaharranhonor.swdm.util;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TextureSet {

    public static final TextureSet DEFAULT_TEXTURE_SET = TextureSet.builder()
        .with("side", TextureSet.Builder::block)
        .with("top", TextureSet.Builder::block)
        .with("bottom", TextureSet.Builder::block)
        .with("front", TextureSet.Builder::block)
        .with("back", TextureSet.Builder::block)
        .build();

    private final Map<String, Function<ResourceLocation, ResourceLocation>> textures;

    public ResourceLocation get(String path, ResourceLocation base) {
        return this.textures.get(path).apply(base);
    }

    private TextureSet(Map<String, Function<ResourceLocation, ResourceLocation>> textures) {
        this.textures = textures;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(TextureSet copy) {
        return new Builder(new HashMap<>(copy.textures));
    }

    public static class Builder {

        private final Map<String, Function<ResourceLocation, ResourceLocation>> textures;

        public Builder() {
            this(new HashMap<>());
        }

        public Builder(Map<String, Function<ResourceLocation, ResourceLocation>> textures) {
            this.textures = textures;
        }

        public Builder with(String path, Function<ResourceLocation, ResourceLocation> texture) {
            this.textures.put(path, texture);
            return this;
        }

        public TextureSet build() {
            return new TextureSet(this.textures);
        }

        public static ResourceLocation block(ResourceLocation res) {
            return ResourceLocationUtil.prefix(res, "block/");
        }

    }
}
