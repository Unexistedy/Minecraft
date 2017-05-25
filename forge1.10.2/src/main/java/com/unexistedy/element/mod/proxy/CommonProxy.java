package com.unexistedy.element.mod.proxy;

import com.unexistedy.element.mod.proxy.common.configuration.ConfigHandler;
import com.unexistedy.element.mod.proxy.common.events.EventHandler;
import com.unexistedy.element.mod.utility.LogHelper;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {
    @Override
    public void preInitialize(FMLPreInitializationEvent event) {
        new LogHelper();
        new Translator().init();
        new ConfigHandler().init();
    }

    @Override
    public void initialize(FMLInitializationEvent event) {
        new EventHandler().init();
    }

    @Override
    public void postInitialize(FMLPostInitializationEvent event) {

    }
}
