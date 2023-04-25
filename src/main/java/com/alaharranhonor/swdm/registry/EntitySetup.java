package com.alaharranhonor.swdm.registry;

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

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SWDM.MOD_ID);

    public static final RegistryObject<EntityType<InvisibleItemFrame>> INVISIBLE_ITEM_FRAME = ENTITIES.register("invisible_item_frame", () -> EntityType.Builder.<InvisibleItemFrame>of(InvisibleItemFrame::new, MobCategory.MISC).build("invisible_item_frame"));
    public static final RegistryObject<EntityType<MirrorPainting>> MIRROR_PAINTING = ENTITIES.register("mirror_painting", () -> EntityType.Builder.<MirrorPainting>of(MirrorPainting::new, MobCategory.MISC).build("mirror_painting"));

    public static void init(IEventBus modBus) {
        ENTITIES.register(modBus);
    }
}
