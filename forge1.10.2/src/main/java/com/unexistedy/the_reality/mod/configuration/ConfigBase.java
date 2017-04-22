package com.unexistedy.the_reality.mod.configuration;

import com.unexistedy.the_reality.mod.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public abstract class ConfigBase {
    public Configuration config;
    public ConfigBase(File fileIn){
        if (this.config==null){
            config=new Configuration(fileIn);
        }
        config.load();
        load();
        if (config.hasChanged())
            config.save();
        ConfigHandler.register(this);
    }
    public abstract void load();
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equalsIgnoreCase(Reference.ID)){
            load();
            config.save();
        }
    }
}
