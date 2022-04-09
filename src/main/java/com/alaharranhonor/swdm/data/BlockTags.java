package com.alaharranhonor.swdm.data;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.blocks.CoatedChain;
import com.alaharranhonor.swdm.util.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
	public BlockTags(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(pGenerator, modId, existingFileHelper);
	}

	@Override
	protected void addTags() {
		super.addTags();

		this.tag(net.minecraft.tags.BlockTags.WALLS).add(Blocks.CHAIN);
		for (RegistryObject<CoatedChain> chain : BlockInit.COATED_CHAINS) {
			this.tag(net.minecraft.tags.BlockTags.WALLS).add(chain.get());
		}

		this.tag(net.minecraft.tags.BlockTags.WALLS).addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_log_wall")).addOptional(new ResourceLocation(SWDM.MOD_ID, "whitewash_plank_wall"));
		this.tag(net.minecraft.tags.BlockTags.WALLS)
			.add(BlockInit.TERRACOTTA_WALL.get())
			.add(BlockInit.ACACIA_PLANK_WALL.get())
			.add(BlockInit.BIRCH_PLANK_WALL.get())
			.add(BlockInit.DARK_OAK_PLANK_WALL.get())
			.add(BlockInit.OAK_PLANK_WALL.get())
			.add(BlockInit.SPRUCE_PLANK_WALL.get())
			.add(BlockInit.JUNGLE_PLANK_WALL.get())
			.add(BlockInit.GLASS_WALL.get())
			.add(BlockInit.STONE_WALL.get());
		BlockInit.STONE_SET_WALLS.keySet().forEach(key -> {
			BlockInit.STONE_SET_WALLS.get(key).keySet().forEach(key2 -> {
				BlockInit.STONE_SET_WALLS.get(key).get(key2).forEach(rb -> {
					this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get());
				});
			});
		});

		BlockInit.SSW_SET_WALLS.keySet().forEach(key -> {
			BlockInit.SSW_SET_WALLS.get(key).keySet().forEach(key2 -> {
				BlockInit.SSW_SET_WALLS.get(key).get(key2).forEach(rb -> {
					this.tag(net.minecraft.tags.BlockTags.WALLS).add(rb.get());
				});
			});
		});
	}
}
