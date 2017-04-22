package com.unexistedy.element;

import com.unexistedy.element.mod.proxy.IProxy;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Unexistedy on 2017/4/23.
 */
@Mod(modid = Reference.ID,useMetadata = true)
public class Element {
    @Mod.Instance(Reference.ID)
    public static Element instance;
    @SidedProxy(modId = Reference.ID,clientSide = Reference.ClientProxy,serverSide = Reference.ServerProxy)
    public static IProxy proxy;
    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        proxy.preInitialize(event);
    }
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        proxy.initialize(event);
    }
    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent event) {
        proxy.postInitialize(event);
    }
}
