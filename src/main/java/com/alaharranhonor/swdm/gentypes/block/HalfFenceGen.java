package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.block.HalfFenceBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class HalfFenceGen extends BasicBlockGen<HalfFenceBlock> {

    protected DeferredBlock<HalfFenceBlock> lowerBlock;
    protected DeferredBlock<HalfFenceBlock> upperBlock;
    protected DeferredBlock<HalfFenceBlock> lowerBlockWaterlogged;
    protected DeferredBlock<HalfFenceBlock> upperBlockWaterlogged;
    protected DeferredBlock<HalfFenceBlock> blockWaterlogged;
    public HalfFenceGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    public boolean register(String name, DeferredRegister.Blocks blocks, DeferredRegister.Items items) {
        // Don't register blocks which are in vanilla
        if (BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("minecraft", name + this.getSuffix()))) {
            return false;
        }

        this.registeredName = name;
        blocks.register(name + this.getSuffix(), this);
        this.lowerBlock = blocks.register(name + this.getSuffix() + "_lower", () -> this.generateFence(false, this.upperBlock, this.lowerBlockWaterlogged));
        this.upperBlock = blocks.register(name + this.getSuffix() + "_upper", () -> this.generateFence(false, this, this.upperBlockWaterlogged));

        this.blockWaterlogged = blocks.register(name + this.getSuffix() + "_waterlogged", () -> this.generateFence(true, this.lowerBlockWaterlogged, this));
        this.lowerBlockWaterlogged = blocks.register(name + this.getSuffix() + "_lower_waterlogged", () -> this.generateFence(true, this.upperBlockWaterlogged, this.lowerBlock));
        this.upperBlockWaterlogged = blocks.register(name + this.getSuffix() + "_upper_waterlogged", () -> this.generateFence(true, this.blockWaterlogged, this.upperBlock));
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties()));
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
    public void addRecipes(RecipeGen gen, RecipeOutput builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 4);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 11));
        gen.tintedHalfFence(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.blockWaterlogged.get(), SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.lowerBlock.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.lowerBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.upperBlock.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfFence(this.upperBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory")); // Item model
    }

    @Override
    public void setRenderType(RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(this.generated, renderType);
        ItemBlockRenderTypes.setRenderLayer(this.blockWaterlogged.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(this.lowerBlock.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(this.lowerBlockWaterlogged.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(this.upperBlock.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(this.upperBlockWaterlogged.get(), renderType);
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.FENCES).add(this.generated, this.blockWaterlogged.get(), this.lowerBlock.get(), this.lowerBlockWaterlogged.get(), this.upperBlock.get(), this.upperBlockWaterlogged.get());
        gen.tag(net.minecraft.tags.BlockTags.WOODEN_FENCES).add(this.generated, this.blockWaterlogged.get(), this.lowerBlock.get(), this.lowerBlockWaterlogged.get(), this.upperBlock.get(), this.upperBlockWaterlogged.get());
    }

    @Override
    public void addLootTables(BlockLoot gen) {
        gen.dropSelf(this.generated);
        gen.dropOther(this.blockWaterlogged.get(), this.generated);
        gen.dropOther(this.lowerBlock.get(), this.generated);
        gen.dropOther(this.lowerBlockWaterlogged.get(), this.generated);
        gen.dropOther(this.upperBlock.get(), this.generated);
        gen.dropOther(this.upperBlockWaterlogged.get(), this.generated);
    }

    @Override
    public Stream<HalfFenceBlock> subTypes() {
        return Stream.of(this.generated, this.blockWaterlogged.get(), this.lowerBlock.get(), this.lowerBlockWaterlogged.get(), this.upperBlock.get(), this.upperBlockWaterlogged.get());
    }

    @Override
    public String getSuffix() {
        return "_half_fence";
    }
}
