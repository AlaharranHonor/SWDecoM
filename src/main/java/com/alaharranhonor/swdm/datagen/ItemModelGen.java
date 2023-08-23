package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.util.RL;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModelGen extends ItemModelProvider {
	public ItemModelGen(PackOutput pOutput, ExistingFileHelper existingFileHelper) {
		super(pOutput, ModRef.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.withExistingParent(ItemSetup.CHANGE_TOOL.getId().getPath(), "item/handheld").texture("layer0", "item/change_tool");
		this.withExistingParent(ItemSetup.INVISIBLE_ITEM_FRAME.getId().getPath(), "item/generated").texture("layer0", "item/invisible_item_frame_item");
		this.withExistingParent(ItemSetup.MIRROR_PAINTING.getId().getPath(), "item/generated").texture("layer0", "item/mirror_painting_item");

		for (GenSet set : SetSetup.SETS) {
			set.genTypes.forEach(genType -> {
				genType.addItemModels(this, set.getBlockTextures());
			});
		}
	}

	public static ResourceLocation blockKey(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	public ItemModelBuilder existingBlock(Block block) {
		return this.withExistingParent(blockKey(block).getPath(), RL.prefix(blockKey(block), "block/"));
	}
}
