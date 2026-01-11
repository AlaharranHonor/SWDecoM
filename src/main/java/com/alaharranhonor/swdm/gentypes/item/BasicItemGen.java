package com.alaharranhonor.swdm.gentypes.item;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockLoot;
import com.alaharranhonor.swdm.datagen.BlockStateGen;
import com.alaharranhonor.swdm.datagen.BlockTagGen;
import com.alaharranhonor.swdm.datagen.EnUsLanguageGen;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public abstract class BasicItemGen<T extends Item> extends GenType<T> {

    public BasicItemGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister.Blocks blocks, DeferredRegister.Items items) {
        // Don't register blocks which are in vanilla
        if (BuiltInRegistries.ITEM.containsKey(ResourceLocation.fromNamespaceAndPath("minecraft", name + this.getSuffix()))) {
            return false;
        }
        this.registeredName = name;
        items.register(name + this.getSuffix(), this);
        return true;
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {}

    @Override
    public void addBlockTags(BlockTagGen gen) {}

    @Override
    public void addLootTables(BlockLoot gen) {}

    @Override
    public void addLang(EnUsLanguageGen gen) {
        gen.add(this.generated, gen.sanitizedName(itemKey(this.generated).getPath()));
    }
}
