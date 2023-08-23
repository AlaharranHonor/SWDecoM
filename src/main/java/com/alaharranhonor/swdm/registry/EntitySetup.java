package com.alaharranhonor.swdm.registry;

import com.alaharranhonor.swdm.ModRef;
import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.entity.InvisibleItemFrame;
import com.alaharranhonor.swdm.entity.MirrorPainting;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntitySetup {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModRef.ID);

    public static final RegistryObject<EntityType<InvisibleItemFrame>> INVISIBLE_ITEM_FRAME = ENTITIES.register("invisible_item_frame", () -> EntityType.Builder.<InvisibleItemFrame>of(InvisibleItemFrame::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("invisible_item_frame"));
    //public static final RegistryObject<EntityType<MirrorPainting>> MIRROR_PAINTING = ENTITIES.register("mirror_painting", () -> EntityType.Builder.<MirrorPainting>of(MirrorPainting::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("mirror_painting"));

    public static void init(IEventBus modBus) {
        ENTITIES.register(modBus);
    }
}
