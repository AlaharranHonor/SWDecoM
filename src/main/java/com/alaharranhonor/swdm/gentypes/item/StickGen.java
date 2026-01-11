package com.alaharranhonor.swdm.gentypes.item;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.datagen.ItemModelGen;
import com.alaharranhonor.swdm.datagen.ItemTagGen;
import com.alaharranhonor.swdm.datagen.RecipeGen;
import com.alaharranhonor.swdm.util.TextureSet;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Supplier;

public class StickGen extends BasicItemGen<Item> {

    public StickGen(GenSet set, Supplier<Block> baseBlock) {
        super(set, baseBlock);
    }

    @Override
    protected Item generate() {
        return new Item(new Item.Properties());
    }

    @Override
    public void addItemModels(ItemModelGen gen, TextureSet textures) {
        ResourceLocation basePath = itemKey(this.generated);
        gen.withExistingParent(itemKey(this.generated).getPath(), "item/handheld").texture("layer0", textures.get("item", basePath));
    }

    @Override
    public void addRecipes(RecipeGen gen, RecipeOutput builder) {
        gen.defaultDecoBench(builder, this.generated, this.baseBlock.get(), 32);
    }

    @Override
    public void addItemTags(ItemTagGen gen) {
        gen.tag(Tags.Items.RODS_WOODEN).add(this.generated);
    }

    @Override
    public String getSuffix() {
        return "_stick";
    }
}
