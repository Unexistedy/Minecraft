package com.unexistedy.theReality.mod.common.configuration;

import com.unexistedy.theReality.mod.reference.Keys;
import com.unexistedy.theReality.mod.utility.Translator;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Unexistedy on 2017/4/26.
 */
public class ConfigSystem extends ReloadableConfig {
    private static String systemLanguage;

    public ConfigSystem() {
        super(Keys.Config.ConfigSystem.File);
    }

    @Override
    public void load() {

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            systemLanguage= Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        else {
            systemLanguage = getString(systemLanguage, Keys.Config.ConfigSystem.SystemLanguage.name, Keys.Config.ConfigSystem.SystemLanguage.category, Translator.DefaultCode, Translator.get(Keys.Config.ConfigSystem.SystemLanguage.comment));
        }
    }
    public static String getSystemLanguage(){
        return systemLanguage;
    }
}
