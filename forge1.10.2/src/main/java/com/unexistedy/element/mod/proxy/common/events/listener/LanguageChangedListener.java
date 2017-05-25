package com.unexistedy.element.mod.proxy.common.events.listener;

import com.unexistedy.element.mod.proxy.common.events.events.LanguageChangedEvent;
import com.unexistedy.element.mod.reference.Log;
import com.unexistedy.element.mod.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LanguageChangedListener implements IResourceManagerReloadListener,IRegisterableListener{
    @SideOnly(Side.CLIENT)
    private static String CurrentCode;
    @SideOnly(Side.CLIENT)
    public LanguageChangedListener(){
        CurrentCode=Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void onResourceManagerReload(IResourceManager resourceManager) {
        String code=Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        if (!CurrentCode.equals(code)) {
            LogHelper.info(Log.Info.ListenerLanguageChanged);
            MinecraftForge.EVENT_BUS.post(new LanguageChangedEvent());
            CurrentCode=code;
        }
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void register() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
    }
}
