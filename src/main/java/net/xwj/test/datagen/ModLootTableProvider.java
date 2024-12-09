package net.xwj.test.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.xwj.test.moditem.ModItems;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModChestLootTables::new, LootContextParamSets.CHEST)));
    }

    private static class ModChestLootTables implements LootTableSubProvider {
        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            ResourceLocation[] chestTypes = {
                    // 村庄箱子
                    BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
                    BuiltInLootTables.VILLAGE_DESERT_HOUSE,
                    BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
                    BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
                    BuiltInLootTables.VILLAGE_TAIGA_HOUSE,

                    // 地牢和矿井
                    BuiltInLootTables.SIMPLE_DUNGEON,
                    BuiltInLootTables.ABANDONED_MINESHAFT,

                    // 水下结构
                    BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                    BuiltInLootTables.UNDERWATER_RUIN_BIG,
                    BuiltInLootTables.SHIPWRECK_TREASURE,
                    BuiltInLootTables.SHIPWRECK_SUPPLY,

                    // 特殊建筑
                    BuiltInLootTables.DESERT_PYRAMID,
                    BuiltInLootTables.JUNGLE_TEMPLE,
                    BuiltInLootTables.WOODLAND_MANSION,
                    BuiltInLootTables.STRONGHOLD_LIBRARY,
                    BuiltInLootTables.STRONGHOLD_CROSSING,
                    BuiltInLootTables.IGLOO_CHEST,

                    // 末地和下界
                    BuiltInLootTables.END_CITY_TREASURE,
                    BuiltInLootTables.BASTION_TREASURE,

                    // 其他
                    BuiltInLootTables.PILLAGER_OUTPOST
            };

            for (ResourceLocation chestType : chestTypes) {
                LootTable.Builder lootTableBuilder = LootTable.lootTable()
                        .withPool(createLootPool(ModItems.SALAD.get(), 0.1f, 1, 2))
                        .withPool(createLootPool(ModItems.FANQIE.get(), 0.25f, 1, 4))
                        .withPool(createLootPool(ModItems.WOJU.get(), 0.25f, 1, 4))
                        .withPool(createLootPool(ModItems.HUANGGUA.get(), 0.25f, 1, 4))
                        .withPool(createLootPool(ModItems.BOCAI.get(), 0.25f, 1, 4))
                        .withPool(createLootPool(ModItems.PLATE.get(), 0.3f, 1, 3))
                        .withPool(createLootPool(ModItems.RED_GEMSTONE.get(), 0.2f, 1, 4))
                        .withPool(createLootPool(ModItems.ORANGE_GEMSTONE.get(), 0.05f, 1, 1))
                        .withPool(createLootPool(ModItems.METAL_DETECTOR.get(), 0.1f, 1, 1))
                        .withPool(createLootPool(ModItems.CHEST_DETECTOR.get(), 0.1f, 1, 1))
                        .withPool(createLootPool(ModItems.FLYING_THUNDER.get(), 0.02f, 1, 1));

                consumer.accept(chestType, lootTableBuilder);
            }
        }

        private LootPool.Builder createLootPool(Item item, float chance, int minCount, int maxCount) {
            return LootPool.lootPool()
                    .add(LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                            .when(LootItemRandomChanceCondition.randomChance(chance)));
        }
    }
}
