package net.xwj.test.tools;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.xwj.test.moditem.ModItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.xwj.test.test;
import net.xwj.test.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public enum ModToolTiers implements Tier {

    ORANGE(4,2000,50.0F,3.0F,16,
            ()->{return Ingredient.of(ModItems.ORANGE_SWORD.get());});

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantmentvalue;
    private final Supplier<Ingredient> repairMaterial;

    ModToolTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantmentvalue, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.enchantmentvalue = enchantmentvalue;
        this.attackDamage = attackDamage;
        this.repairMaterial = repairMaterial;

    }
    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentvalue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }

    @Override
    public @Nullable TagKey<Block> getTag() {
        return Tier.super.getTag();
    }
}

