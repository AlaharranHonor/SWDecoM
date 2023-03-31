package com.alaharranhonor.swdm;

import com.alaharranhonor.swdm.gentypes.GenType;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenSet {

    private final Supplier<Block> baseBlock;
    private final String baseName;
    private final List<List<String>> modifierSets;
    private final List<Function<Supplier<Block>, GenType<?>>> genTypeFactories;
    private final Function<Supplier<Block>, GenType<Block>> baseGenerator;

    // TODO Set Block/Item Tags

    public final List<GenType<?>> genTypes = new ArrayList<>();
    private Supplier<Block> generatedBaseBlock;

    private GenSet(Supplier<Block> baseBlock, String baseName, List<List<String>> modifierSets, List<Function<Supplier<Block>, GenType<?>>> genTypeFactories, Function<Supplier<Block>, GenType<Block>> baseGenerator) {
        this.baseBlock = baseBlock;
        this.generatedBaseBlock = baseBlock;
        this.baseName = baseName;
        this.modifierSets = modifierSets;
        this.genTypeFactories = genTypeFactories;
        this.baseGenerator = baseGenerator;
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
                genType.register(nameBuilder.toString(), blocks, items);
                this.genTypes.add(genType);
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

        private Builder(Supplier<Block> baseBlock, String baseName) {
            this.baseBlock = baseBlock;
            this.baseName = baseName;
        }

        private Builder(Supplier<Block> base) {
            this(base, base.get().getRegistryName().getPath());
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

        public GenSet build() {
            if (this.modifierSets.isEmpty()) {
                this.set("");
            }

            return new GenSet(this.baseBlock, this.baseName, this.modifierSets, this.genTypes, this.baseGenerator);
        }
    }
}