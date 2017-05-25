package com.unexistedy.element.mod.misc;

public enum Stage {
    Null,New,Initializing,Loading,Ready,Error,Warn;
    public Boolean isReady(){
        return this.equals(Ready);
    }
}
