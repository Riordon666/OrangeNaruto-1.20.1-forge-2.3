package net.xwj.test.moditem;

import net.xwj.test.moditem.custom.ChestDetectorItem;
import net.xwj.test.moditem.custom.MetalDetectorItem;
import net.xwj.test.moditem.custom.PlateItem;
import net.xwj.test.moditem.custom.SaladItem;
import net.xwj.test.test;
import net.xwj.test.tools.ModToolTiers;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    //注册物品
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, test.MODID);

    //创建一个物品
    public static final RegistryObject<Item> ORANGE_GEMSTONE = ITEMS.register("orange_gemstone",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RED_GEMSTONE = ITEMS.register("red_gemstone",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            ()->new MetalDetectorItem(new Item.Properties().rarity(Rarity.EPIC).durability(250)));

    public static final RegistryObject<Item> CHEST_DETECTOR = ITEMS.register("chest_detector",
            ()->new ChestDetectorItem(new Item.Properties().rarity(Rarity.EPIC).durability(100)));

    // 创建一个食物
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final RegistryObject<Item> SALAD = ITEMS.register("salad",
            ()->new SaladItem(new Item.Properties().food(ModFoods.SALAD)));

    public static final RegistryObject<Item> WOJU = ITEMS.register("woju",
            ()->new Item(new Item.Properties().food(ModFoods.WOJU)));

    public static final RegistryObject<Item> FANQIE = ITEMS.register("fanqie",
            ()->new Item(new Item.Properties().food(ModFoods.FANQIE)));

    public static final RegistryObject<Item> HUANGGUA = ITEMS.register("huanggua",
            ()->new Item(new Item.Properties().food(ModFoods.HUANGGUA)));

    public static final RegistryObject<Item> BOCAI = ITEMS.register("bocai",
            ()->new Item(new Item.Properties().food(ModFoods.BOCAI)));

    public static final RegistryObject<Item> PLATE = ITEMS.register("plate",
            ()->new PlateItem(new Item.Properties().food(ModFoods.PLATE)));


    //工具
    public static final RegistryObject<SwordItem> ORANGE_SWORD = ITEMS.register("orange_sword",
            ()-> new SwordItem(ModToolTiers.ORANGE,16, -1.8F,new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<AxeItem> ORANGE_AXE = ITEMS.register("orange_axe",
            ()-> new AxeItem(ModToolTiers.ORANGE,18, -2.8F, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ShovelItem> ORANGE_SHOVEL = ITEMS.register("orange_shovel",
            ()-> new ShovelItem(ModToolTiers.ORANGE,1, 20.0F, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<HoeItem> ORANGE_HOE = ITEMS.register("orange_hoe",
            ()-> new HoeItem(ModToolTiers.ORANGE,1, 20.0F, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<PickaxeItem> ORANGE_PICKAXE = ITEMS.register("orange_pickaxe",
            ()-> new PickaxeItem(ModToolTiers.ORANGE,2, 20.0F, new Item.Properties().rarity(Rarity.RARE)));


    //装备
    public static final RegistryObject<Item> ORANGE_HELMET = ITEMS.register("orange_helmet",
            ()-> new ArmorItem(ModAmorMaterials.ORANGE, ArmorItem.Type.HELMET,new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ORANGE_CHESTPLATE = ITEMS.register("orange_chestplate",
            ()-> new ArmorItem(ModAmorMaterials.ORANGE, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ORANGE_LEGGINGS = ITEMS.register("orange_leggings",
            ()-> new ArmorItem(ModAmorMaterials.ORANGE, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ORANGE_BOOTS = ITEMS.register("orange_boots",
            ()-> new ArmorItem(ModAmorMaterials.ORANGE, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Rarity.EPIC)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
