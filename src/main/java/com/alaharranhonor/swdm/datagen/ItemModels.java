package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.registry.ItemSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.util.RL;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
	public ItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.withExistingParent(ItemSetup.CHANGE_TOOL.getId().getPath(), "item/handheld").texture("layer0", "item/change_tool");

		for (GenSet set : SetSetup.SETS) {
			set.genTypes.forEach(genType -> {
				genType.addItemModels(this, set.getBlockTextures());
			});
		}
	}

	public ItemModelBuilder existingBlock(Block block) {
		return this.withExistingParent(block.getRegistryName().getPath(), RL.prefix(block.getRegistryName(), "block/"));
	}
}
