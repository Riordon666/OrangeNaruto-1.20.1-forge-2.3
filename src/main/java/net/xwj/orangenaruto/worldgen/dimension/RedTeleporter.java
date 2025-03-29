package net.xwj.orangenaruto.worldgen.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.xwj.orangenaruto.modblock.ModBlocks;
import net.xwj.orangenaruto.modblock.RedPortalBlock;

import java.util.Optional;
import java.util.function.Function;

public class RedTeleporter implements ITeleporter {
    private final ServerLevel level;
    private static final int SEARCH_RADIUS = 16;

    public RedTeleporter(ServerLevel level) {
        this.level = level;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        return entity;
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        BlockPos entityPos = entity.blockPosition();

        // 先尝试找到附近的传送门
        Optional<BlockPos> existingPortal = findNearbyPortal(destWorld, entityPos);
        if (existingPortal.isPresent()) {
            BlockPos portalPos = existingPortal.get();
            // 计算传送门前方2格的位置
            float playerYaw = entity.getYRot();
            Direction playerFacing = Direction.fromYRot(playerYaw);
            BlockPos inFrontOfPortal = portalPos.relative(playerFacing, 2);
            return new PortalInfo(new Vec3(inFrontOfPortal.getX() + 0.5, inFrontOfPortal.getY(), inFrontOfPortal.getZ() + 0.5),
                    Vec3.ZERO, playerYaw, entity.getXRot());
        }

        // 如果找不到现有传送门，在玩家背后创建一个新的
        float playerYaw = entity.getYRot();
        Direction playerFacing = Direction.fromYRot(playerYaw);
        Direction opposite = playerFacing.getOpposite();

        // 在玩家背后2格的位置
        BlockPos behindPos = entityPos.relative(opposite, 2);
        BlockPos safePos = findSafePosition(destWorld, behindPos);
        createPortal(destWorld, safePos, playerFacing.getAxis());

        // 返回玩家面向传送门的位置，并在传送门前方2格
        BlockPos inFrontOfPortal = safePos.relative(playerFacing, 2);
        return new PortalInfo(new Vec3(inFrontOfPortal.getX() + 0.5, inFrontOfPortal.getY(), inFrontOfPortal.getZ() + 0.5),
                Vec3.ZERO, playerYaw + 180, entity.getXRot());
    }

    private Optional<BlockPos> findNearbyPortal(ServerLevel world, BlockPos center) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        // 在较小范围内搜索传送门
        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++) {
            for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {
                mutable.set(center.getX() + x, center.getY(), center.getZ() + z);

                // 在垂直方向上下16格搜索
                for (int y = -16; y <= 16; y++) {
                    mutable.setY(center.getY() + y);
                    if (world.getBlockState(mutable).is(ModBlocks.RED_PORTAL.get())) {
                        return Optional.of(mutable.immutable());
                    }
                }
            }
        }
        return Optional.empty();
    }

    private BlockPos findSafePosition(ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(pos.getX(),
                Math.min(world.getMaxBuildHeight() - 10, Math.max(pos.getY(), world.getMinBuildHeight() + 10)),
                pos.getZ());

        // 向下寻找安全的位置
        while (mutable.getY() > world.getMinBuildHeight()) {
            BlockState stateBelow = world.getBlockState(mutable.below());
            BlockState stateAt = world.getBlockState(mutable);
            if (stateBelow.isSolid() && stateAt.isAir()) {
                return mutable.immutable();
            }
            mutable.move(Direction.DOWN);
        }

        return world.getSharedSpawnPos().above();
    }

    private void createPortal(ServerLevel world, BlockPos pos, Direction.Axis axis) {
        BlockPos framePos = pos.above();

        // 根据玩家朝向调整传送门方向
        int xOffset = axis == Direction.Axis.X ? 0 : -2;
        int zOffset = axis == Direction.Axis.Z ? 0 : -2;

        // 创建框架 (4×5)
        for (int y = 0; y < 5; y++) {
            for (int i = -2; i <= 1; i++) {
                BlockPos frameBlockPos = framePos.offset(
                        axis == Direction.Axis.X ? i : xOffset,
                        y,
                        axis == Direction.Axis.Z ? i : zOffset
                );
                if (i == -2 || i == 1 || y == 0 || y == 4) {
                    world.setBlock(frameBlockPos, ModBlocks.RED_BLOCK.get().defaultBlockState(), 3);
                }
            }
        }

        // 创建传送门方块
        BlockState portalState = ModBlocks.RED_PORTAL.get().defaultBlockState()
                .setValue(RedPortalBlock.AXIS, axis);
        for (int y = 1; y < 4; y++) {
            for (int i = -1; i < 1; i++) {
                BlockPos portalPos = framePos.offset(
                        axis == Direction.Axis.X ? i : xOffset,
                        y,
                        axis == Direction.Axis.Z ? i : zOffset
                );
                world.setBlock(portalPos, portalState, 3);
            }
        }
    }
}