package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.util.RenderTypeWrapper;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenSet {

    private final Supplier<Block> baseBlock;
    private final String baseName;
    private final List<List<String>> modifierSets;
    private final List<Function<Supplier<Block>, GenType<?>>> genTypeFactories;
    private final Function<Supplier<Block>, GenType<Block>> baseGenerator;
    private final TextureSet blockTextures;
    private final TextureSet itemTextures;
    private final Supplier<RenderTypeWrapper> renderType;
    private final BlockColor blockColors;
    private final ItemColor itemColors;
    private final List<TagKey<Block>> blockTags;
    private final List<TagKey<Item>> itemTags;

    public final List<GenType<?>> genTypes = new ArrayList<>();
    private Supplier<Block> generatedBaseBlock;

    private GenSet(Supplier<Block> baseBlock,
                   String baseName,
                   List<List<String>> modifierSets,
                   List<Function<Supplier<Block>, GenType<?>>> genTypeFactories,
                   Function<Supplier<Block>, GenType<Block>> baseGenerator,
                   TextureSet blockTextures,
                   TextureSet itemTextures,
                   Supplier<RenderTypeWrapper> renderType,
                   BlockColor blockColors,
                   ItemColor itemColors,
                   List<TagKey<Block>> blockTags,
                   List<TagKey<Item>> itemTags
    ) {
        this.baseBlock = baseBlock;
        this.generatedBaseBlock = baseBlock;
        this.baseName = baseName;
        this.modifierSets = modifierSets;
        this.genTypeFactories = genTypeFactories;
        this.baseGenerator = baseGenerator;
        this.blockTextures = blockTextures;
        this.itemTextures = itemTextures;
        this.renderType = renderType;
        this.blockColors = blockColors;
        this.itemColors = itemColors;
        this.blockTags = blockTags;
        this.itemTags = itemTags;
    }

    public void register(DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        int[] indices = new int[this.modifierSets.size()];
        Arrays.fill(indices, 0);
        List<List<String>> sets = this.modifierSets;
        boolean completed = false;
        while (!completed) {
            StringBuilder nameBuilder = new StringBuilder(this.baseName);
            for (int i = 0; i < sets.size(); i++) {
                List<String> modifierSet = sets.get(i);
                String modifier = modifierSet.get(indices[i]);
                if (!StringUtil.isNullOrEmpty(modifier)) {
                    nameBuilder.append('_').append(modifier);
                }
            }

            if (this.baseGenerator != null) {
                GenType<Block> baseGen = this.baseGenerator.apply(this.baseBlock);
                baseGen.register(nameBuilder.toString(), blocks, items);
                this.genTypes.add(baseGen);
                this.generatedBaseBlock = baseGen;
            }
            for (Function<Supplier<Block>, GenType<?>> factory : this.genTypeFactories) {
                GenType<?> genType = factory.apply(this.generatedBaseBlock);
                if (genType.register(nameBuilder.toString(), blocks, items)) {
                    this.genTypes.add(genType);
                }
            }

            int setId = sets.size() - 1;
            indices[setId] = indices[setId] + 1;
            while (indices[setId] == sets.get(setId).size()) {
                indices[setId] = 0;
                setId--;
                if (setId < 0) {
                    completed = true;
                    break;
                }
                indices[setId] = indices[setId] + 1;
            }
        }
    }

    public TextureSet getBlockTextures() {
        return this.blockTextures;
    }

    public TextureSet getItemTextures() {
        return this.itemTextures;
    }

    public RenderTypeWrapper getRenderType() {
        return this.renderType.get();
    }

    public BlockColor getBlockColors() {
        return this.blockColors;
    }

    public ItemColor getItemColors() {
        return this.itemColors;
    }

    public List<TagKey<Block>> getBlockTags() {
        return this.blockTags;
    }

    public List<TagKey<Item>> getItemTags() {
        return this.itemTags;
    }

    public static Builder builder(RegistryObject<Block> base) {
        return new Builder(base);
    }

    public static Builder builder(Supplier<Block> base) {
        return new Builder(base);
    }

    public static Builder builder(Supplier<Block> baseBlock, String baseName) {
        return new Builder(baseBlock, baseName);
    }

    public static class Builder {

        private final Supplier<Block> baseBlock;
        private final String baseName;
        private final List<List<String>> modifierSets = new ArrayList<>();
        private final List<Function<Supplier<Block>, GenType<?>>> genTypes = new ArrayList<>();
        private Function<Supplier<Block>, GenType<Block>> baseGenerator;
        private final TextureSet.Builder blockTextures = TextureSet.builder(TextureSet.DEFAULT_BLOCK_TEXTURE_SET);
        private final TextureSet.Builder itemTextures = TextureSet.builder(TextureSet.DEFAULT_ITEM_TEXTURE_SET);
        private Supplier<RenderTypeWrapper> renderType = RenderTypeWrapper::solid;
        private BlockColor blockColor;
        private ItemColor itemColor;
        private List<TagKey<Block>> blockTags = new ArrayList<>();
        private List<TagKey<Item>> itemTags = new ArrayList<>();

        private Builder(Supplier<Block> baseBlock, String baseName) {
            this.baseBlock = baseBlock;
            this.baseName = baseName;
        }

        private Builder(Supplier<Block> base) {
            this(base, base.get().getRegistryName().getPath());
        }

        private Builder(RegistryObject<Block> base) {
            this(base, base.getId().getPath());
        }

        public Builder withBase(Function<Supplier<Block>, GenType<Block>> type) {
            this.baseGenerator = type;
            return this;
        }

        public Builder set(String... set) {
            this.modifierSets.add(Arrays.asList(set));
            return this;
        }

        public Builder set(List<String> set) {
            this.modifierSets.add(List.copyOf(set));
            return this;
        }

        @SafeVarargs
        public final Builder sets(List<String>... sets) {
            this.modifierSets.addAll(List.of(sets));
            return this;
        }

        @SafeVarargs
        public final Builder types(Function<Supplier<Block>, GenType<?>>... types) {
            this.genTypes.addAll(List.of(types));
            return this;
        }

        public final Builder types(List<Function<Supplier<Block>, GenType<?>>> types) {
            this.genTypes.addAll(types);
            return this;
        }

        @SafeVarargs
        public final Builder blockTags(TagKey<Block>... tags) {
            this.blockTags.addAll(List.of(tags));
            return this;
        }

        @SafeVarargs
        public final Builder itemTags(TagKey<Item>... tags) {
            this.itemTags.addAll(List.of(tags));
            return this;
        }

        public final Builder blockTextures(Consumer<TextureSet.Builder> textures) {
            textures.accept(this.blockTextures);
            return this;
        }

        public final Builder itemTextures(Consumer<TextureSet.Builder> textures) {
            textures.accept(this.itemTextures);
            return this;
        }

        public final Builder renderType(Supplier<RenderTypeWrapper> renderType) {
            this.renderType = renderType;
            return this;
        }

        public final Builder blockColors(BlockColor color) {
            this.blockColor = color;
            return this;
        }

        public final Builder itemColors(ItemColor color) {
            this.itemColor = color;
            return this;
        }

        public GenSet build() {
            if (this.modifierSets.isEmpty()) {
                this.set("");
            }

            return new GenSet(
                this.baseBlock,
                this.baseName,
                this.modifierSets,
                this.genTypes,
                this.baseGenerator,
                this.blockTextures.build(),
                this.itemTextures.build(),
                this.renderType,
                this.blockColor,
                this.itemColor,
                this.blockTags,
                this.itemTags
            );
        }
    }
}