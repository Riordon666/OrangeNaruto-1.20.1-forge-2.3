package net.xwj.test.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xwj.test.entity.custom.FlyingThunderGodEntity;
import net.xwj.test.test;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, test.MODID);

    public static final RegistryObject<EntityType<FlyingThunderGodEntity>> FLYING_THUNDER_GOD =
            ENTITY_TYPES.register("flying_thunder_god",
                    () -> EntityType.Builder.<FlyingThunderGodEntity>of(FlyingThunderGodEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("flying_thunder_god"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
