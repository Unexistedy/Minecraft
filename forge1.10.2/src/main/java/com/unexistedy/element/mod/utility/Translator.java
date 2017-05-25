package com.unexistedy.element.mod.utility;

import com.unexistedy.element.mod.proxy.common.events.events.LanguageChangedEvent;
import com.unexistedy.element.mod.proxy.common.events.events.TranslatorLoadedEvent;
import com.unexistedy.element.mod.misc.Stage;
import com.unexistedy.element.mod.reference.Lang;
import com.unexistedy.element.mod.reference.Log;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class Translator {
    public static final String DefaultLanguage="en_US";
    private static final String FileEnd=".lang";
    private static Translator instance;
    private static Stage stage=Stage.Null;

    private Side side;
    private String CurrentLanguage;
    private Map<String,File> FileMap;
    private Map<String,String> LangMap;
    private Results Result=Results.Null;

    public static String get(String key,Object...parameters){
        getInstance().Result=Results.Translated;
        if (getInstance().side.isClient()&& I18n.hasKey(key))
            return I18n.format(key, parameters);
        else if (getInstance().side.isServer()&&getInstance().LangMap!=null&&getInstance().LangMap.containsKey(key))
            return String.format(getInstance().LangMap.get(key),parameters);
        else {
            getInstance().Result=Results.NotTranslated;
            return String.format(key, parameters);
        }
    }
    public static String[] get(String[] keys){
        List<String> values =new ArrayList<String>();
        Boolean notTranslated=true;
        for (String entry:keys){
            values.add(get(entry));
            if (getInstance().Result.isTranslated())
                notTranslated=false;
        }
        if (notTranslated)
            getInstance().Result=Results.NotTranslated;
        else
            getInstance().Result=Results.Translated;
        return values.toArray(new String[values.size()]);
    }
    public static Results getResults(){
        if (instance==null)
            return Results.Null;
        else
            return getInstance().Result;
    }
    public static Stage getStage() {
        return stage;
    }
    public static String getCurrentLanguage() {
        return getInstance().CurrentLanguage;
    }
    private static Translator getInstance() {
        if (instance==null)
            new Translator().init();
        return instance;
    }

    public Translator(){
        if (instance==null)
            instance=this;
        stage=Stage.New;
        instance.side= FMLCommonHandler.instance().getEffectiveSide();
        LogHelper.info(true,"Setting translator on "+instance.side.toString()+" side.");
    }
    public void init(){
        stage=Stage.Initializing;
        LogHelper.info(true,"Initiating translator.");
       instance.CurrentLanguage=getLanguage();
        if (!stage.equals(Stage.Error))
            load();
        else LogHelper.error(true,"Failed to set language!Unable to load translator!");
    }

    private String getLanguage() {
        LogHelper.info(true,"Setting language.");
        if (getInstance().side.isClient())
            return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        else
            return getConfigLanguage();
    }
    private void load() {
        stage=Stage.Loading;
        LogHelper.info(true,"Loading Translator.");
        if (getInstance().side!=null) {
            if (getInstance().side.isClient())
                loadClient();
            else
                loadServer();
        }
        else {
            LogHelper.error(true,"Unknown Error! Failed to load translator!");
            stage=Stage.Error;
        }
    }

    @SideOnly(Side.SERVER)
    private String getConfigLanguage() {
        getInstance().FileMap=getFileMap();
        if (!stage.equals(Stage.Error)) {
            LogHelper.info(true,"Searching config files");
            File[] files = FileHelper.list(Reference.ConfigFolder);
            if (files == null){
                LogHelper.warn(true,"No config file found!Will set to default language.");
                return DefaultLanguage;
            }
            else {
                Map<String,String> KeyMap=new HashMap<String, String>();
                Boolean injected=false;
                String code=DefaultLanguage;
                LogHelper.info(true,"Reading valid config folder.");
                for (Map.Entry<String,File> entry:getInstance().FileMap.entrySet()){
                    String value=searchValue(entry.getValue(), Lang.Config.Holder);
                    if (value!=null) {
                        KeyMap.put(value, entry.getKey());
                    }
                }
                LogHelper.info(true,"Matching config files and Language.");
                for (File entry:files){
                    if (KeyMap.containsKey(entry.getName()))
                    {
                        LogHelper.info(true,"File %s is valid.",entry.getName());
                        if (!injected){
                            injected=true;
                            code=KeyMap.get(entry.getName());
                            LogHelper.info(true,"Language confirmed.Removing extra config files.");
                        }
                        else {
                            LogHelper.info(true,"Removing file %s.",entry.getName());
                            FileHelper.delete(entry);
                        }
                    }
                    else LogHelper.info(true,"File %s is not a config folder of this mod.",entry.getName());
                }
                if (injected)
                    LogHelper.info(true,"Files removed.Language code is %s.",code);
                else
                    LogHelper.info(true,"Not valid file found. Code will be set to Default.");
                return code;
            }
        }
        else{
            LogHelper.error(true,"Unavailable language folder!");
            return DefaultLanguage;
        }
    }
    @SideOnly(Side.CLIENT)
    private void loadClient(){
        stage=Stage.Ready;
        LogHelper.info(Log.Info.TranslatorLoaded,getInstance().side.toString());
        MinecraftForge.EVENT_BUS.post(new TranslatorLoadedEvent());
    }
    @SideOnly(Side.SERVER)
    private void loadServer(){
        getInstance().LangMap=new HashMap<String, String>();
        injectLang(DefaultLanguage);
        if (!getInstance().CurrentLanguage.equals(DefaultLanguage)){
            if (!getInstance().FileMap.containsKey(getInstance().CurrentLanguage))
                getInstance().CurrentLanguage=DefaultLanguage;
            else
                injectLang(getInstance().CurrentLanguage);
        }
        if (!stage.equals(Stage.Warn))
            stage=Stage.Ready;
        if (stage.isReady())
            LogHelper.info(Log.Info.TranslatorLoaded,getInstance().side.toString());
        else
            LogHelper.warn(Log.Warn.TranslatorLoaded,getInstance().side.toString());
        MinecraftForge.EVENT_BUS.post(new TranslatorLoadedEvent());
    }

    @SideOnly(Side.SERVER)
    private Map<String,File> getFileMap() {
        LogHelper.info(true,"Searching language files.");
        Map<String,File> fileMap=new HashMap<String, File>();
        File langHolder=new File(Translator.class.getResource("/assets/"+Reference.ID+"/lang").getFile());
        File[] files=FileHelper.list(langHolder);
        if (files!=null){
            for (File processing:files){
                String code=getCode(processing);
                if (code!=null)
                    fileMap.put(code,processing);
            }
            LogHelper.info(true,"Language files loaded.");
        }
        else
        {
            LogHelper.warn(true,"No language file is found!");
        }
        return fileMap;
    }
    @SideOnly(Side.SERVER)
    private String searchValue(File file,String key){
        LogHelper.info(true,"Searching key %s in file %s.",key,file.getName());
        Map<String,String> lang=FileHelper.readMap(file);
        if (lang.containsKey(key)){
            LogHelper.info(true,"Key %s found.Value is %s.",key,lang.get(key));
            return lang.get(key);
        }
        LogHelper.info(true,"Key %s not found.",key);
        return null;
    }
    @SideOnly(Side.SERVER)
    private void injectLang(String codeIn){
        LogHelper.info(true,"Injecting language %s.",codeIn);
        if (!getInstance().FileMap.containsKey(codeIn)){
            LogHelper.warn(true,"Code unavailable.Set to default.");
            codeIn=DefaultLanguage;
        }
        if (!getInstance().FileMap.containsKey(codeIn)) {
            LogHelper.error(true,"File not found! Language map could be empty!");
            if (getInstance().LangMap==null)
                getInstance().LangMap = new HashMap<String, String>();
        }
        else {
            Map<String,String> map=FileHelper.readMap(getInstance().FileMap.get(codeIn));
            for (Map.Entry<String,String> entry:map.entrySet()){
                getInstance().LangMap.put(entry.getKey(),entry.getValue());
            }
            LogHelper.info(true,"Injection complete.");
        }
    }
    @SideOnly(Side.SERVER)
    private String getCode(File fileIn){
        Map<String,String> map=FileHelper.readMap(fileIn);
        String code;
        if (map.containsKey(Lang.CodeKey))
            code=map.get(Lang.CodeKey);
        else if (map.containsKey(Lang.NameKey))
            code=map.get(Lang.NameKey);
        else {
            String name=fileIn.getName();
            if (name.endsWith(FileEnd))
                code=name.substring(0,name.indexOf(FileEnd));
            else {
                LogHelper.warn(true,"No code found in file %s.",fileIn.getName());
                return null;
            }
        }
        LogHelper.info(true,"Code %s found in file %s.",code,fileIn.getName());
        return code;
    }

    @SubscribeEvent
    public static void onLanguageChangedEvent(LanguageChangedEvent event){
        LogHelper.info(Log.Info.TranslatorLanguageChangedEvent,event.getCurrentCode());
        getInstance().CurrentLanguage=event.getCurrentCode();
        getInstance().load();
    }

    public enum Results{
        Null,NotTranslated,Translated;
        public Boolean isTranslated(){
            return this.equals(Translated);
        }
    }
}
