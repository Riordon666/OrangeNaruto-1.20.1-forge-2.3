package net.xwj.orangenaruto.modblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.xwj.orangenaruto.worldgen.dimension.ModDimensions;
import net.xwj.orangenaruto.worldgen.dimension.RedTeleporter;

public class RedPortalBlock extends NetherPortalBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public RedPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(AXIS) == Direction.Axis.Z ? Z_AABB : X_AABB;
    }

    public boolean trySpawnPortal(LevelAccessor level, BlockPos pos) {
        Size size = this.getPortalSize(level, pos);
        if (size != null && size.isValid() && size.width >= 3 && size.height >= 4) {
            size.createPortalBlocks();
            return true;
        }
        return false;
    }

    private Size getPortalSize(LevelAccessor level, BlockPos pos) {
        // 先尝试X轴方向
        Size size = new Size(level, pos, Direction.Axis.X);
        if (size != null && size.isValid() && size.width >= 3 && size.height >= 4) {
            return size;
        }

        // 再尝试Z轴方向
        Size size1 = new Size(level, pos, Direction.Axis.Z);
        if (size1 != null && size1.isValid() && size1.width >= 3 && size1.height >= 4) {
            return size1;
        }

        return null;
    }

    public static class Size {
        private final LevelAccessor level;
        private final Direction.Axis axis;
        private final Direction rightDir;
        private final Direction leftDir;
        private int numPortalBlocks;
        private BlockPos bottomLeft;
        private int height;
        private int width;

        public Size(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
            this.level = level;
            this.axis = axis;
            if (axis == Direction.Axis.X) {
                this.rightDir = Direction.EAST;
                this.leftDir = Direction.WEST;
            } else {
                this.rightDir = Direction.SOUTH;
                this.leftDir = Direction.NORTH;
            }

            this.bottomLeft = this.findBottomLeft(pos);
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

        private BlockPos findBottomLeft(BlockPos pos) {
            BlockPos.MutableBlockPos checkPos = pos.mutable();

            // 如果当前位置不是框架方块，向下找到第一个框架方块
            while (!isValidFrame(checkPos) && checkPos.getY() > level.getMinBuildHeight()) {
                checkPos.move(Direction.DOWN);
            }

            // 如果没找到框架方块，返回null
            if (!isValidFrame(checkPos)) {
                return null;
            }

            // 向下搜索到底部
            while (checkPos.getY() > level.getMinBuildHeight() && isValidFrame(checkPos.below())) {
                checkPos.move(Direction.DOWN);
            }

            // 向左搜索到最左边
            while (isValidFrame(checkPos.relative(leftDir))) {
                checkPos.move(leftDir);
            }

            return isValidFrame(checkPos) ? checkPos : null;
        }

        private int calculateWidth() {
            int width = 1;
            BlockPos.MutableBlockPos pos = this.bottomLeft.mutable().move(rightDir);

            while (isValidFrame(level.getBlockState(pos))) {
                ++width;
                pos.move(rightDir);
                if (width > 21) break;
            }

            return width;
        }

        private int calculateHeight() {
            int height = 1;
            BlockPos.MutableBlockPos pos = this.bottomLeft.mutable().move(Direction.UP);

            while (isValidFrame(level.getBlockState(pos))) {
                ++height;
                pos.move(Direction.UP);
                if (height > 21) break;
            }

            return height;
        }

        private boolean isValidFrame(BlockPos pos) {
            return level.getBlockState(pos).is(ModBlocks.RED_BLOCK.get());
        }

        private boolean isValidFrame(BlockState state) {
            return state.is(ModBlocks.RED_BLOCK.get());
        }

        public boolean isValid() {
            // 检查基本尺寸要求
            if (this.bottomLeft == null ||
                    this.width < 3 || this.width > 21 ||
                    this.height < 4 || this.height > 21) {
                return false;
            }

            // 检查四个角
            if (!isValidFrame(bottomLeft) ||
                    !isValidFrame(bottomLeft.relative(rightDir, width - 1)) ||
                    !isValidFrame(bottomLeft.above(height - 1)) ||
                    !isValidFrame(bottomLeft.relative(rightDir, width - 1).above(height - 1))) {
                return false;
            }

            // 检查边框
            for (int w = 1; w < width - 1; w++) {
                if (!isValidFrame(bottomLeft.relative(rightDir, w)) ||
                        !isValidFrame(bottomLeft.relative(rightDir, w).above(height - 1))) {
                    return false;
                }
            }

            for (int h = 1; h < height - 1; h++) {
                if (!isValidFrame(bottomLeft.above(h)) ||
                        !isValidFrame(bottomLeft.relative(rightDir, width - 1).above(h))) {
                    return false;
                }
            }

            // 检查内部空间
            for (int h = 1; h < height - 1; h++) {
                for (int w = 1; w < width - 1; w++) {
                    BlockState state = level.getBlockState(bottomLeft.relative(rightDir, w).above(h));
                    if (!state.isAir() && !state.is(ModBlocks.RED_PORTAL.get())) {
                        return false;
                    }
                }
            }

            return true;
        }

        public void createPortalBlocks() {
            BlockState portalState = ModBlocks.RED_PORTAL.get().defaultBlockState().setValue(AXIS, this.axis);

            // 先清除可能存在的旧传送门方块
            for (int h = 1; h < this.height - 1; h++) {
                for (int w = 1; w < this.width - 1; w++) {
                    BlockPos pos = this.bottomLeft.relative(rightDir, w).above(h);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(ModBlocks.RED_PORTAL.get())) {
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }

            // 创建新的传送门方块
            for (int h = 1; h < this.height - 1; h++) {
                for (int w = 1; w < this.width - 1; w++) {
                    BlockPos pos = this.bottomLeft.relative(rightDir, w).above(h);
                    if (level.getBlockState(pos).isAir()) {
                        level.setBlock(pos, portalState, Block.UPDATE_ALL);
                        ++this.numPortalBlocks;
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        Direction.Axis axis = state.getValue(AXIS);
        Size size = new Size(level, currentPos, axis);

        // 如果框架完整，保持传送门方块
        if (size != null && size.isValid()) {
            return state;
        }

        // 如果框架不完整，移除传送门方块
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) {
            return;
        }

        Direction.Axis axis = state.getValue(AXIS);
        Size size = new Size(level, pos, axis);

        if (size == null || !size.isValid()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            } else {
                Level entityWorld = entity.level();
                if (entityWorld != null) {
                    if (entityWorld.dimension() == ModDimensions.RED_DIMENSION_KEY) {
                        teleportToOverworld(entity);
                    } else {
                        teleportToDimension(entity);
                    }
                }
            }
        }
    }

    private void teleportToDimension(Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            ServerLevel destinationWorld = serverLevel.getServer().getLevel(ModDimensions.RED_DIMENSION_KEY);
            if (destinationWorld != null && !entity.isPassenger()) {
                entity.changeDimension(destinationWorld, new RedTeleporter(destinationWorld));
            }
        }
    }

    private void teleportToOverworld(Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            ServerLevel destinationWorld = serverLevel.getServer().getLevel(Level.OVERWORLD);
            if (destinationWorld != null && !entity.isPassenger()) {
                entity.changeDimension(destinationWorld, new RedTeleporter(destinationWorld));
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}