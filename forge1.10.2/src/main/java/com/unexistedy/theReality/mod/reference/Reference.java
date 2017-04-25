package com.unexistedy.theReality.mod.reference;

import com.unexistedy.theReality.mod.common.event.event.TranslatorLoadedEvent;
import com.unexistedy.theReality.mod.common.event.listener.TranslatorLoadedListener;
import com.unexistedy.theReality.mod.utility.Translator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/24.
 */
public class Reference implements TranslatorLoadedListener{
    private static Reference instance;
    public Reference(FMLPreInitializationEvent event){
        instance=this;
        SourceFile=event.getSourceFile();
        ConfigDirectory=event.getModConfigurationDirectory();
        Name=event.getModMetadata().name;
        Translator.registerListener(instance);
    }
    public static final String ID="the_reality";
    public static String Name=ID;
    public static final String ClientProxy="com.unexistedy.theReality.mod.proxy.ClientProxy";
    public static final String ServerClient="com.unexistedy.theReality.mod.proxy.ServerProxy";
    public static final String GuiFactory="com.unexistedy.theReality.mod.client.gui.GuiFactory";
    private final File SourceFile;
    private final File ConfigDirectory;
    public static final File ResourceFile=new File(Reference.class.getResource("/assets/"+ID).getFile());

    public static File getConfigDirectory() {
        return getInstance().ConfigDirectory;
    }

    public static File getSourceFile() {
        return getInstance().SourceFile;
    }

    public static Reference getInstance() {
        return instance;
    }

    @Override
    public void onTranslatorLoaded() {
        String temp=Translator.get(Keys.Name);
        if (Translator.getSTATE().isTranslated())
            Name=temp;
        else
            Name=ID;
    }
}
