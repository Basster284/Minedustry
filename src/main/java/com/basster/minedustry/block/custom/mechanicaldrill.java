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
    private static final int TICK_DELAY = 100;
    private static final Block[] MINABLE_BLOCKS = {
            Blocks.STONE,
            Blocks.DEEPSLATE,
            Blocks.GRANITE,
            Blocks.DIORITE,
            Blocks.ANDESITE,
            Blocks.SAND,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE
    };

    public mechanicaldrill(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, TICK_DELAY);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos blockBelow = pos.below();
        BlockState blockBelowState = level.getBlockState(blockBelow);

        if (!canMine(blockBelowState)) {
            level.scheduleTick(pos, this, TICK_DELAY);
            return;
        }

        ItemStack drop = getDropForBlock(blockBelowState);
        if (!drop.isEmpty()) {
            spawnItem(level, pos, drop);
            spawnParticles(level, pos, random);
        }

        level.scheduleTick(pos, this, TICK_DELAY);
    }

    private boolean canMine(BlockState state) {
        for (Block block : MINABLE_BLOCKS) {
            if (state.is(block)) {
                return true;
            }
        }
        return false;
    }

    private ItemStack getDropForBlock(BlockState state) {
        if (state.is(Blocks.COPPER_ORE) || state.is(Blocks.DEEPSLATE_COPPER_ORE)) {
            return new ItemStack(Items.RAW_COPPER);
        }
        return new ItemStack(state.getBlock().asItem());
    }

    private void spawnItem(ServerLevel level, BlockPos pos, ItemStack stack) {
        level.addFreshEntity(new ItemEntity(
                level,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                stack
        ));
    }

    private void spawnParticles(ServerLevel level, BlockPos pos, RandomSource random) {
        // Основные частицы (как у смерти моба)
        for (int i = 0; i < 5; i++) {
            level.sendParticles(
                    ParticleTypes.CLOUD, // Тип частиц (бело-голубые)
                    pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5,
                    pos.getY() + 0.2,
                    pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5,
                    1,                               // Количество
                    0.0, 0.0, 0.0,                   // Смещение
                    0.15 + random.nextDouble() * 0.1 // Скорость вверх (как у мобов)
            );
        }

        // Дополнительные лёгкие частицы (для объёма)
        for (int i = 0; i < 3; i++) {
            level.sendParticles(
                    ParticleTypes.WHITE_ASH,
                    pos.getX() + 0.5,
                    pos.getY() + 0.2,
                    pos.getZ() + 0.5,
                    1,
                    0.1, 0.0, 0.1,
                    0.08
            );
        }
    }

}
