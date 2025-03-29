package net.xwj.orangenaruto.modblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RedPortalShape {
    private static final int MIN_WIDTH = 2;
    private static final int MAX_WIDTH = 21;
    private static final int MIN_HEIGHT = 3;
    private static final int MAX_HEIGHT = 21;
    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private int numPortalBlocks;
    private BlockPos bottomLeft;
    private int height;
    private int width;

    public RedPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        this.level = level;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        this.bottomLeft = this.calculateBottomLeft(pos);
        if (this.bottomLeft == null) {
            this.bottomLeft = pos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.calculateWidth();
            if (this.width > 0) {
                this.height = this.calculateHeight();
            }
        }
    }

    @Nullable
    private BlockPos calculateBottomLeft(BlockPos pos) {
        int i = Math.max(0, pos.getY() - MAX_HEIGHT);
        int j = Math.min(pos.getY(), i + MAX_HEIGHT);

        for(int y = pos.getY(); y >= i; --y) {
            for(int x = 0; x < MAX_WIDTH; ++x) {
                BlockPos blockpos = new BlockPos(pos.getX(), y, pos.getZ());
                blockpos = blockpos.relative(this.rightDir, x);

                if (isPortalFrame(this.level.getBlockState(blockpos))) {
                    return blockpos;
                }
            }
        }
        return null;
    }

    private int calculateWidth() {
        int width = 1;  // 从1开始计数，因为已经找到了第一个方块
        BlockPos.MutableBlockPos pos = this.bottomLeft.mutable().move(this.rightDir);

        while (isPortalFrame(this.level.getBlockState(pos))) {
            ++width;
            pos.move(this.rightDir);
            if (width > MAX_WIDTH) break;
        }

        return width;
    }

    private int calculateHeight() {
        int height = 1;  // 从1开始计数，因为已经找到了第一个方块
        BlockPos.MutableBlockPos pos = this.bottomLeft.mutable().move(Direction.UP);

        while (isPortalFrame(this.level.getBlockState(pos))) {
            ++height;
            pos.move(Direction.UP);
            if (height > MAX_HEIGHT) break;
        }

        return height;
    }

    public boolean isValid() {
        // 框架的最小尺寸是3×4（内部空间1×2）
        // 框架的最大尺寸是21×21（内部空间19×19）
        return this.bottomLeft != null &&
                this.width >= 3 && this.width <= 21 &&
                this.height >= 4 && this.height <= 21;
    }

    public boolean isComplete() {
        return this.isValid() && validateFrame();
    }

    public static boolean isPortalFrame(BlockState state) {
        return state.is(ModBlocks.RED_BLOCK.get());
    }

    private boolean canConnect(BlockState state) {
        return state.isAir() || state.is(ModBlocks.RED_PORTAL.get());
    }

    public void createPortalBlocks() {
        if (!validateFrame()) return;  // 确保框架完整

        BlockState portalState = ModBlocks.RED_PORTAL.get().defaultBlockState()
                .setValue(RedPortalBlock.AXIS, this.axis);

        // 先清除可能存在的旧传送门方块
        for (int h = 1; h < this.height - 1; h++) {
            for (int w = 1; w < this.width - 1; w++) {
                BlockPos pos = this.bottomLeft.relative(this.rightDir, w).above(h);
                if (this.level.getBlockState(pos).is(ModBlocks.RED_PORTAL.get())) {
                    this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
                }
            }
        }

        // 创建新的传送门方块
        for (int h = 1; h < this.height - 1; h++) {
            for (int w = 1; w < this.width - 1; w++) {
                BlockPos pos = this.bottomLeft.relative(this.rightDir, w).above(h);
                if (this.level.getBlockState(pos).isAir()) {
                    this.level.setBlock(pos, portalState, 18);
                    ++this.numPortalBlocks;
                }
            }
        }
    }

    public boolean validateFrame() {
        if (!isValid()) return false;

        // 检查四个角
        if (!isPortalFrame(this.level.getBlockState(bottomLeft)) ||
                !isPortalFrame(this.level.getBlockState(bottomLeft.relative(this.rightDir, width - 1))) ||
                !isPortalFrame(this.level.getBlockState(bottomLeft.above(height - 1))) ||
                !isPortalFrame(this.level.getBlockState(bottomLeft.relative(this.rightDir, width - 1).above(height - 1)))) {
            return false;
        }

        // 检查底部和顶部
        for (int w = 1; w < width - 1; w++) {
            if (!isPortalFrame(this.level.getBlockState(bottomLeft.relative(this.rightDir, w))) ||
                    !isPortalFrame(this.level.getBlockState(bottomLeft.relative(this.rightDir, w).above(height - 1)))) {
                return false;
            }
        }

        // 检查左右两边
        for (int h = 1; h < height - 1; h++) {
            if (!isPortalFrame(this.level.getBlockState(bottomLeft.above(h))) ||
                    !isPortalFrame(this.level.getBlockState(bottomLeft.relative(this.rightDir, width - 1).above(h)))) {
                return false;
            }
        }

        // 检查内部空间是否为空气
        for (int h = 1; h < height - 1; h++) {
            for (int w = 1; w < width - 1; w++) {
                BlockPos pos = bottomLeft.relative(this.rightDir, w).above(h);
                BlockState state = this.level.getBlockState(pos);
                if (!state.isAir() && !state.is(ModBlocks.RED_PORTAL.get())) {
                    return false;
                }
            }
        }

        return true;
    }
}