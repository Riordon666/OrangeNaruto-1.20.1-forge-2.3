package net.xwj.test.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.xwj.test.registry.ModEntities;
import net.xwj.test.capability.PlayerTeleportPosProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkHooks;

public class FlyingThunderGodEntity extends Projectile {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(FlyingThunderGodEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> DATA_IN_GROUND = SynchedEntityData.defineId(FlyingThunderGodEntity.class, EntityDataSerializers.BOOLEAN);
    private Direction stuckDirection;
    private BlockPos stuckInBlock;

    public FlyingThunderGodEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(false);
        this.setBoundingBox(new AABB(this.getX() - 0.2D, this.getY() - 0.2D, this.getZ() - 0.2D,
                this.getX() + 0.2D, this.getY() + 0.2D, this.getZ() + 0.2D));
    }

    public FlyingThunderGodEntity(Level level, LivingEntity shooter) {
        super(ModEntities.FLYING_THUNDER_GOD.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_IN_GROUND, false);
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.13F;
    }

    public void shootFromRotation(Entity entity, float xRot, float yRot, float offset, float velocity, float inaccuracy) {
        float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((xRot + offset) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        Vec3 vec3 = entity.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0.0D, vec3.z));
    }

    @Override
    public void tick() {
        super.tick();

        // 更新碰撞箱
        this.setBoundingBox(new AABB(this.getX() - 0.2D, this.getY() - 0.2D, this.getZ() - 0.2D,
                this.getX() + 0.2D, this.getY() + 0.2D, this.getZ() + 0.2D));

        if (!this.isInGround()) {
            Vec3 movement = this.getDeltaMovement();

            // 检查下一个位置是否会碰撞
            Vec3 nextPos = this.position().add(movement);
            HitResult hitResult = this.level().clip(new ClipContext(
                    this.position(),
                    nextPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            ));

            // 如果会碰撞，处理碰撞
            if (hitResult.getType() != HitResult.Type.MISS) {
                if (hitResult instanceof BlockHitResult blockHit) {
                    this.onHitBlock(blockHit);
                    return;
                }
            }

            double horizontalSpeed = movement.horizontalDistance();

            // 更新旋转
            this.setYRot((float)(Mth.atan2(movement.x, movement.z) * (180F / Math.PI)));
            this.setXRot((float)(Mth.atan2(movement.y, horizontalSpeed) * (180F / Math.PI)));

            // 保持旋转平滑
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            // 移动实体
            this.setPos(nextPos.x, nextPos.y, nextPos.z);

            // 应用重力
            if (!this.isNoGravity()) {
                Vec3 newMovement = this.getDeltaMovement();
                this.setDeltaMovement(newMovement.multiply(0.99F, 0.99F, 0.99F).add(0.0D, -0.05D, 0.0D));
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        float damage = 4.0F;

        if (target instanceof LivingEntity livingTarget) {
            damage += this.getDeltaMovement().length() * 0.5;
            livingTarget.hurt(this.damageSources().thrown(this, this.getOwner()), damage);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        this.stuckDirection = hitResult.getDirection();
        BlockPos hitPos = hitResult.getBlockPos();
        this.stuckInBlock = hitPos;

        // 设置插入状态
        this.entityData.set(DATA_IN_GROUND, true);
        this.setDeltaMovement(Vec3.ZERO);

        // 调整位置
        double x = hitPos.getX() + 0.5 + stuckDirection.getStepX() * 0.5;
        double y = hitPos.getY() + 0.5 + stuckDirection.getStepY() * 0.5;
        double z = hitPos.getZ() + 0.5 + stuckDirection.getStepZ() * 0.5;
        this.setPos(x, y, z);

        // 如果是玩家投掷的，记录传送点
        if (this.getOwner() instanceof ServerPlayer player) {
            player.getCapability(PlayerTeleportPosProvider.PLAYER_TELEPORT_POS).ifPresent(teleportPos -> {
                teleportPos.setTeleportPos(new Vec3(x, y, z));
                player.sendSystemMessage(Component.literal("传送标记已设置,5分钟内有效!"));
            });
        }

        // 根据击中方向调整旋转
        float yaw = switch(stuckDirection) {
            case NORTH -> 180;
            case SOUTH -> 0;
            case WEST -> 90;
            case EAST -> 270;
            default -> this.getYRot();
        };
        float pitch = stuckDirection == Direction.UP ? 90 : (stuckDirection == Direction.DOWN ? -90 : 0);

        this.setYRot(yaw);
        this.setXRot(pitch);
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void setItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            this.entityData.set(DATA_ITEM, stack.copy());
        }
    }

    public ItemStack getItem() {
        return this.entityData.get(DATA_ITEM);
    }

    public boolean isInGround() {
        return this.entityData.get(DATA_IN_GROUND);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ItemStack itemstack = this.getItem();
        if (!itemstack.isEmpty()) {
            tag.put("Item", itemstack.save(new CompoundTag()));
        }
        tag.putBoolean("InGround", this.isInGround());
        if (this.stuckInBlock != null) {
            tag.putInt("StuckX", this.stuckInBlock.getX());
            tag.putInt("StuckY", this.stuckInBlock.getY());
            tag.putInt("StuckZ", this.stuckInBlock.getZ());
            tag.putInt("StuckDirection", this.stuckDirection.get3DDataValue());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Item", 10)) {
            this.setItem(ItemStack.of(tag.getCompound("Item")));
        }
        this.entityData.set(DATA_IN_GROUND, tag.getBoolean("InGround"));
        if (tag.contains("StuckX")) {
            this.stuckInBlock = new BlockPos(
                    tag.getInt("StuckX"),
                    tag.getInt("StuckY"),
                    tag.getInt("StuckZ")
            );
            this.stuckDirection = Direction.from3DDataValue(tag.getInt("StuckDirection"));
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide && this.isInGround()) {
            ItemStack itemstack = this.getItem();
            if (!itemstack.isEmpty()) {
                // 增加耐久度损耗
                itemstack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(player.getUsedItemHand());
                });

                // 如果物品没有损坏，添加到玩家物品栏
                if (!itemstack.isEmpty()) {
                    if (player.getInventory().add(itemstack)) {
                        player.take(this, 1);
                        this.discard();
                    }
                } else {
                    // 如果物品损坏，直接移除实体
                    this.discard();
                }
            }
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public Direction getStuckDirection() {
        return this.stuckDirection;
    }
}
