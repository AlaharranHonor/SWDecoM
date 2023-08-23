package com.alaharranhonor.swdm.datagen;

import com.alaharranhonor.swdm.GenSet;
import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.block.*;
import com.alaharranhonor.swdm.registry.BlockSetup;
import com.alaharranhonor.swdm.registry.SetSetup;
import com.alaharranhonor.swdm.util.RL;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class BlockStateGen extends BlockStateProvider {


    public BlockStateGen(PackOutput pOutput, ExistingFileHelper exFileHelper) {
        super(pOutput, ModRef.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockSetup.MANUAL_BLOCKS.values().forEach(block -> {
            if (block.getId().equals(BlockSetup.THATCH.getId())) {
                return;
            }
            this.simpleBlock(block.get());
            this.itemModels().withExistingParent(block.getId().getPath(), new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath()));
        });

        this.simpleBlock(BlockSetup.THATCH.get(), this.models().cubeColumn(BlockSetup.THATCH.getId().getPath(), this.modLoc("block/thatch"), this.modLoc("block/thatch_top")));
        this.itemModels().withExistingParent(BlockSetup.THATCH.getId().getPath(), RL.prefix(BlockSetup.THATCH.getId(), "block/"));

        this.simpleBlock(BlockSetup.DECO_WORKSHOP.get(), models().cubeBottomTop(BlockSetup.DECO_WORKSHOP.getId().getPath(), this.modLoc("block/deco_workshop_side"), this.modLoc("block/deco_workshop_bot"), this.modLoc("block/deco_workshop_top")));
        this.itemModels().withExistingParent(BlockSetup.DECO_WORKSHOP.getId().getPath(), this.modLoc("block/deco_workshop"));

        for (GenSet set : SetSetup.SETS) {
            set.genTypes.forEach(genType -> {
                genType.addBlockStates(this, set.getBlockTextures());
            });
        }
    }

    public static ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public void shelf(ShelfBlock block, ResourceLocation texture) {
        this.getVariantBuilder(block).forAllStates(state -> {
            SWDMBlockstateProperties.ShelfType shelfType = state.getValue(ShelfBlock.SHELF_TYPE);
            Direction facing = state.getValue(ShelfBlock.FACING);
            return ConfiguredModel.builder().modelFile(
                    this.models().withExistingParent(key(block).getPath() + "_" + shelfType.getSerializedName(), this.modLoc("shelf_" + shelfType.getSerializedName())).texture("texture", texture)
                ).rotationY((int) facing.toYRot())
                .build();
        });
    }

    public void tintedStairs(StairBlock block, ResourceLocation texture) {
        this.tintedStairs(block, texture, texture, texture);
    }

    public void tintedStairs(StairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        ModelFile stairs = models().withExistingParent(key(block).toString(), modLoc("block/stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile stairsInner = models().withExistingParent(key(block).toString() + "_inner", modLoc("block/inner_stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile stairsOuter = models().withExistingParent(key(block).toString() + "_outer", modLoc("block/outer_stairs"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);

        this.stairsBlock(block, stairs, stairsInner, stairsOuter);
    }

    public void ladder(LadderBlock block, ResourceLocation texture) {
        this.getVariantBuilder(block)
            .forAllStates(state -> {
                Direction facing = state.getValue(LadderBlock.FACING);
                return ConfiguredModel.builder().modelFile(
                    this.models().withExistingParent(key(block).getPath(), this.mcLoc("block/ladder")).texture("texture", texture)
                ).rotationY((int) (facing.toYRot() + 180) % 360).build();
            });
    }

    public void beamBlock(BeamBlock block) {
        String name = key(block).getPath();
        this.getVariantBuilder(block).forAllStates(state -> {
            SWDMBlockstateProperties.Tileable tile = state.getValue(BeamBlock.TILE);
            return ConfiguredModel.builder()
                .modelFile(this.models().cubeColumn(
                    name + "_" + tile.getSerializedName(),
                    this.modLoc("block/" + name + "_" + tile.getSerializedName()),
                    this.modLoc("block/" + name + "_single")
                )).build();
        });

        this.itemModels().withExistingParent(name, modLoc("block/" + name + "_single")); // Item model
    }

    public void tintedSlab(SlabBlock block, ResourceLocation texture, ResourceLocation doubleSlab) {
        tintedSlab(block, texture, texture, texture, doubleSlab);
    }

    public void tintedSlab(SlabBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation doubleSlab) {
        this.slabBlock(block,
            models().withExistingParent(key(block).getPath(), modLoc("block/slab"))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top),
            models().withExistingParent(key(block).getPath() + "_top", modLoc("block/slab_top"))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top),
            models().getExistingFile(doubleSlab)
        );
    }

    public void tintedHalfWall(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.halfWallBlock(block, wallType, side, bottom, top);
        this.halfWallBlockItem(block, side, bottom, top);
    }

    public void tintedHalfWall(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation texture) {
        this.halfWallBlock(block, wallType, texture);
        this.halfWallBlockItem(block, texture);
    }

    public void tintedHalfFence(HalfFenceBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.halfFenceBlock(block, wallType, side, bottom, top);
        this.halfFenceBlockItem(block, side, bottom, top);
    }

    public void tintedTrapdoor(TrapDoorBlock block, ResourceLocation texture) {
        tintedTrapdoor(block, texture, texture, texture);
    }

    public void shutter(ShutterBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
        ModelFile model = models().withExistingParent(key(block).toString(), modLoc("block/template_shutter"))
            .texture("side", side)
            .texture("front", bottom)
            .texture("back", top);
        this.shutterBlock(block, model);
    }

    public void shutterBlock(ShutterBlock block, ModelFile model) {
        this.shutterBlock(block, model, model);
    }

    public void shutterBlock(ShutterBlock block, ModelFile leftModel, ModelFile rightModel) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(ShutterBlock.FACING).toYRot()) + 90;
            boolean rh = state.getValue(ShutterBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(ShutterBlock.OPEN);
            boolean right = rh ^ open;
            if (open) {
                yRot += 90;
            }
            if (rh && open) {
                yRot += 180;
            }
            yRot %= 360;
            return ConfiguredModel.builder().modelFile(right ? rightModel : leftModel)
                .rotationY(yRot)
                .build();
        }, ShutterBlock.POWERED);
    }

    public void tintedTrapdoor(TrapDoorBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
        ModelFile openModel = models().withExistingParent(key(block).toString() + "_open", modLoc("block/template_orientable_trapdoor_open"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile topModel = models().withExistingParent(key(block).toString() + "top", modLoc("block/template_orientable_trapdoor_top"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        ModelFile bottomModel = models().withExistingParent(key(block).toString() + "_bottom", modLoc("block/template_orientable_trapdoor_bottom"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
        this.trapdoorBlock(block, bottomModel, topModel, openModel, true);
    }

    public void halfWallBlockItem(HalfWallBlock block, ResourceLocation texture) {
        halfWallBlockItem(block, texture, texture, texture);
    }

    public void halfWallBlockItem(HalfWallBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.models().withExistingParent(key(block).getPath() + "_inventory", modLoc("block/wall_inventory"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public void halfWallBlock(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        halfWallBlockInternal(block, wallType, key(block).toString(), side, bottom, top);
    }

    public void halfWallBlock(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation texture) {
        halfWallBlockInternal(block, wallType, key(block).toString(), texture, texture, texture);
    }

    private void halfWallBlockInternal(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType, String baseName, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        halfWallBlock(block, wallType,
            wallPost(baseName + "_post", side, bottom, top), wallSide(baseName + "_side", side, bottom, top), wallSideTall(baseName + "_side_tall", side, bottom, top),
            halfWallPost(baseName + "_half_post", side, bottom, top), halfWallSide(baseName + "_half_side", side, bottom, top)
        );
    }

    public void halfWallBlock(HalfWallBlock block, SWDMBlockstateProperties.WallType wallType,
                              ModelFile post, ModelFile side, ModelFile sideTall,
                              ModelFile halfPost, ModelFile halfSide
    ) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        if (wallType == SWDMBlockstateProperties.WallType.FULL) {
            builder.part().modelFile(post).addModel().condition(HalfWallBlock.UP, true);
        }
        if (wallType == SWDMBlockstateProperties.WallType.LOWER) {
            builder.part().modelFile(halfPost).addModel().condition(HalfWallBlock.UP, true);
        }
        if (wallType == SWDMBlockstateProperties.WallType.UPPER) {
            builder.part().modelFile(halfPost).rotationX(180).addModel().condition(HalfWallBlock.UP, true);
        }

        BlockStateProvider.WALL_PROPS.entrySet().stream()
            .filter(e -> e.getKey().getAxis().isHorizontal())
            .forEach(entry -> {
                if (wallType == SWDMBlockstateProperties.WallType.FULL) {
                    wallSidePartFull(builder, side, entry, WallSide.LOW);
                    wallSidePartFull(builder, sideTall, entry, WallSide.TALL);
                } else {
                    wallSidePartHalf(builder, halfSide, entry, wallType);
                    wallSidePartHalf(builder, halfSide, entry, wallType);
                }
            });
    }

    private void wallSidePartFull(MultiPartBlockStateBuilder builder, ModelFile fullWall, Map.Entry<Direction, Property<WallSide>> entry, WallSide height) {
        builder.part()
            .modelFile(fullWall)
            .rotationY((((int) entry.getKey().toYRot()) + 180) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), height);
    }

    private void wallSidePartHalf(MultiPartBlockStateBuilder builder, ModelFile halfWall, Map.Entry<Direction, Property<WallSide>> entry, SWDMBlockstateProperties.WallType type) {
        builder.part()
            .modelFile(halfWall)
            .rotationX(type == SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)
            .rotationY((((int) entry.getKey().toYRot()) + (type != SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), WallSide.TALL, WallSide.LOW);
    }

    public void halfFenceBlockItem(HalfFenceBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        this.models().withExistingParent(key(block).getPath() + "_inventory", modLoc("block/fence_inventory"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public void halfFenceBlock(HalfFenceBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        String baseName = key(block).toString();
        halfFenceBlock(block, wallType,
            fencePost(baseName + "_post", side, bottom, top), fenceSide(baseName + "_side", side, bottom, top),
            halfFencePost(baseName + "_half_post", side, bottom, top), halfFenceSide(baseName + "_half_side", side, bottom, top));
    }

    public void halfFenceBlock(HalfFenceBlock block, SWDMBlockstateProperties.WallType wallType,
                               ModelFile post, ModelFile side,
                               ModelFile halfPost, ModelFile halfSide) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        if (wallType == SWDMBlockstateProperties.WallType.FULL) {
            builder.part().modelFile(post).addModel();
        }
        if (wallType == SWDMBlockstateProperties.WallType.LOWER) {
            builder.part().modelFile(halfPost).addModel();
        }
        if (wallType == SWDMBlockstateProperties.WallType.UPPER) {
            builder.part().modelFile(halfPost).rotationX(180).addModel();
        }

        this.halfFenceBlockMultipart(builder, wallType, side, halfSide);
    }

    public void halfFenceBlockMultipart(MultiPartBlockStateBuilder builder, SWDMBlockstateProperties.WallType wallType, ModelFile side, ModelFile halfSide) {
        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream()
            .filter(e -> e.getKey().getAxis().isHorizontal())
            .forEach(entry -> {
                Direction dir = entry.getKey();
                BooleanProperty value = entry.getValue();
                if (wallType == SWDMBlockstateProperties.WallType.FULL) {
                    fenceSidePartFull(builder, side, entry);
                } else {
                    fenceSidePartHalf(builder, halfSide, entry, wallType);
                }
            });
    }

    private void fenceSidePartFull(MultiPartBlockStateBuilder builder, ModelFile fullFence, Map.Entry<Direction, BooleanProperty> entry) {
        builder.part()
            .modelFile(fullFence)
            .rotationY((((int) entry.getKey().toYRot()) + 180) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), true);
    }

    private void fenceSidePartHalf(MultiPartBlockStateBuilder builder, ModelFile halfFence, Map.Entry<Direction, BooleanProperty> entry, SWDMBlockstateProperties.WallType type) {
        builder.part()
            .modelFile(halfFence)
            .rotationX(type == SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)
            .rotationY((((int) entry.getKey().toYRot()) + (type != SWDMBlockstateProperties.WallType.UPPER ? 180 : 0)) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), true);
    }

    public void swdmFenceBlockItem(HalfFenceBlock block, ResourceLocation texture, ResourceLocation lattice, String fenceType) {
        this.models().withExistingParent(key(block).getPath() + "_inventory", modLoc("block/fence/fence_" + fenceType + "_inv")).texture("texture", texture).texture("lattice", lattice);
    }

    public void swdmFenceBlock(HalfFenceBlock block, SWDMBlockstateProperties.WallType wallType, ResourceLocation texture, ResourceLocation lattice, String fenceType) {
        String baseName = key(block).toString();
        ModelFile post = models().withExistingParent(baseName + "_post", this.modLoc("block/fence/fence_post")).texture("texture", texture).texture("lattice", lattice).texture("particle", texture);
        ModelFile halfPost = models().withExistingParent(baseName + "_half_post", this.modLoc("block/fence/fence_half_post")).texture("texture", texture).texture("lattice", lattice).texture("particle", texture);
        ModelFile full = models().withExistingParent(baseName + "_full", this.modLoc("block/fence/fence_" + fenceType + "_full")).texture("texture", texture).texture("lattice", lattice).texture("particle", texture);
        ModelFile lower = models().withExistingParent(baseName + "_lower", this.modLoc("block/fence/fence_" + fenceType + "_lower")).texture("texture", texture).texture("lattice", lattice).texture("particle", texture);
        ModelFile upper = models().withExistingParent(baseName + "_upper", this.modLoc("block/fence/fence_" + fenceType + "_upper")).texture("texture", texture).texture("lattice", lattice).texture("particle", texture);
        swdmFenceBlock(block, wallType, post, halfPost, full, upper, lower);
    }

    public void swdmFenceBlock(HalfFenceBlock block, SWDMBlockstateProperties.WallType wallType,
                               ModelFile post, ModelFile halfPost,
                               ModelFile full, ModelFile upper, ModelFile lower) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        if (wallType == SWDMBlockstateProperties.WallType.FULL) {
            builder.part().modelFile(post).addModel();
        }
        if (wallType == SWDMBlockstateProperties.WallType.LOWER) {
            builder.part().modelFile(halfPost).addModel();
        }
        if (wallType == SWDMBlockstateProperties.WallType.UPPER) {
            builder.part().modelFile(halfPost).rotationX(180).addModel();
        }

        this.swdmFenceBlockMultipart(builder, wallType, full, upper, lower);
    }

    public void swdmFenceBlockMultipart(MultiPartBlockStateBuilder builder, SWDMBlockstateProperties.WallType wallType, ModelFile full, ModelFile upper, ModelFile lower) {
        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream()
            .filter(e -> e.getKey().getAxis().isHorizontal())
            .forEach(entry -> {
                if (wallType == SWDMBlockstateProperties.WallType.FULL) {
                    swdmFenceSidePartFull(builder, full, entry);
                } else if (wallType == SWDMBlockstateProperties.WallType.LOWER) {
                    swdmFenceSidePartHalf(builder, lower, entry, wallType);
                } else {
                    swdmFenceSidePartHalf(builder, upper, entry, wallType);
                }
            });

    }

    private void swdmFenceSidePartFull(MultiPartBlockStateBuilder builder, ModelFile fullFence, Map.Entry<Direction, BooleanProperty> entry) {
        builder.part()
            .modelFile(fullFence)
            .rotationY((((int) entry.getKey().toYRot()) + 270) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), true);
    }

    private void swdmFenceSidePartHalf(MultiPartBlockStateBuilder builder, ModelFile halfFence, Map.Entry<Direction, BooleanProperty> entry, SWDMBlockstateProperties.WallType type) {
        builder.part()
            .modelFile(halfFence)
            .rotationY((((int) entry.getKey().toYRot()) + 270) % 360)
            .uvLock(true)
            .addModel()
            .condition(entry.getValue(), true);
    }

    public BlockModelBuilder halfFencePost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_fence_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder halfFenceSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_fence_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder fencePost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_fence_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder fenceSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_fence_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder wallPost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder wallSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder wallSideTall(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_wall_side_tall"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder halfWallPost(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_wall_post"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }

    public BlockModelBuilder halfWallSide(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, this.modLoc("block/template_half_wall_side"))
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("top", top);
    }


    public void horizontalCarpet(CarpetBlock block, ResourceLocation wool) {
        ModelFile carpetModel = models().carpet(key(block).toString(), wool);
        this.horizontalBlock(block, carpetModel);
    }

    public void carpet(CarpetBlock block, ResourceLocation wool) {
        ModelFile carpetModel = models().carpet(key(block).toString(), wool);
        this.getVariantBuilder(block)
            .addModels(this.getVariantBuilder(block).partialState(), new ConfiguredModel(carpetModel));
    }

    public void pressurePlate(PressurePlateBlock block, ResourceLocation texture) {
        ModelFile pressurePlateUp = models().singleTexture(key(block).toString(), mcLoc("block/pressure_plate_up"), texture);
        ModelFile pressurePlateDown = models().singleTexture(key(block).toString() + "_down", mcLoc("block/pressure_plate_down"), texture);

        this.getVariantBuilder(block)
            .partialState().with(PressurePlateBlock.POWERED, true)
            .modelForState().modelFile(pressurePlateDown).addModel()
            .partialState().with(PressurePlateBlock.POWERED, false)
            .modelForState().modelFile(pressurePlateUp).addModel();

    }

    public void button(ButtonBlock block, ResourceLocation texture) {
        ModelFile button = models().singleTexture(key(block).toString(), mcLoc("block/button"), texture);
        ModelFile buttonPressed = models().singleTexture(key(block).toString() + "_pressed", mcLoc("block/button_pressed"), texture);
        models().singleTexture(key(block).toString() + "_inventory", mcLoc("block/button_inventory"), texture);

        this.getVariantBuilder(block)
            .forAllStates((state) -> {
                int yRot = ((int) state.getValue(TrapDoorBlock.FACING).toYRot()) + 180;
                int xRot = 0;
                if (state.getValue(ButtonBlock.FACE) == AttachFace.CEILING) {
                    xRot = 180;
                } else if (state.getValue(ButtonBlock.FACE) == AttachFace.WALL) {
                    xRot = 90;
                }

                return ConfiguredModel.builder().modelFile(state.getValue(ButtonBlock.POWERED) ? buttonPressed : button)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
            });
    }

    public void twoWayBlock(TwoWayBlock block) {
        ModelFile single = models().singleTexture(key(block).getPath() + "_single", mcLoc("block/chain"), "all", modLoc("block/" + key(block).getPath() + "_single"));
        ModelFile middle = models().singleTexture(key(block).getPath() + "_middle", mcLoc("block/chain"), "all", modLoc("block/" + key(block).getPath() + "_middle"));
        ModelFile edge = models().singleTexture(key(block).getPath() + "_edge", mcLoc("block/chain"), "all", modLoc("block/" + key(block).getPath() + "_edge"));

        this.itemModels().singleTexture(key(block).getPath(), mcLoc("item/generated"), "layer0", new ResourceLocation("swdm", "item/" + key(block).getPath()));

        this.getVariantBuilder(block).forAllStates((state) -> {
            ModelFile fileToUse = state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.SINGLE
                ? single
                : state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.MIDDLE
                ? middle
                : edge;

            return ConfiguredModel.builder().modelFile(fileToUse)
                .rotationX(state.getValue(TwoWayBlock.AXIS).isHorizontal() ? 90 : (state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.RIGHT ? 180 : 0))
                .rotationY((state.getValue(TwoWayBlock.AXIS) == Direction.Axis.X ? 90 : 0) + (state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.RIGHT && state.getValue(TwoWayBlock.AXIS) == Direction.Axis.X ? 180 : state.getValue(TwoWayBlock.PART) == SWDMBlockstateProperties.TwoWay.LEFT && state.getValue(TwoWayBlock.AXIS) == Direction.Axis.Z ? 180 : 0))
                .build();
        });
    }
}
