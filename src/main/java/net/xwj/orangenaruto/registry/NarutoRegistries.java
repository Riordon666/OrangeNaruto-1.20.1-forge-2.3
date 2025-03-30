package net.xwj.orangenaruto.registry;

import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.abilities.Ability;
import net.xwj.orangenaruto.clans.Clans;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * can be retrieved using GameRegistry.findRegistry
 * e.g. GameRegistry.findRegistry(Jutsu.class)
 */
public class NarutoRegistries {

    public static ForgeRegistry<Ability> ABILITIES;
    public static ForgeRegistry<Clans> CLANS;

    public static final ResourceLocation ABILITY_REGISTRY_LOC = new ResourceLocation(OrangeNaruto.MODID, "abilities");
    public static final ResourceLocation CLAN_REGISTRY_LOC = new ResourceLocation(OrangeNaruto.MODID, "clans");

    public static void init(NewRegistryEvent event) {
        RegistryBuilder<Ability> abilities = new RegistryBuilder<>();
        abilities.setName(ABILITY_REGISTRY_LOC);
        event.create(abilities, (registry) -> ABILITIES = (ForgeRegistry<Ability>) registry);

        RegistryBuilder<Clans> clans = new RegistryBuilder<>();
        clans.setName(CLAN_REGISTRY_LOC);
        event.create(clans, (registry) -> CLANS = (ForgeRegistry<Clans>) registry);

    }
}
