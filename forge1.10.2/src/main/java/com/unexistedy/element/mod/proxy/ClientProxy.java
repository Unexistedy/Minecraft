package com.unexistedy.element.mod.proxy;

import com.unexistedy.element.mod.proxy.common.components.blocks.BlockHandler;
import com.unexistedy.element.mod.proxy.common.components.items.ItemHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInitialize(FMLPreInitializationEvent event) {
        super.preInitialize(event);
    }

    @Override
    public void initialize(FMLInitializationEvent event) {
        super.initialize(event);
        new ItemHandler().registerModel();
        new BlockHandler().registerModel();
    }

    @Override
    public void postInitialize(FMLPostInitializationEvent event) {
        super.postInitialize(event);
    }
}
