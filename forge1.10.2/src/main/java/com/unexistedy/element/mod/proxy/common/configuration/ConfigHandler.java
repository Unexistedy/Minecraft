package com.unexistedy.element.mod.proxy.common.configuration;

import com.unexistedy.element.mod.proxy.common.events.events.TranslatorLoadedEvent;
import com.unexistedy.element.mod.misc.Stage;
import com.unexistedy.element.mod.reference.Lang;
import com.unexistedy.element.mod.reference.Log;
import com.unexistedy.element.mod.reference.Reference;
import com.unexistedy.element.mod.utility.FileHelper;
import com.unexistedy.element.mod.utility.LogHelper;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ConfigHandler {
    static List<ReloadableConfig> ConfigList;
    static File ConfigHolder;
    static Stage stage=Stage.New;
    public ConfigHandler(){
        ConfigHolder=new File(Reference.ConfigFolder, Translator.get(Lang.Config.Holder));
        if (ConfigList==null)
            ConfigList=new ArrayList<ReloadableConfig>();
        stage=Stage.Ready;
    }

    public void init(){
        load();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            loadClient();
        else
            loadServer();
    }
    private void load(){

    }
    private void loadClient(){

    }
    private void loadServer(){
        new ConfigSystem(Lang.Config.System.File);
    }

    static void register(ReloadableConfig config){
        if (ConfigList==null)
            ConfigList=new ArrayList<ReloadableConfig>();
        if (!ConfigList.contains(config))
            ConfigList.add(config);
    }

    @SubscribeEvent
    public static void onTranslatorLoadedEvent(TranslatorLoadedEvent event){
        if (stage.isReady()&&ConfigList!=null&&ConfigList.size()>=1)
        {
            LogHelper.info(Log.Info.ConfigHandlerReloading);
            FileHelper.delete(ConfigHolder);
            ConfigHolder=new File(Reference.ConfigFolder, Translator.get(Lang.Config.Holder));
            for (ReloadableConfig entry:ConfigList){
                entry.reload();
            }
            LogHelper.info(Log.Info.ConfigHandlerReloaded);
        }
    }
    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equalsIgnoreCase(Reference.ID)&&ConfigList!=null){
            for (ReloadableConfig entry:ConfigList){
                entry.save();
            }
        }
    }
}
