package com.unexistedy.element.mod.common.event.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public class TranslatorLoadedEvent extends Event {
    private Boolean isReload;
    private Boolean hasChanged;

    public Boolean isReload() {
        return isReload;
    }

    public Boolean hasChanged() {
        return hasChanged;
    }

    public TranslatorLoadedEvent(Boolean isReload,Boolean hasChanged){
        this.isReload=isReload;
        this.hasChanged=hasChanged;
    }
}
