package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.entity.InvisibleItemFrame;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntitySetup {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ModRef.ID);

    public static final DeferredHolder<EntityType<?>, EntityType<InvisibleItemFrame>> INVISIBLE_ITEM_FRAME = REGISTRY.register("invisible_item_frame", () -> EntityType.Builder.<InvisibleItemFrame>of(InvisibleItemFrame::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("invisible_item_frame"));
    //public static final DeferredHolder<EntityType<MirrorPainting>> MIRROR_PAINTING = ENTITIES.register("mirror_painting", () -> EntityType.Builder.<MirrorPainting>of(MirrorPainting::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("mirror_painting"));

    public static void init(IEventBus modBus) {
        REGISTRY.register(modBus);

        remapEntities(REGISTRY);
    }

    public static void remapEntities(DeferredRegister<EntityType<?>> registry) {
        BuiltInRegistries.ENTITY_TYPE.keySet().forEach(mapping -> {
            String key = mapping.getPath();

            for (String wood : SetSetup.ALL_WOODS) {
                if (key.startsWith("mirror_" + wood)) {
                    remap(registry, key, "mirror_" + wood, wood + "_mirror");
                }
            }
        });
    }

    private static boolean remap(DeferredRegister<?> mapping, String key, String from, String to) {
        if (key.startsWith(from)) { // Using .contains() will result in blue/light_blue collision. eg. light_blue_dark_prismarine -> light_dark_prismarine_blue
            mapping.addAlias(ResourceLocation.parse(from), ResourceLocation.parse(to));
            return true;
        }
        return false;
    }
}
