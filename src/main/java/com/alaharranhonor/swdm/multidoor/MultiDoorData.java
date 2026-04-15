package com.alaharranhonor.swdm.multidoor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.List;

public record MultiDoorData(int width, int height, List<MultiDoorTexture> textures) implements TooltipComponent {

    public static MultiDoorData fromTextures(int width, int height, List<ResourceLocation> textures) {
        return new MultiDoorData(width, height, textures.stream().map(MultiDoorTexture::new).toList());
    }

    public static final Codec<MultiDoorData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("width").forGetter(MultiDoorData::width),
        Codec.INT.fieldOf("height").forGetter(MultiDoorData::height),
        MultiDoorTexture.CODEC.listOf().fieldOf("textures").forGetter(MultiDoorData::textures)
    ).apply(instance, MultiDoorData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MultiDoorData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, MultiDoorData::width,
        ByteBufCodecs.INT, MultiDoorData::height,
        MultiDoorTexture.STREAM_CODEC.apply(ByteBufCodecs.list()), MultiDoorData::textures,
        MultiDoorData::new
    );

    /**
     * Gets the texture for a specific cell in the door grid.
     * Cells are indexed column-first: index = w * height + h
     */
    public MultiDoorTexture getTexture(int x, int y) {
        int index = y * this.width + x;
        if (index >= 0 && index < this.textures.size()) {
            return this.textures.get(index);
        }
        // Fallback if out of bounds
        return this.textures.isEmpty()
            ? MultiDoorTexture.EMPTY
            : this.textures.getFirst();
    }

    public record MultiDoorTexture(ResourceLocation texture, List<Float> uvs) {
        public static final List<Float> DEFAULT_UVS = List.of(0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f);
        public static final MultiDoorTexture EMPTY = new MultiDoorTexture(ResourceLocation.withDefaultNamespace("missingno"));

        public MultiDoorTexture(ResourceLocation texture) {
            this(texture, DEFAULT_UVS);
        }

        public static final Codec<MultiDoorTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(MultiDoorTexture::texture),
            Codec.FLOAT.listOf().fieldOf("uvs").forGetter(MultiDoorTexture::uvs)
        ).apply(instance, MultiDoorTexture::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MultiDoorTexture> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, MultiDoorTexture::texture,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs.list(8)), MultiDoorTexture::uvs,
            MultiDoorTexture::new
        );


    }
}
