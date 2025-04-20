package com.basster.minedustry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class mechanicaldrill extends Block {
    private static final int TICK_DELAY = 100;

    public mechanicaldrill(Properties properties){
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);

        BlockPos blockBelow = pos.below();
        BlockState blockStateBelow = level.getBlockState(blockBelow);
        ItemStack drop;

        if (blockStateBelow.is(Blocks.COPPER_ORE) || blockStateBelow.is(Blocks.DEEPSLATE_COPPER_ORE)) {
            drop = new ItemStack(Items.RAW_COPPER);
        } else {
            drop = new ItemStack(blockStateBelow.getBlock());
        }

        level.addFreshEntity(new ItemEntity(level,
                pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                drop));

        level.scheduleTick(pos, this, TICK_DELAY);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            if (!canStay(level, pos)) {
                level.destroyBlock(pos, false);
                return;
            }
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
