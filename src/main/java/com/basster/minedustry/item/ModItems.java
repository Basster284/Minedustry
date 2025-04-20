package com.basster.minedustry.item;

import com.basster.minedustry.block.ModBlocks;
import com.basster.minedustry.main;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, main.MODID);
    //blocks
    public static final RegistryObject<BlockItem> MECHANICAL_DRILL = ITEMS.register("mechanicaldrill", () -> new BlockItem(ModBlocks.MECHANICAL_DRILL.get(), new Item.Properties()));
    //ores
    public static final RegistryObject<Item> LEAD = ITEMS.register("lead", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
