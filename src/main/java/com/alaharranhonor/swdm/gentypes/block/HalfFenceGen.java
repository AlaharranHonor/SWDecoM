package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.HalfFenceBlock;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HalfFenceGen extends BasicBlockGen<HalfFenceBlock> {

    protected RegistryObject<HalfFenceBlock> lowerBlock;
    protected RegistryObject<HalfFenceBlock> upperBlock;
    protected RegistryObject<HalfFenceBlock> lowerBlockWaterlogged;
    protected RegistryObject<HalfFenceBlock> upperBlockWaterlogged;
    protected RegistryObject<HalfFenceBlock> blockWaterlogged;
    public HalfFenceGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        // Don't register blocks which are in vanilla
        if (ForgeRegistries.BLOCKS.containsKey(new ResourceLocation("minecraft", name + this.getSuffix()))) {
            return false;
        }

        this.registeredName = name;
        blocks.register(name + this.getSuffix(), this);
        this.lowerBlock = blocks.register(name + this.getSuffix() + "_lower", () -> this.generateFence(false, this.upperBlock, this.lowerBlockWaterlogged));
        this.upperBlock = blocks.register(name + this.getSuffix() + "_upper", () -> this.generateFence(false, this, this.upperBlockWaterlogged));

        this.blockWaterlogged = blocks.register(name + this.getSuffix() + "_waterlogged", () -> this.generateFence(true, this.lowerBlockWaterlogged, this));
        this.lowerBlockWaterlogged = blocks.register(name + this.getSuffix() + "_lower_waterlogged", () -> this.generateFence(true, this.upperBlockWaterlogged, this.lowerBlock));
        this.upperBlockWaterlogged = blocks.register(name + this.getSuffix() + "_upper_waterlogged", () -> this.generateFence(true, this.blockWaterlogged, this.upperBlock));
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties().tab(SWDM.TAB)));
        return true;
    }

    @Override
    protected HalfFenceBlock generate() {
        return this.generateFence(false, this.lowerBlock, this.blockWaterlogged);
    }

    private HalfFenceBlock generateFence(boolean waterlogged, Supplier<HalfFenceBlock> next, Supplier<HalfFenceBlock> waterlog) {
        return new HalfFenceBlock(this.props(), waterlogged, this, next, waterlog);
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 4);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 11));
        gen.tintedHalfFence(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.blockWaterlogged.get(), SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.lowerBlock.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.lowerBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.upperBlock.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.upperBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.FENCES).add(this.generated, this.blockWaterlogged.get(), this.lowerBlock.get(), this.lowerBlockWaterlogged.get(), this.upperBlock.get(), this.upperBlockWaterlogged.get());
        gen.tag(net.minecraft.tags.BlockTags.WOODEN_FENCES).add(this.generated, this.blockWaterlogged.get(), this.lowerBlock.get(), this.lowerBlockWaterlogged.get(), this.upperBlock.get(), this.upperBlockWaterlogged.get());
    }

    @Override
    public void addLootTables(LootTables.ModLootTables gen) {
        gen.dropSelf(this.generated);
        gen.dropOther(this.blockWaterlogged.get(), this.generated);
        gen.dropOther(this.lowerBlock.get(), this.generated);
        gen.dropOther(this.lowerBlockWaterlogged.get(), this.generated);
        gen.dropOther(this.upperBlock.get(), this.generated);
        gen.dropOther(this.upperBlockWaterlogged.get(), this.generated);
    }

    @Override
    public String getSuffix() {
        return "_half_fence";
    }
}
