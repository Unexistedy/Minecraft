package com.unexistedy.element.mod.proxy.common.configuration;

import com.unexistedy.element.mod.proxy.common.events.events.LanguageChangedEvent;
import com.unexistedy.element.mod.proxy.common.events.events.TranslatorLoadedEvent;
import com.unexistedy.element.mod.reference.Lang;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ConfigSystem extends ConfigReloadable {
    private static String setCode=null;
    public ConfigSystem(String keyIn) {
        super(keyIn);
    }

    @Override
    public void load() {
        setCode=getString(setCode, Lang.Config.System.Name.Language,Lang.Config.System.Category.Language, Translator.DefaultLanguage,Lang.Config.System.Comment.Language);
    }

    @Override
    public void afterLoad() {
        if (!setCode.equals(Translator.getCurrentLanguage()))
            MinecraftForge.EVENT_BUS.post(new LanguageChangedEvent(setCode));
    }
    @SubscribeEvent
    public static void onTranslatorLoadedEvent(TranslatorLoadedEvent event){
        if (setCode!=null)
            setCode=Translator.getCurrentLanguage();
    }
}
