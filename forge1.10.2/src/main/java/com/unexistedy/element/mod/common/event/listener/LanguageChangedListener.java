package com.unexistedy.element.mod.common.event.listener;

import com.unexistedy.element.mod.common.event.event.LanguageChangedEvent;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public class LanguageChangedListener implements IResourceManagerReloadListener {
    private static String currentCode;
    public LanguageChangedListener(){
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            currentCode= Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        else currentCode= Translator.getCurrentCode();
    }
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()&&(!currentCode.equals(Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode()))){
            MinecraftForge.EVENT_BUS.post(new LanguageChangedEvent(currentCode,Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode()));
            currentCode=Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        }
    }
}
