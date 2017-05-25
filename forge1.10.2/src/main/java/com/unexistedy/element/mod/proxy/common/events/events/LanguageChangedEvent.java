package com.unexistedy.element.mod.proxy.common.events.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LanguageChangedEvent extends Event{
    private String currentCode;
    @SideOnly(Side.CLIENT)
    public LanguageChangedEvent(){
        currentCode= Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }
    @SideOnly(Side.SERVER)
    public LanguageChangedEvent(String codeIn){
        currentCode=codeIn;
    }

    public String getCurrentCode() {
        return currentCode;
    }
}
