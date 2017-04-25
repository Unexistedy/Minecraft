package com.unexistedy.theReality.mod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Unexistedy on 2017/4/24.
 */
public interface IProxy {
    void preInitialize(FMLPreInitializationEvent event);

    void initialize(FMLInitializationEvent event);

    void postInitialize(FMLPostInitializationEvent event);
}
