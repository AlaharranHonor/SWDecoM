package com.alaharranhonor.swdm.gentypes.block;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.SWDMStandingSignBlock;
import com.alaharranhonor.swdm.block.SWDMWallSignBlock;
import com.alaharranhonor.swdm.datagen.*;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SignGen extends BasicBlockGen<SWDMStandingSignBlock> {

    private final WoodType wood;
    private RegistryObject<SWDMWallSignBlock> wallBlock;
    public SignGen(GenSet set, Supplier<Block> baseBlock, WoodType wood) {
        super(set, baseBlock);
        this.wood = wood;
    }

    @Override
    public boolean register(String name, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        // Don't register blocks which are in vanilla
        if (ForgeRegistries.BLOCKS.containsKey(new ResourceLocation("minecraft", name + this.getSuffix()))) {
            return false;
        }

        this.registeredName = name;
        RegistryObject<SWDMStandingSignBlock> standingSign = blocks.register(name + this.getSuffix(), this);
        this.wallBlock = blocks.register(name + this.getSuffix() + "_wall", () -> new SWDMWallSignBlock(this.props(), this.wood));
        items.register(name + this.getSuffix(), () -> new SignItem(new Item.Properties(), this.generated, this.wallBlock.get()));
        BlockSetup.SIGN_BLOCKS.add(standingSign);
        BlockSetup.SIGN_BLOCKS.add(this.wallBlock);
        return true;
    }

    @Override
    protected BlockBehaviour.Properties props() {
        return super.props().noCollission();
    }

    @Override
    protected SWDMStandingSignBlock generate() {
        return new SWDMStandingSignBlock(this.props(), this.wood);
    }

    @Override
    public void addRecipes(RecipeGen gen, Consumer<FinishedRecipe> builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 8);
    }

    @Override
    public void addLootTables(LootTableGen.BlockLoot gen) {
        super.addLootTables(gen);
        gen.dropOther(this.wallBlock.get(), this.generated);
    }

    @Override
    public void addBlockStates(BlockStateGen gen, TextureSet textures) {
        String path = blockKey(this.generated).getPath();
        ResourceLocation basePath = new ResourceLocation(blockKey(this.baseBlock.get()).getNamespace(), path.substring(0, path.length() - 5));
        gen.signBlock(this.generated, this.wallBlock.get(), textures.get("", basePath));
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        gen.singleTexture(blockKey(this.generated).getPath(), gen.mcLoc("item/generated"), "layer0", TextureSet.Builder.item(blockKey(this.generated)));
    }

    @Override
    public void addItemTags(ItemTagGen gen) {
        gen.tag(net.minecraft.tags.ItemTags.SIGNS).add(this.generated.asItem());
    }

    @Override
    public void addBlockTags(BlockTagGen gen) {
        gen.tag(net.minecraft.tags.BlockTags.STANDING_SIGNS).add(this.generated);
        gen.tag(net.minecraft.tags.BlockTags.WALL_SIGNS).add(this.wallBlock.get());
    }

    @Override
    public String getSuffix() {
        return "_sign";
    }
}
