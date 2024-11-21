package net.xwj.test.modblock;

import net.xwj.test.moditem.ModItems;
import net.xwj.test.test;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    //注册方块
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, test.MODID);

    //方块放地上
    public static final RegistryObject<Block> ORANGE_GEMSTONE_BLOCK = BLOCKS.register("orange_gemstone_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(4.0F, 6.0F)
                    .lightLevel(value -> 15)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            ));

    public static final RegistryObject<Block> RED_ORE = BLOCKS.register("red_ore",
            ()->new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.0F, 6.0F)
                    .lightLevel(value -> 15)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            ));


    //方块拿手上
    public static final RegistryObject<Item> ORANGE_GEMSTONE_BLOCK_ITEM = ModItems.ITEMS.register("orange_gemstone_block",
            () -> new BlockItem(ORANGE_GEMSTONE_BLOCK.get(), new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RED_ORE_ITEM = ModItems.ITEMS.register("red_ore",
            () -> new BlockItem(RED_ORE.get(), new Item.Properties().rarity(Rarity.EPIC)));



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
