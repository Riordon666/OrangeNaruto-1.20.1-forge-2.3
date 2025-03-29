package net.xwj.orangenaruto.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTeleportPosProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    public static Capability<PlayerTeleportPos> PLAYER_TELEPORT_POS =
            CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerTeleportPos teleportPos = null;
    private final LazyOptional<PlayerTeleportPos> optional = LazyOptional.of(this::createPlayerTeleportPos);

    private PlayerTeleportPos createPlayerTeleportPos() {
        if(this.teleportPos == null) {
            this.teleportPos = new PlayerTeleportPos();
        }
        return this.teleportPos;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_TELEPORT_POS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerTeleportPos().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerTeleportPos().loadNBTData(nbt);
    }
}
