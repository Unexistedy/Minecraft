package com.unexistedy.element.mod.proxy.common.configuration;

public interface ReloadableConfig {
    void work();
    void preLoad();
    void load();
    void afterLoad();
    void postLoad();
    void reload();
    void save();
    void register(String category,String name,ConfigReloadable.IProperty iProperty);
    void injectValues();
}
