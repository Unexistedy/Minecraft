package com.unexistedy.theReality.mod.common.configuration;

import com.unexistedy.theReality.mod.common.classes.HandlerBase;
import com.unexistedy.theReality.mod.common.event.listener.TranslatorLoadedListener;
import com.unexistedy.theReality.mod.reference.Keys;
import com.unexistedy.theReality.mod.reference.Reference;
import com.unexistedy.theReality.mod.utility.LogHelper;
import com.unexistedy.theReality.mod.utility.Translator;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unexistedy on 2017/4/25.
 */
@Mod.EventBusSubscriber
public class ConfigHandler extends HandlerBase implements TranslatorLoadedListener{
    private static List<ReloadableConfig>ConfigList=null;
    private static String currentCode=null;
    private static ConfigHandler instance=null;
    private static File currentFolder=null;
    public ConfigHandler(){
        instance=this;
        ConfigList=new ArrayList<ReloadableConfig>();
        currentCode= Translator.getCurrentCode();
        currentFolder=new File(Reference.getConfigDirectory(),Translator.get(Keys.Config.ConfigFolder));
        Translator.registerListener(instance);
        load();
        setReady();
        LogHelper.info(Keys.Log.ConfigHandlerLoadedInfo);
    }
    @Override
    public void load(){
        loadCommon();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            loadClient();
        else
            loadServer();
        if (!ConfigSystem.getSystemLanguage().equals(Translator.getCurrentCode()))
            Translator.setCode(ConfigSystem.getSystemLanguage());
    }
    @Override
    public void loadCommon() {
        new ConfigSystem();
    }

    @Override
    public void loadClient() {

    }

    @Override
    public void loadServer() {

    }
    public static void register(ReloadableConfig config){
        if (ConfigList==null)
            ConfigList=new ArrayList<ReloadableConfig>();
        ConfigList.add(config);
    }

    @Override
    public void onTranslatorLoaded() {
        if (!Translator.getCurrentCode().equals(currentCode)){
            for (ReloadableConfig entry:ConfigList){
                LogHelper.info("Reloading file "+entry.configuration.getConfigFile().getName());
                entry.onTranslatorReloadedEvent();
            }
            if (!currentFolder.delete()){
                LogHelper.warn(Keys.Log.FileCanNotDeleteWarn,currentFolder.getName());
            }
            currentFolder=new File(Reference.getConfigDirectory(),Translator.get(Keys.Config.ConfigFolder));
            LogHelper.info(Keys.Log.ConfigHandlerReloadedInfo);
        }
    }
    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equalsIgnoreCase(Reference.ID)){
            for (ReloadableConfig entry:ConfigList){
                entry.onConfigchangedEvent(event);
            }
        }
    }
}
