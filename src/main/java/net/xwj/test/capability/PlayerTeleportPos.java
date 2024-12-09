package net.xwj.test.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class PlayerTeleportPos {
    private Vec3 teleportPos;
    private long timeSet;

    public void setTeleportPos(Vec3 pos) {
        this.teleportPos = pos;
        this.timeSet = System.currentTimeMillis();
    }

    public Vec3 getTeleportPos() {
        return teleportPos;
    }

    public long getTimeSet() {
        return timeSet;
    }

    public boolean hasValidTeleportPos() {
        return teleportPos != null;
    }

    public void clearTeleportPos() {
        this.teleportPos = null;
        this.timeSet = 0;
    }

    public void saveNBTData(CompoundTag nbt) {
        if (teleportPos != null) {
            nbt.putDouble("teleportX", teleportPos.x);
            nbt.putDouble("teleportY", teleportPos.y);
            nbt.putDouble("teleportZ", teleportPos.z);
            nbt.putLong("timeSet", timeSet);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        if (nbt.contains("teleportX")) {
            this.teleportPos = new Vec3(
                    nbt.getDouble("teleportX"),
                    nbt.getDouble("teleportY"),
                    nbt.getDouble("teleportZ")
            );
            this.timeSet = nbt.getLong("timeSet");
        }
    }
}
