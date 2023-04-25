package com.alaharranhonor.swdm.util;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TextureSet {

    public static final TextureSet DEFAULT_BLOCK_TEXTURE_SET = TextureSet.builder(TextureSet.Builder::block)
        .with("item", TextureSet.Builder::item)
        .build();
    
    public static final TextureSet DEFAULT_ITEM_TEXTURE_SET = TextureSet.builder(TextureSet.Builder::item).build();

    private final Map<String, Function<ResourceLocation, ResourceLocation>> textures;

    public ResourceLocation get(String path, ResourceLocation base) {
        if (textures.containsKey(path)) {
            return this.textures.get(path).apply(base);
        }

        return this.textures.get("").apply(base);
    }

    private TextureSet(Map<String, Function<ResourceLocation, ResourceLocation>> textures) {
        this.textures = textures;
    }

    public static Builder builder(Function<ResourceLocation, ResourceLocation> def) {
        return new Builder(def);
    }

    public static Builder builder(TextureSet copy) {
        return new Builder(new HashMap<>(copy.textures));
    }

    public static class Builder {

        private final Map<String, Function<ResourceLocation, ResourceLocation>> textures;

        public Builder(Function<ResourceLocation, ResourceLocation> def) {
            this.textures = new HashMap<>();
            this.with("", def);
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
            return RL.prefix(res, "block/");
        }

        public static ResourceLocation entity(ResourceLocation res) {
            return RL.prefix(res, "entity/");
        }

        public static ResourceLocation item(ResourceLocation res) {
            return RL.prefix(res, "item/");
        }

    }
}
