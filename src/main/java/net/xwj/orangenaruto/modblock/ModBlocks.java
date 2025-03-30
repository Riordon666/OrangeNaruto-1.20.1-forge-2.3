package net.xwj.orangenaruto.modblock;

import net.xwj.orangenaruto.modblock.weapons.PaperBombBlock;
import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.moditem.ModItems;
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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OrangeNaruto.MODID);

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
                    .strength(2.0F, 6.0F)
                    .lightLevel(value -> 0)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            ));

    public static final RegistryObject<Block> RED_BLOCK = BLOCKS.register("red_block",
            ()->new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(4.0F, 6.0F)
                    .lightLevel(value -> 15)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            ));


    public static final RegistryObject<Block> RED_PORTAL = BLOCKS.register("red_portal",
            () -> new RedPortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .noCollission()
                    .lightLevel(value -> 15)
                    .strength(-1.0F)
                    .noLootTable()
                    .sound(SoundType.GLASS)
            ));

    //方块拿手上
    public static final RegistryObject<Item> ORANGE_GEMSTONE_BLOCK_ITEM = ModItems.ITEMS.register("orange_gemstone_block",
            () -> new BlockItem(ORANGE_GEMSTONE_BLOCK.get(), new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RED_ORE_ITEM = ModItems.ITEMS.register("red_ore",
            () -> new BlockItem(RED_ORE.get(), new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RED_BLOCK_ITEM = ModItems.ITEMS.register("red_block",
            () -> new BlockItem(RED_BLOCK.get(), new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RED_PORTAL_ITEM = ModItems.ITEMS.register("red_portal",
            () -> new BlockItem(RED_BLOCK.get(), new Item.Properties().rarity(Rarity.EPIC)));



    public static final RegistryObject<Block> PAPER_BOMB = BLOCKS.register("paper_bomb",
            () -> {
                BlockBehaviour.Properties blockBehavior = BlockBehaviour.Properties.of()
                        .noCollission()
                        .instabreak()
                        .strength(0.5F)
                        .sound(SoundType.CROP);
                return new PaperBombBlock(blockBehavior);
            });

    public static final RegistryObject<Item> ITEM_PAPER_BOMB = ModItems.ITEMS.register("paper_bomb",
            () -> new BlockItem(PAPER_BOMB.get(), new Item.Properties()));

    public static final RegistryObject<Block> BONSAI_TREE = BLOCKS.register("bonsai_tree",
            () -> new RotatableDecorativeBlock(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.CROP)
                    , Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D)));

    public static final RegistryObject<Item> ITEM_BONSAI_TREE = ModItems.ITEMS.register("bonsai_tree", ()
            -> new BlockItem(BONSAI_TREE.get(), new Item.Properties()));


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
