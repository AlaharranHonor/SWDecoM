package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HalfWallGen extends BasicBlockGen<HalfWallBlock> {

    private RegistryObject<HalfWallBlock> lowerBlock;
    private RegistryObject<HalfWallBlock> upperBlock;
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
        this.lowerBlock = blocks.register(name + this.getSuffix() + "_lower", () -> this.generateHalfWall(this.upperBlock));
        this.upperBlock = blocks.register(name + this.getSuffix() + "_upper", () -> this.generateHalfWall(this));
        items.register(name + this.getSuffix(), () -> new BlockItem(this.get(), new Item.Properties().tab(SWDM.TAB)));
        return true;
    }

    @Override
    protected HalfWallBlock generate() {
        return this.generateHalfWall(this.lowerBlock);
    }

    private HalfWallBlock generateHalfWall(Supplier<HalfWallBlock> next) {
        return new HalfWallBlock(this.props(), this, next);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedHalfWall(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.lowerBlock.get(), SWDMBlockstateProperties.WallType.LOWER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        gen.tintedHalfWall(this.upperBlock.get(), SWDMBlockstateProperties.WallType.UPPER, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
    }

    @Override
    public void addItemModels(ItemModels gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        gen.withExistingParent(path, gen.modLoc("block/" + path + "_inventory"));
    }

    @Override
    public void addRecipes(Recipes gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 1);
    }

    @Override
    public void addLang(Languages gen) {
        super.addLang(gen);
    }

    @Override
    public void addLootTables(LootTables.ModLootTables gen) {
        gen.dropSelf(this.generated);
        gen.dropOther(this.lowerBlock.get(), this.generated);
        gen.dropOther(this.upperBlock.get(), this.generated);
    }

    @Override
    public void addItemTags(ItemTags gen) {

    }

    @Override
    public void addBlockTags(BlockTags gen) {
        gen.tag(net.minecraft.tags.BlockTags.WALLS).add(this.generated, this.lowerBlock.get(), this.upperBlock.get());
    }

    @Override
    public String getSuffix() {
        return "_wall";
    }
}
