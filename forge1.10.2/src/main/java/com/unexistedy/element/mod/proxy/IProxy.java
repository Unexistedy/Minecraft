package com.unexistedy.element.mod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
    void preInitialize(FMLPreInitializationEvent event);

    void initialize(FMLInitializationEvent event);

    void postInitialize(FMLPostInitializationEvent event);
}
