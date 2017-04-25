package com.unexistedy.theReality.mod.common.event.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public class LanguageChangedEvent extends Event {
    private String previousCode;
    private String currentCode;
    public LanguageChangedEvent(String previousCode,String currentCode){
        this.currentCode=currentCode;
        this.previousCode=previousCode;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public String getPreviousCode() {
        return previousCode;
    }
    public Boolean hasChanged(){
        return !previousCode.equals(currentCode);
    }
}
