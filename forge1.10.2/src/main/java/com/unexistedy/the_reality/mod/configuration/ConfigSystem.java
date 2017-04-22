package com.unexistedy.the_reality.mod.configuration;

import org.apache.logging.log4j.Level;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public class ConfigSystem extends ConfigBase implements ConfigReloadable {
    private static String SystemLanguage="";
    private static Boolean ShowTrace=false;
    private static Level LogLevel=Level.ALL;
    public ConfigSystem(File fileIn) {
        super(fileIn);
    }
    @Override
    public void reload(File fileIn) {

    }

    @Override
    public void load() {
    }
}
