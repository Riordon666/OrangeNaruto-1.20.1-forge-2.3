package net.xwj.orangenaruto.entity.projectile;

import net.xwj.orangenaruto.entity.NarutoEntities;
import net.xwj.orangenaruto.entity.projectile.ExplosiveKunaiEntity;
import net.xwj.orangenaruto.entity.projectile.NoArrowAbstractArrow;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.xwj.orangenaruto.moditem.ModItems;
import net.xwj.orangenaruto.sounds.NarutoSounds;


public class KunaiEntity extends NoArrowAbstractArrow{
    public KunaiEntity(EntityType<? extends KunaiEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public KunaiEntity(Level worldIn, LivingEntity shooter) {
        super(NarutoEntities.KUNAI.get(), shooter, worldIn);
    }

    public KunaiEntity(EntityType<? extends KunaiEntity> kunaiEntityEntityType, LivingEntity shooter, Level worldIn) {
        super(kunaiEntityEntityType, shooter, worldIn);
    }

    public KunaiEntity(Level level, double x, double y, double z) {
        super(NarutoEntities.KUNAI.get(), x, y, z, level);
    }

    public KunaiEntity(EntityType<ExplosiveKunaiEntity> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return NarutoSounds.KUNAI_THUD.get();
    }

    @Override
    public void setSoundEvent(SoundEvent pSound) {
        super.setSoundEvent(this.getDefaultHitGroundSoundEvent());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.KUNAI.get());
    }
}