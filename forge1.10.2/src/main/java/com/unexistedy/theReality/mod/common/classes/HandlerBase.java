package com.unexistedy.theReality.mod.common.classes;

import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public abstract class HandlerBase extends Base{
    public void load(){
        loadCommon();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            loadClient();
        else
            loadServer();
    }
    public abstract void loadCommon();
    public abstract void loadClient();
    public abstract void loadServer();
}
