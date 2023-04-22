package com.alaharranhonor.swdm.gentypes.item;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.Languages;
import com.alaharranhonor.swdm.datagen.LootTables;
import com.alaharranhonor.swdm.gentypes.GenType;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class BasicItemGen<T extends Item> extends GenType<T> {

    public BasicItemGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        // Don't register blocks which are in vanilla
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation("minecraft", name + this.getSuffix()))) {
            return false;
        }
        this.registeredName = name;
        items.register(name + this.getSuffix(), this);
        return true;
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {}

    @Override
    public void addBlockTags(BlockTags gen) {}

    @Override
    public void addLootTables(LootTables.ModLootTables gen) {}

    @Override
    public void addLang(Languages gen) {
        gen.add(this.generated, gen.sanitizedName(this.generated.getRegistryName().getPath()));
    }
}
