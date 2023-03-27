package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
	public ItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.withExistingParent(BlockInit.CHANGE_TOOL.getId().getPath(), "item/handheld").texture("layer0", "item/change_tool");
	}
}
