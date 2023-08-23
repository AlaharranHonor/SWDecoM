package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.DecoWorkshopBlock;
import com.alaharranhonor.swdm.block.entity.SWDMSignBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRef.ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModRef.ID);

    // Registered sign blocks are stored for the block set for sign block entity
    public static final List<RegistryObject<? extends SignBlock>> SIGN_BLOCKS = new ArrayList<>();
    public static RegistryObject<BlockEntityType<SWDMSignBlockEntity>> SIGN_BLOCK_ENTITY;

    public static final Map<ResourceLocation, RegistryObject<Block>> MANUAL_BLOCKS = new HashMap<>();

    public static final RegistryObject<Block> WHITEWASH_PLANKS = register("whitewash_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.SNOW)));
    public static final RegistryObject<Block> WHITEWASH_LOG = register("whitewash_log", () -> log(MapColor.SNOW, MapColor.SNOW));
    public static final RegistryObject<Block> WHITEWASH_STRIPPED_LOG = register("whitewash_stripped_log", () -> log(MapColor.SNOW, MapColor.SNOW));

    public static final RegistryObject<Block> BAMBOO_PLANKS = register("bamboo_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> BAMBOO_LOG = register("bamboo_log", () -> log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> BAMBOO_STRIPPED_LOG = register("bamboo_stripped_log", () -> log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));

    public static final RegistryObject<Block> THATCH = register("thatch", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistryObject<Block> THATCH_PLANKS = register("thatch_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> THATCH_LOG = register("thatch_log", () -> log(MapColor.COLOR_YELLOW, MapColor.COLOR_YELLOW));
    public static final RegistryObject<Block> THATCH_STRIPPED_LOG = register("thatch_stripped_log", () -> log(MapColor.COLOR_YELLOW, MapColor.COLOR_YELLOW));

    public static final RegistryObject<Block> SMOOTH_STONE_BORDERLESS = register("smooth_stone_borderless", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE)));
    public static final RegistryObject<Block> DECO_WORKSHOP = REGISTRY.register("deco_workshop", () -> new DecoWorkshopBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));


    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);
        BLOCK_ENTITIES.register(modBus);

        SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(SWDMSignBlockEntity::new, SIGN_BLOCKS.stream().map(RegistryObject::get).toList().toArray(new Block[0])).build(null));
    }

    public static RegistryObject<Block> register(String name, Supplier<Block> supplier) {
        RegistryObject<Block> block = REGISTRY.register(name, supplier);
        MANUAL_BLOCKS.put(block.getId(), block);
        return block;
    }

    private static RotatedPillarBlock log(MapColor pTopColor, MapColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((state) -> {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    public static Block get(String name) {
        return REGISTRY.getEntries().stream().filter(rb -> rb.getId().getPath().equals(name)).findFirst().get().get();
    }
}
