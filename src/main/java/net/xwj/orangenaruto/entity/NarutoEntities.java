package net.xwj.orangenaruto.entity;

import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.entity.SubstitutionLogEntity;
import net.xwj.orangenaruto.entity.item.PaperBombEntity;
import net.xwj.orangenaruto.entity.jutsuprojectile.FireballJutsuEntity;
import net.xwj.orangenaruto.entity.jutsuprojectile.WaterBulletJutsuEntity;
import net.xwj.orangenaruto.entity.projectile.ExplosiveKunaiEntity;
import net.xwj.orangenaruto.entity.projectile.KunaiEntity;
import net.xwj.orangenaruto.entity.projectile.SenbonEntity;
import net.xwj.orangenaruto.entity.projectile.ShurikenEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xwj.orangenaruto.OrangeNaruto;

import static net.xwj.orangenaruto.OrangeNaruto.MODID;

@Mod.EventBusSubscriber(modid = OrangeNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NarutoEntities {

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI = register("kunai",
            EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(8));

    public static final RegistryObject<EntityType<SenbonEntity>> SENBON = register("senbon",
            EntityType.Builder.<SenbonEntity>of(SenbonEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(8));

    public static final RegistryObject<EntityType<ExplosiveKunaiEntity>> EXPLOSIVE_KUNAI = register("explosive_kunai",
            EntityType.Builder.<ExplosiveKunaiEntity>of(ExplosiveKunaiEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(8));

    public static final RegistryObject<EntityType<ShurikenEntity>> SHURIKEN = register("shuriken",
            EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(8));

    // func_233608_b_ is updateInterval
    public static final RegistryObject<EntityType<PaperBombEntity>> PAPER_BOMB = register("paper_bomb",
            EntityType.Builder.<PaperBombEntity>of(PaperBombEntity::new, MobCategory.MISC).fireImmune().sized(0.5F, 0.5F).setTrackingRange(10).clientTrackingRange(10));


    public static final RegistryObject<EntityType<FireballJutsuEntity>> FIREBALL_JUTSU = register("fireball_jutsu",
            EntityType.Builder.<FireballJutsuEntity>of(FireballJutsuEntity::new, MobCategory.MISC).sized(1.5F, 1.5F).clientTrackingRange(4).updateInterval(10));

    public static final RegistryObject<EntityType<WaterBulletJutsuEntity>> WATER_BULLET_JUTSU = register("water_bullet_jutsu",
            EntityType.Builder.<WaterBulletJutsuEntity>of(WaterBulletJutsuEntity::new, MobCategory.MISC).fireImmune().sized(0.3F, 0.3F).clientTrackingRange(4).updateInterval(10));


    public static final RegistryObject<EntityType<SubstitutionLogEntity>> SUBSTITUTION_LOG = register("substitution_log",
            EntityType.Builder.<SubstitutionLogEntity>of(SubstitutionLogEntity::new, MobCategory.MISC).fireImmune().sized(0.3F, 0.3F).clientTrackingRange(4));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String key, EntityType.Builder<T> builder) {
        return ENTITIES.register(key, () -> builder.build(key));
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(SUBSTITUTION_LOG.get(), SubstitutionLogEntity.createAttributes().build());
    }

}
