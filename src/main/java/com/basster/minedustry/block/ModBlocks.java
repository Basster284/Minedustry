package com.basster.minedustry.block;

import com.basster.minedustry.block.custom.mechanicaldrill;
import com.basster.minedustry.main;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, main.MODID);
    public static final RegistryObject<Block> MECHANICAL_DRILL = BLOCKS.register("mechanicaldrill", () -> new mechanicaldrill(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
