package com.unexistedy.element.mod.reference;

import com.unexistedy.element.mod.common.event.listener.TranslatorLoadedListener;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Reference implements TranslatorLoadedListener {
    private static Reference instance;
    public Reference(FMLPreInitializationEvent event){
        instance=this;
        SourceFile=event.getSourceFile();
        ConfigDirectory=event.getModConfigurationDirectory();
        Name=event.getModMetadata().name;
        Translator.registerListener(instance);
    }
    public static final String ID="element";
    public static String Name=ID;
    public static final String ClientProxy="com.unexistedy.element.mod.proxy.ClientProxy";
    public static final String ServerClient="com.unexistedy.element.mod.proxy.ServerProxy";
    public static final String GuiFactory="com.unexistedy.element.mod.client.gui.GuiFactory";
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
