package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.SWDM;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BlockSetup {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWDM.MOD_ID);
    public static final Map<ResourceLocation, RegistryObject<Block>> BLOCKS_BY_NAME = new HashMap<>();

    public static final RegistryObject<Block> MANGROVE_PLANKS = register("mangrove_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED)));
    public static final RegistryObject<Block> MANGROVE_LOG = register("mangrove_log", () -> log(MaterialColor.COLOR_RED, MaterialColor.COLOR_RED));
    public static final RegistryObject<Block> MANGROVE_STRIPPED_LOG = register("mangrove_stripped_log", () -> log(MaterialColor.COLOR_RED, MaterialColor.COLOR_RED));

    public static final RegistryObject<Block> WHITEWASH_PLANKS = register("whitewash_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.SNOW)));
    public static final RegistryObject<Block> WHITEWASH_LOG = register("whitewash_log", () -> log(MaterialColor.SNOW, MaterialColor.SNOW));
    public static final RegistryObject<Block> WHITEWASH_STRIPPED_LOG = register("whitewash_stripped_log", () -> log(MaterialColor.SNOW, MaterialColor.SNOW));

    public static final RegistryObject<Block> BAMBOO_PLANK = register("bamboo_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN)));
    public static final RegistryObject<Block> BAMBOO_LOG = register("bamboo_log", () -> log(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));
    public static final RegistryObject<Block> BAMBOO_STRIPPED_LOG = register("bamboo_stripped_log", () -> log(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));

    public static final RegistryObject<Block> THATCH_PLANK = register("thatch_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> THATCH_LOG = register("thatch_log", () -> log(MaterialColor.COLOR_YELLOW, MaterialColor.COLOR_YELLOW));
    public static final RegistryObject<Block> THATCH_STRIPPED_LOG = register("thatch_stripped_log", () -> log(MaterialColor.COLOR_YELLOW, MaterialColor.COLOR_YELLOW));


    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static RegistryObject<Block> register(String name, Supplier<Block> supplier) {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        BLOCKS_BY_NAME.put(block.getId(), block);
        return block;
    }

    private static RotatedPillarBlock log(MaterialColor pTopColor, MaterialColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }
}
