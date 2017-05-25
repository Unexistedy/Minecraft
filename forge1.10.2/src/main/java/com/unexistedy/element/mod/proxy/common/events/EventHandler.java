package com.unexistedy.element.mod.proxy.common.events;

import com.unexistedy.element.mod.proxy.common.events.listener.LanguageChangedListener;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EventHandler {
    public void init(){
        load();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            loadClient();
        else
            loadServer();
    }
    private void load(){

    }
    private void loadClient(){
        new LanguageChangedListener().register();
    }
    private void loadServer(){

    }
}
