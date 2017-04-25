package com.unexistedy.theReality.mod.common.event;

import com.unexistedy.theReality.mod.common.classes.HandlerBase;
import com.unexistedy.theReality.mod.common.event.listener.LanguageChangedListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public class EventHandler extends HandlerBase{
    public EventHandler(){
        load();
    }
    public void loadCommon(){

    }
    @SideOnly(Side.CLIENT)
    public void loadClient(){
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new LanguageChangedListener());
    }
    @SideOnly(Side.SERVER)
    public void loadServer(){

    }
}
