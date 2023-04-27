package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.block.DecoWorkshopBlock;
import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockSetup {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWDM.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SWDM.MOD_ID);

    // Registered sign blocks are stored for the block set for sign block entity
    public static final List<RegistryObject<? extends SignBlock>> SIGN_BLOCKS = new ArrayList<>();
    public static RegistryObject<BlockEntityType<SWDMSignBlockEntity>> SIGN_BLOCK_ENTITY;

    public static final Map<ResourceLocation, RegistryObject<Block>> MANUAL_BLOCKS = new HashMap<>();

    public static final RegistryObject<Block> WHITEWASH_PLANKS = register("whitewash_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SNOW)));
    public static final RegistryObject<Block> WHITEWASH_LOG = register("whitewash_log", () -> log(MaterialColor.SNOW, MaterialColor.SNOW));
    public static final RegistryObject<Block> WHITEWASH_STRIPPED_LOG = register("whitewash_stripped_log", () -> log(MaterialColor.SNOW, MaterialColor.SNOW));

    public static final RegistryObject<Block> BAMBOO_PLANKS = register("bamboo_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN)));
    public static final RegistryObject<Block> BAMBOO_LOG = register("bamboo_log", () -> log(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));
    public static final RegistryObject<Block> BAMBOO_STRIPPED_LOG = register("bamboo_stripped_log", () -> log(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));

    public static final RegistryObject<Block> THATCH = register("thatch", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistryObject<Block> THATCH_PLANKS = register("thatch_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> THATCH_LOG = register("thatch_log", () -> log(MaterialColor.COLOR_YELLOW, MaterialColor.COLOR_YELLOW));
    public static final RegistryObject<Block> THATCH_STRIPPED_LOG = register("thatch_stripped_log", () -> log(MaterialColor.COLOR_YELLOW, MaterialColor.COLOR_YELLOW));

    public static final RegistryObject<Block> SMOOTH_STONE_BORDERLESS = register("smooth_stone_borderless", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE)));
    public static final RegistryObject<Block> DECO_WORKSHOP = BLOCKS.register("deco_workshop", () -> new DecoWorkshopBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.5F, 6.0F)));


    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_ENTITIES.register(modBus);

        SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(SWDMSignBlockEntity::new, SIGN_BLOCKS.stream().map(RegistryObject::get).toList().toArray(new Block[0])).build(null));
    }

    public static RegistryObject<Block> register(String name, Supplier<Block> supplier) {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        MANUAL_BLOCKS.put(block.getId(), block);
        return block;
    }

    private static RotatedPillarBlock log(MaterialColor pTopColor, MaterialColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    public static Block get(String name) {
        return BLOCKS.getEntries().stream().filter(rb -> rb.getId().getPath().equals(name)).findFirst().get().get();
    }
}
