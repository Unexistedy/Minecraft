package com.unexistedy.theReality.mod.utility;

import com.unexistedy.theReality.mod.common.classes.HandlerBase;
import com.unexistedy.theReality.mod.common.event.event.LanguageChangedEvent;
import com.unexistedy.theReality.mod.common.event.listener.TranslatorLoadedListener;
import com.unexistedy.theReality.mod.reference.Keys;
import com.unexistedy.theReality.mod.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Unexistedy on 2017/4/25.
 */
@Mod.EventBusSubscriber
public class Translator extends HandlerBase {
    public static String get(String key,Object...parameters){
    if (key != null){
        if (instance!=null&&instance.isReady()) {
            STATE = State.Translated;
            if (FMLCommonHandler.instance().getEffectiveSide().isClient() && I18n.hasKey(key))
                return I18n.format(key, parameters);
            else if (LanguageMap.containsKey(key))
                return String.format(LanguageMap.get(key), parameters);
            STATE = State.NotTranslated;
            return String.format(key, parameters);
        } else {
            STATE = State.Default;
            return String.format(key, parameters);
        }
    }
    else{
        STATE = State.Error;
        return "Null";
    }
}
    public static String[] get(String[] keys){
        Boolean allTranslated=true;
        Boolean allIgnored=true;
        Boolean error=false;
        List<String> results=new ArrayList<String>();
        for (String entry:keys){
            results.add(get(entry));
            if (STATE.equals(State.Translated))
                allIgnored=false;
            else {
                allTranslated = false;
                if (STATE.equals(State.Error))
                    error=true;
            }
        }
        if (error)
            STATE=State.Error;
        else if (!instance.isReady())
            STATE=State.Default;
        else if (allTranslated)
            STATE=State.Translated;
        else if (allIgnored)
            STATE=State.NotTranslated;
        else
            STATE=State.PartlyTranslated;
        return (String[])results.toArray();
    }
    public static String getName(){
        return get(Keys.Name);
    }
    public static String getCurrentCode(){
        if (instance!=null)
            return instance.currentCode;
        else
            return null;
    }
    public static void setCode(String codeIn){
        if (instance==null)
            new Translator();
        if (codeIn!=null) {
            instance.currentCode = codeIn;
            instance.load();
        }
    }
    public static State getSTATE() {
        return STATE;
    }
    public static Translator getInstance() {
        return instance!=null?instance:(new Translator()).instance;
    }

    private static Translator instance=null;
    private static Map<String,File> FileMap;
    private static Map<String,String> LanguageMap;
    private static State STATE=State.Default;
    private String currentCode;
    public static final String DefaultCode="en_US";
    public Translator(){
        instance=this;
        if (ListenerList==null)
            ListenerList=new ArrayList<TranslatorLoadedListener>();
        LanguageMap=new HashMap<String, String>();
        FileMap=new HashMap<String, File>();
        STATE=State.Default;
        loadFiles();
        currentCode=getCode();
        load();
    }

    private void loadFiles() {
        File langFile=new File(Reference.ResourceFile,"lang");
        if (langFile.exists()){
            File[] files=langFile.listFiles();
            for (File entry:files){
                if (!entry.isDirectory()) {
                    String code = searchFile(entry, Keys.Translator.LanguageCode);
                    if (code == null) {
                        String name = entry.getName();
                        if (name.contains("."))
                            name = name.substring(0, name.indexOf("."));
                        code = name;
                    }
                    if (FileMap.containsKey(code))
                        LogHelper.warn(Keys.Log.LangFileExistWarn,code);
                    FileMap.put(code, entry);
                }
            }
        }
    }
    private String getCode(){
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        else{
            String code=DefaultCode;
            String folder;
            Boolean ready=false;
            File configFolder;
            for (Map.Entry<String,File> entry:FileMap.entrySet()){
                folder=searchFile(entry.getValue(),Keys.Config.ConfigFolder);
                if (folder!=null){
                    configFolder=new File(Reference.getConfigDirectory(),folder);
                    if (configFolder.isDirectory()&&configFolder.exists()){
                        if (!ready){
                            ready=true;
                            code=entry.getKey();
                        }
                        else
                        {
                            if (!configFolder.delete())
                                LogHelper.warn(Keys.Log.FileCanNotDeleteWarn,configFolder.getName());
                        }
                    }
                }
            }
            return code;
        }
    }
    @Override
    public void load(){
        Boolean isReload=isReady();
        loadCommon();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            loadClient();
        else
            loadServer();
        for (TranslatorLoadedListener entry:ListenerList){
            entry.onTranslatorLoaded();
        }
        setReady();
        if (!isReload)
            LogHelper.info(Keys.Log.TranslatorLoadedInfo,FMLCommonHandler.instance().getEffectiveSide().toString());
        else
            LogHelper.info(Keys.Log.TranslatorReloadedInfo);

    }

    private String searchFile(File entry, String languageCode) {
        try {
            BufferedReader reader=new BufferedReader(new FileReader(entry));
            String KEY;
            String VALUE;
            for (String textLine=reader.readLine();textLine!=null;textLine=reader.readLine()){
                KEY=getKey(textLine);
                VALUE=getValue(textLine);
                if (KEY!=null&&KEY.equals(languageCode))
                    return VALUE;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void loadCommon() {
        if (!FileMap.containsKey(currentCode))
            currentCode=DefaultCode;
    }
    @Override
    public void loadClient() {

    }
    @Override
    public void loadServer() {
        Map<String,String> tempMap=new HashMap<String, String>();
        if (FileMap.containsKey(DefaultCode))
            tempMap=readMap(FileMap.get(DefaultCode));
        if ((!currentCode.equals(DefaultCode))&&FileMap.containsKey(currentCode))
            tempMap=replaceMap(tempMap,readMap(FileMap.get(currentCode)));
        LanguageMap=tempMap;
    }

    private Map<String,String> replaceMap(Map<String, String> tempMap, Map<String, String> map) {
        for (Map.Entry<String,String> entry:map.entrySet()){
            tempMap.put(entry.getKey(),entry.getValue());
        }
        return tempMap;
    }
    private Map<String,String> readMap(File file) {
        Map<String,String> tempMap=new HashMap<String, String>();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String KEY;
            String VALUE;
            for (String textLine =reader.readLine();textLine!=null;textLine=reader.readLine()){
                KEY=getKey(textLine);
                VALUE=getValue(textLine);
                if (KEY!=null&VALUE!=null){
                    tempMap.put(KEY,VALUE);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempMap;
    }

    private String getValue(String textLine) {
        if (textLine.contains("=")&&textLine.indexOf("=")<textLine.length()-2){
            return textLine.substring(textLine.indexOf("=")+1);
        }
        return null;
    }
    private String getKey(String textLine) {
        if (textLine.contains("=")&&textLine.indexOf("=")>0){
            return textLine.substring(0,textLine.indexOf("="));
        }
        return null;
    }

    public static void registerListener(TranslatorLoadedListener listener){
        if (instance==null)
            ListenerList=new ArrayList<TranslatorLoadedListener>();
        if (!ListenerList.contains(listener))
            ListenerList.add(listener);
    }
    public enum State{
        Translated,NotTranslated,PartlyTranslated,Default,Error;
        public Boolean isTranslated(){
            return this.equals(Translated);
        }
    }
    private static List<TranslatorLoadedListener> ListenerList=null;
    @SubscribeEvent
    public static void onLanguageChangedEvent(LanguageChangedEvent event){
        instance.currentCode=event.getCurrentCode();
        instance.load();
    }
}
