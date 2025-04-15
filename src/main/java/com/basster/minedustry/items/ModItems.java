package com.basster.minedustry.items;

import com.basster.minedustry.main;
import com.google.common.eventbus.EventBus;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.w3c.dom.events.Event;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, main.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
