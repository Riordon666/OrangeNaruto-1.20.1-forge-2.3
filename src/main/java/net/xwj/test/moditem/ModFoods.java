package net.xwj.test.moditem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties SALAD = new FoodProperties.Builder().nutrition(15).saturationMod(1.8f)
            .effect(()-> new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1500,2),0.8f)
            .effect(()-> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,1500,2),0.8f)
            .effect(()-> new MobEffectInstance(MobEffects.JUMP,1500),0.8f)
            .effect(()-> new MobEffectInstance(MobEffects.REGENERATION,1500,2),0.8f)
            .effect(()-> new MobEffectInstance(MobEffects.SATURATION,1500),0.8f)
            .alwaysEat().build();



    public static final FoodProperties WOJU = new FoodProperties.Builder().nutrition(8).saturationMod(1.4f)
            .effect(()-> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,1200),0.9f)
            .alwaysEat().build();

    public static final FoodProperties FANQIE = new FoodProperties.Builder().nutrition(8).saturationMod(1.4f)
            .effect(()-> new MobEffectInstance(MobEffects.JUMP,1200),0.9f)
            .alwaysEat().build();

    public static final FoodProperties HUANGGUA = new FoodProperties.Builder().nutrition(8).saturationMod(1.4f)
            .effect(()-> new MobEffectInstance(MobEffects.REGENERATION,1200),0.9f)
            .effect(()-> new MobEffectInstance(MobEffects.BLINDNESS,200),0.1f)
            .alwaysEat().build();

    public static final FoodProperties BOCAI = new FoodProperties.Builder().nutrition(8).saturationMod(1.4f)
            .effect(()-> new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200),0.9f)
            .alwaysEat().build();

    public static final FoodProperties PLATE = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f)
            .effect(()-> new MobEffectInstance(MobEffects.HUNGER,100,1),1f)
            .effect(()-> new MobEffectInstance(MobEffects.CONFUSION,100,1),1f)
            .effect(()-> new MobEffectInstance(MobEffects.BLINDNESS,100,1),1f)
            .effect(()-> new MobEffectInstance(MobEffects.POISON,100,1),1f)
            .alwaysEat().build();
}
