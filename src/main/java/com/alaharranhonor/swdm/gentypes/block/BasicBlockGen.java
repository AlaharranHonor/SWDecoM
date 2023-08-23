package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.datagen.EnUsLanguageGen;
import com.alaharranhonor.swdm.datagen.LootTableGen;
import com.alaharranhonor.swdm.datagen.RecipeGen;
import com.alaharranhonor.swdm.gentypes.GenType;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BasicBlockGen<T extends Block> extends GenType<T> {

    public BasicBlockGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 1);
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        // Don't register blocks which are in vanilla
        if (ForgeRegistries.BLOCKS.containsKey(new ResourceLocation("minecraft", name + this.getSuffix()))) {
            return false;
        }
        this.registeredName = name;
        blocks.register(name + this.getSuffix(), this);
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties()));
        return true;
    }

    @Override
    public void addLang(EnUsLanguageGen gen) {
        gen.add(this.generated, gen.sanitizedName(blockKey(this.generated).getPath()));
    }

    @Override
    public void addLootTables(LootTableGen.BlockLoot gen) {
        gen.dropSelf(this.generated);
    }

    @Override
    public void setRenderType(RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(this.generated, renderType);
    }

    @Override
    public void registerBlockColors(BlockColors reg, BlockColor color) {
        reg.register(color, this.generated);
    }

    @Override
    public void registerItemColors(ItemColors reg, ItemColor color) {
        reg.register(color, this.generated);
    }
}
