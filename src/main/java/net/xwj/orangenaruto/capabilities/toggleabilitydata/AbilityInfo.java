package net.xwj.orangenaruto.capabilities.toggleabilitydata;

import net.xwj.orangenaruto.abilities.Ability;
import net.xwj.orangenaruto.registry.NarutoRegistries;

import java.util.Objects;

/**
 * Tracks the ability and how long it has been active for (server side)
 */
public class AbilityInfo {

    public Ability ability;

    public int activeTime;

    /**
     * This stops two abilities with the same name being able to be toggled on.
     * @return
     */
    @Override
    public int hashCode() {
        var resourceKey = NarutoRegistries.ABILITIES.getResourceKey(ability);
        return resourceKey.map(abilityResourceKey -> Objects.hash(abilityResourceKey.location())).orElseGet(Objects::hash);
    }
}
