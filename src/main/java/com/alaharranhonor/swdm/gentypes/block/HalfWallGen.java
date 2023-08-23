package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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
import java.util.stream.Stream;

public class HalfWallGen extends BasicBlockGen<HalfWallBlock> {

    private RegistryObject<HalfWallBlock> lowerBlock;
    private RegistryObject<HalfWallBlock> upperBlock;
    private RegistryObject<HalfWallBlock> lowerBlockWaterlogged;
    private RegistryObject<HalfWallBlock> upperBlockWaterlogged;
    private RegistryObject<HalfWallBlock> blockWaterlogged;
    public HalfWallGen(GenSet set, Supplier<Block> baseBlock) {
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
        this.lowerBlock = blocks.register(name + this.getSuffix() + "_lower", () -> this.generateHalfWall(false, this.upperBlock, this.lowerBlockWaterlogged));
        this.upperBlock = blocks.register(name + this.getSuffix() + "_upper", () -> this.generateHalfWall(false, this, this.upperBlockWaterlogged));

        this.blockWaterlogged = blocks.register(name + this.getSuffix() + "_waterlogged", () -> this.generateHalfWall(true, this.lowerBlockWaterlogged, this));
        this.lowerBlockWaterlogged = blocks.register(name + this.getSuffix() + "_lower_waterlogged", () -> this.generateHalfWall(true, this.upperBlockWaterlogged, this.lowerBlock));
        this.upperBlockWaterlogged = blocks.register(name + this.getSuffix() + "_upper_waterlogged", () -> this.generateHalfWall(true, this.blockWaterlogged, this.upperBlock));
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties()));
        return true;
    }

    @Override
    protected HalfWallBlock generate() {
        return this.generateHalfWall(false, this.lowerBlock, this.blockWaterlogged);
    }

    private HalfWallBlock generateHalfWall(boolean waterlogged, Supplier<HalfWallBlock> next, Supplier<HalfWallBlock> waterlog) {
        return new HalfWallBlock(this.props(), waterlogged, this, next, waterlog);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedHalfWall(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.blockWaterlogged.get(), SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.lowerBlock.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.lowerBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.upperBlock.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.upperBlockWaterlogged.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory"));
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 1);
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
    public void registerBlockColors(BlockColors reg, BlockColor color) {
        reg.register(color, this.generated);
        reg.register(color, this.blockWaterlogged.get());
        reg.register(color, this.lowerBlock.get());
        reg.register(color, this.lowerBlockWaterlogged.get());
        reg.register(color, this.upperBlock.get());
        reg.register(color, this.upperBlockWaterlogged.get());
    }

    @Override
    public void addLang(EnUsLanguageGen gen) {
        super.addLang(gen);
    }

    @Override
    public void addLootTables(LootTableGen.BlockLoot gen) {
        gen.dropSelf(this.generated);
        gen.dropOther(this.blockWaterlogged.get(), this.generated);
        gen.dropOther(this.lowerBlock.get(), this.generated);
        gen.dropOther(this.lowerBlockWaterlogged.get(), this.generated);
        gen.dropOther(this.upperBlock.get(), this.generated);
        gen.dropOther(this.upperBlockWaterlogged.get(), this.generated);
    }

    @Override
    public void addItemTags(ItemTagGen gen) {

    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.WALLS).add(
            this.generated, this.lowerBlock.get(), this.upperBlock.get(),
            this.blockWaterlogged.get(), this.lowerBlockWaterlogged.get(), this.upperBlockWaterlogged.get());
    }

    @Override
    public Stream<HalfWallBlock> subTypes() {
        return Stream.of(this.generated, this.lowerBlock.get(), this.upperBlock.get(),
            this.blockWaterlogged.get(), this.lowerBlockWaterlogged.get(), this.upperBlockWaterlogged.get());
    }

    @Override
    public String getSuffix() {
        return "_wall";
    }
}
