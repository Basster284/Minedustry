package com.basster.minedustry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;

public class mechanicaldrill extends Block {
    public static final BooleanProperty TEXTURE_FRAME = BooleanProperty.create("texture_frame");
    private static final int TICK_DELAY = 100;

    public mechanicaldrill(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(TEXTURE_FRAME, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TEXTURE_FRAME);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Vec3 center = Vec3.atCenterOf(pos);
        for (int i = 0; i < 2; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;

            level.addParticle(ParticleTypes.CLOUD,
                    center.x + offsetX,
                    pos.getY() + 0.1,
                    center.z + offsetZ,
                    0, 0.1, 0);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, state.cycle(TEXTURE_FRAME), 3);
        level.scheduleTick(pos, this, TICK_DELAY);

        BlockPos blockBelow = pos.below();
        BlockState blockStateBelow = level.getBlockState(blockBelow);

        if (!blockStateBelow.isAir()) {
            ItemStack drop = blockStateBelow.is(Blocks.COPPER_ORE) ||
                    blockStateBelow.is(Blocks.DEEPSLATE_COPPER_ORE)
                    ? new ItemStack(Items.RAW_COPPER)
                    : new ItemStack(blockStateBelow.getBlock());

            level.addFreshEntity(new ItemEntity(level,
                    pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                    drop));

            level.destroyBlock(blockBelow, false);
        }

        level.scheduleTick(pos, this, 100);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide && canStay(level, pos)) {
            level.scheduleTick(pos, this, TICK_DELAY);
        }
    }

    private boolean canStay(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(Blocks.STONE) ||
                below.is(Blocks.COPPER_ORE) ||
                below.is(Blocks.DEEPSLATE_COPPER_ORE) ||
                below.is(Blocks.DEEPSLATE) ||
                below.is(Blocks.ANDESITE) ||
                below.is(Blocks.DIORITE) ||
                below.is(Blocks.GRANITE) ||
                below.is(Blocks.SAND);
    }

}
