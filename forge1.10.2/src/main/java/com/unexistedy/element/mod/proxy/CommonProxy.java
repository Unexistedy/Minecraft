package com.unexistedy.element.mod.proxy;

import com.unexistedy.element.mod.common.configuration.ConfigHandler;
import com.unexistedy.element.mod.common.event.EventHandler;
import com.unexistedy.element.mod.utility.LogHelper;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Unexistedy on 2017/4/24.
 */
public class CommonProxy implements IProxy {
    @Override
    public void preInitialize(FMLPreInitializationEvent event) {
        new Translator();
        new LogHelper();
        new ConfigHandler();
    }

    @Override
    public void initialize(FMLInitializationEvent event) {
        new EventHandler();
    }

    @Override
    public void postInitialize(FMLPostInitializationEvent event) {

    }
}
