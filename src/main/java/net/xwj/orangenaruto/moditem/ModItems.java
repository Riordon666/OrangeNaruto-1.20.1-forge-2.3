package net.xwj.orangenaruto.moditem;

import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.entity.projectile.ExplosiveKunaiEntity;
import net.xwj.orangenaruto.moditem.custom.*;
import net.xwj.orangenaruto.moditem.weapons.ExplosiveKunaiItem;
import net.xwj.orangenaruto.moditem.weapons.KunaiItem;
import net.xwj.orangenaruto.moditem.weapons.SenbonItem;
import net.xwj.orangenaruto.moditem.weapons.ShurikenItem;
import net.xwj.orangenaruto.tools.ModToolTiers;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xwj.orangenaruto.moditem.armor.NarutoArmorItem;
import net.xwj.orangenaruto.moditem.armor.NarutoArmorMaterial;
import net.xwj.orangenaruto.moditem.weapons.ExplosiveKunaiItem;
import net.xwj.orangenaruto.moditem.weapons.KunaiItem;
import net.xwj.orangenaruto.moditem.weapons.SenbonItem;
import net.xwj.orangenaruto.moditem.weapons.ShurikenItem;
import net.xwj.orangenaruto.sounds.NarutoSounds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    //注册物品
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OrangeNaruto.MODID);

    //创建一个物品
    public static final RegistryObject<Item> ORANGE_GEMSTONE = ITEMS.register("orange_gemstone",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> RED_GEMSTONE = ITEMS.register("red_gemstone",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            ()->new MetalDetectorItem(new Item.Properties().rarity(Rarity.EPIC).durability(250)));

    public static final RegistryObject<Item> CHEST_DETECTOR = ITEMS.register("chest_detector",
            ()->new ChestDetectorItem(new Item.Properties().rarity(Rarity.EPIC).durability(100)));

    public static final RegistryObject<Item> FLYING_THUNDER = ITEMS.register("flying_thunder",
            ()->new FlyingThunderGodItem(new Item.Properties().rarity(Rarity.EPIC).durability(25)));


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


    public static final RegistryObject<Item> KUNAI = ITEMS.register("kunai",
            () -> new KunaiItem(new Item.Properties()));

    public static final RegistryObject<Item> EXPLOSIVE_KUNAI = ITEMS.register("explosive_kunai", ()
            -> new ExplosiveKunaiItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", ()
            -> new ShurikenItem(new Item.Properties()));

    public static final RegistryObject<Item> SENBON = ITEMS.register("senbon", ()
            -> new SenbonItem(new Item.Properties()));

    // Armor

    public static final RegistryObject<Item> RED_ANBU_MASK = ITEMS.register("red_anbu_mask", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.ANBU_MAT, ArmorItem.Type.HELMET, new Item.Properties())
            .setShouldHideNameplate(true));

    public static final RegistryObject<Item> YELLOW_ANBU_MASK = ITEMS.register("yellow_anbu_mask", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.ANBU_MAT, ArmorItem.Type.HELMET, new Item.Properties())
            .setShouldHideNameplate(true));

    public static final RegistryObject<Item> GREEN_ANBU_MASK = ITEMS.register("green_anbu_mask", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.ANBU_MAT, ArmorItem.Type.HELMET, new Item.Properties())
            .setShouldHideNameplate(true));

    public static final RegistryObject<Item> BLUE_ANBU_MASK = ITEMS.register("blue_anbu_mask", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.ANBU_MAT, ArmorItem.Type.HELMET, new Item.Properties())
            .setShouldHideNameplate(true));

    public static final RegistryObject<Item> MIST_ANBU_MASK = ITEMS.register("mist_anbu_mask", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.ANBU_MAT, ArmorItem.Type.HELMET, new Item.Properties())
            .setShouldHideNameplate(true));

    public static final RegistryObject<Item> FLAK_JACKET_NEW = ITEMS.register("flak_jacket_new", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.FLAK_MAT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> FLAK_JACKET = ITEMS.register("flak_jacket", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.FLAK_STRONGER_MAT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> ANBU_ARMOR = ITEMS.register("anbu_armor", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.FLAK_STRONGER_MAT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> AKATSUKI_CLOAK = ITEMS.register("akatsuki_cloak", ()
            -> new NarutoArmorItem(NarutoArmorMaterial.CHARACTER_CLOTHES, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> LONELY_MARCH = ITEMS.register("lonely_march", ()
            -> new RecordItem(41, NarutoSounds.LONELY_MARCH, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 46 * 20));

    public static final RegistryObject<Item> FABRIC = ITEMS.register("fabric", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FABRIC_REINFORCED = ITEMS.register("fabric_reinforced", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FABRIC_REINFORCED_GREEN = ITEMS.register("fabric_reinforced_green", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FABRIC_REINFORCED_BLACK = ITEMS.register("fabric_reinforced_black", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARMOR_PLATE = ITEMS.register("armor_plate", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARMOR_PLATE_GREEN = ITEMS.register("armor_plate_green", ()
            -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KATANA = ITEMS.register("katana", ()
            -> new SwordItem(NinjaTier.KATANA, 3, -1.95F, new Item.Properties()));

    // Ninja Masks

    private static RegistryObject<Item> createHeadband(String itemName) {
        return ITEMS.register(itemName, ()
                -> new NarutoArmorItem(NarutoArmorMaterial.HEADBAND, ArmorItem.Type.HELMET, new Item.Properties()));
    }

    //    BLANK(0, "blankBlueHeadBand", "headband_blank);"),
    public static final RegistryObject<Item> HEADBAND_BLUE = createHeadband("headband_blue");
    //    BLANK_BLACK(1, "blankBlackHeadBand", "headband_black);"),
    public static final RegistryObject<Item> HEADBAND_BLACK = createHeadband("headband_black");
    //    BLANK_RED(2, "blankRedHeadBand", "headband_kred);"),
    public static final RegistryObject<Item> HEADBAND_RED = createHeadband("headband_red");
    //    CUSTARD(4, "custardHeadband", "headband_custard);"),
    public static final RegistryObject<Item> HEADBAND_CUSTARD = createHeadband("headband_custard");


    public static final RegistryObject<Item> HEADBAND_LEAF = createHeadband("headband_leaf");
    public static final RegistryObject<Item> HEADBAND_LEAF_SCRATCHED = createHeadband("headband_leaf_scratched");
    public static final RegistryObject<Item> HEADBAND_LEAF_BLACK = createHeadband("headband_leaf_black");
    public static final RegistryObject<Item> HEADBAND_LEAF_BLACK_SCRATCHED = createHeadband("headband_leaf_black_scratched");

    public static final RegistryObject<Item> HEADBAND_ROCK = createHeadband("headband_rock");
    public static final RegistryObject<Item> HEADBAND_ROCK_SCRATCHED = createHeadband("headband_rock_scratched");

    public static final RegistryObject<Item> HEADBAND_SAND = createHeadband("headband_sand");
    public static final RegistryObject<Item> HEADBAND_SAND_SCRATCHED = createHeadband("headband_sand_scratched");

    public static final RegistryObject<Item> HEADBAND_SOUND = createHeadband("headband_sound");
    public static final RegistryObject<Item> HEADBAND_SOUND_SCRATCHED = createHeadband("headband_sound_scratched");

    public static final RegistryObject<Item> HEADBAND_MIST = createHeadband("headband_mist");
    public static final RegistryObject<Item> HEADBAND_MIST_SCRATCHED = createHeadband("headband_mist_scratched");

    public static final RegistryObject<Item> HEADBAND_WATERFALL = createHeadband("headband_waterfall");
    public static final RegistryObject<Item> HEADBAND_WATERFALL_SCRATCHED = createHeadband("headband_waterfall_scratched");

    public static final RegistryObject<Item> HEADBAND_CLOUD = createHeadband("headband_cloud");
    public static final RegistryObject<Item> HEADBAND_CLOUD_SCRATCHED = createHeadband("headband_cloud_scratched");

    public static final RegistryObject<Item> HEADBAND_RAIN = createHeadband("headband_rain");
    public static final RegistryObject<Item> HEADBAND_RAIN_SCRATCHED = createHeadband("headband_rain_scratched");

    public static final RegistryObject<Item> HEADBAND_GRASS = createHeadband("headband_grass");
    public static final RegistryObject<Item> HEADBAND_GRASS_SCRATCHED = createHeadband("headband_grass_scratched");


    public static final RegistryObject<Item> HEADBAND_PRIDE = createHeadband("headband_pride");
    public static final RegistryObject<Item> HEADBAND_YOUTUBE = createHeadband("headband_youtube");
    public static final RegistryObject<Item> HEADBAND_LAVA = createHeadband("headband_lava");



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
