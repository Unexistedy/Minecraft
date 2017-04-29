package com.unexistedy.element.mod.components.items;

import com.unexistedy.element.mod.common.classes.HandlerBase;
import com.unexistedy.element.mod.components.CreativeTab;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Unexistedy on 2017/4/26.
 */
@GameRegistry.ObjectHolder(Reference.ID)
@Mod.EventBusSubscriber
public class ItemHandler extends HandlerBase {
    public ItemHandler(){
        if (!CreativeTab.getInstance().isReady())
            new CreativeTab();
        load();
        setReady();
    }
    @Override
    public void loadCommon() {

    }

    @Override
    public void loadClient() {

    }

    @Override
    public void loadServer() {

    }
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event){
        new ItemHandler();
    }
}
