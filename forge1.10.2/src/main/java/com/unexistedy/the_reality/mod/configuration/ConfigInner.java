package com.unexistedy.the_reality.mod.configuration;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public class ConfigInner extends ConfigBase {
    private static String SystemLanguageCode;
    public ConfigInner(File fileIn) {
        super(fileIn);
    }
    @Override
    public void load() {
        SystemLanguageCode=config.getString("System language","System setting","en_US","You are not supposed to see this");
    }
}
