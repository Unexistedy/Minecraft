package com.unexistedy.element.mod.reference;

import com.unexistedy.element.mod.proxy.common.events.events.TranslatorLoadedEvent;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod.EventBusSubscriber
public class Reference {
    public static final String ID="element";
    public static String Name=ID;
    public static final String ClientProxy="com.unexistedy.element.mod.proxy.ClientProxy";
    public static final String ServerProxy="com.unexistedy.element.mod.proxy.ServerProxy";
    public static final String GuiFactory="";

    public static File ConfigFolder;
    public static File SourceFolder;
    public static Logger DefaultLogger;

    public Reference(FMLPreInitializationEvent event){
        Name=event.getModMetadata().name;
        ConfigFolder=event.getModConfigurationDirectory();
        SourceFolder=event.getSourceFile();
        DefaultLogger=event.getModLog();
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTranslatorLoadedEvent(TranslatorLoadedEvent event){
        String name= Translator.get(Lang.Name);
        if (Translator.getResults().isTranslated())
            Name=name;
    }
}
