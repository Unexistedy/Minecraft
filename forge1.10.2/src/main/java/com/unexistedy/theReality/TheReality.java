package com.unexistedy.theReality;

import com.unexistedy.theReality.mod.proxy.IProxy;
import com.unexistedy.theReality.mod.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.sql.Ref;

/**
 * Created by Unexistedy on 2017/4/24.
 */
@Mod(modid = Reference.ID,useMetadata = true,guiFactory = Reference.GuiFactory)
public class TheReality {
    @Mod.Instance(Reference.ID)
    public static TheReality instance;
    @SidedProxy(modId = Reference.ID,clientSide = Reference.ClientProxy,serverSide = Reference.ServerClient)
    public static IProxy proxy;
    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event){
        new Reference(event);
        proxy.preInitialize(event);
        event.getModLog();
    }
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event){
        proxy.initialize(event);
    }
    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent event){
        proxy.postInitialize(event);
    }
}
