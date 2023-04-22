package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.HalfWallBlock;
import com.alaharranhonor.swdm.block.SWDMBlockstateProperties;
import com.alaharranhonor.swdm.datagen.BlockStates;
import com.alaharranhonor.swdm.datagen.BlockTags;
import com.alaharranhonor.swdm.datagen.ItemModels;
import com.alaharranhonor.swdm.datagen.ItemTags;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
        items.register(name + this.getSuffix() + "_lower", () -> new BlockItem(this.get(), new Item.Properties().tab(SWDM.TAB)));
        items.register(name + this.getSuffix() + "_upper", () -> new BlockItem(this.get(), new Item.Properties().tab(SWDM.TAB)));
        return true;
    }

    @Override
    protected HalfWallBlock generate() {
        return this.generateHalfWall(this.lowerBlock);
    }

    private HalfWallBlock generateHalfWall(Supplier<HalfWallBlock> next) {
        return new HalfWallBlock(this.props(), next);
    }

    @Override
    public void addBlockStates(BlockStates gen, TextureSet textures) {
        String path = this.generated.getRegistryName().getPath();
        ResourceLocation basePath = new ResourceLocation(this.baseBlock.get().getRegistryName().getNamespace(), path.substring(0, path.length() - 5));
        gen.tintedHalfWall(this.generated, SWDMBlockstateProperties.WallType.FULL, textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        //gen.tintedHalfWall(this.lowerBlock.get(), textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
        //gen.tintedHalfWall(this.upperBlock.get(), textures.get("side", basePath), textures.get("bottom", basePath), textures.get("top", basePath));
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
        gen.tag(net.minecraft.tags.BlockTags.WALLS).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_wall";
    }
}
