package com.unexistedy.the_reality.mod.configuration;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public class ConfigHandler {
    private static File Direction;
    private static List<ConfigBase> ConfigHolder;
    public ConfigHandler(FMLPreInitializationEvent event){
        Direction= event.getModConfigurationDirectory();
        if (ConfigHolder==null||ConfigHolder.size()<1)
            ConfigHolder=new ArrayList<ConfigBase>();
    }
    public static void register(ConfigBase config){
        if (!ConfigHolder.contains(config))
            ConfigHolder.add(config);
    }
}
