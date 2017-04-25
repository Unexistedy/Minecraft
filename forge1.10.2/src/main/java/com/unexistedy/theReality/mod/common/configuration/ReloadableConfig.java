package com.unexistedy.theReality.mod.common.configuration;

import com.unexistedy.theReality.mod.common.classes.Base;
import com.unexistedy.theReality.mod.common.event.event.TranslatorLoadedEvent;
import com.unexistedy.theReality.mod.reference.Keys;
import com.unexistedy.theReality.mod.reference.Reference;
import com.unexistedy.theReality.mod.utility.LogHelper;
import com.unexistedy.theReality.mod.utility.Translator;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public abstract class ReloadableConfig extends Base{
    public Configuration configuration;
    private String ConfigKey;
    private Boolean giveValue=true;
    private Map<String,Map<String,String[]>> Properties;
    public ReloadableConfig(String keyIn){
        String file=Translator.get(keyIn);
        if (file !=null) {
            if (!file.endsWith(".cfg"))
                file+=".cfg";
            configuration = new Configuration(new File(Reference.getConfigDirectory(), Translator.get(Keys.Config.ConfigFolder) + "/" +file));
            configuration.load();
            ConfigKey = keyIn;
            Properties = new HashMap<String, Map<String, String[]>>();
            load();
            configuration.save();
            ConfigHandler.register(this);
            setReady();
        }
    }
    abstract public void load();
    public void reload(){
        giveValue=false;
        if (this.ConfigKey!=null){
            String fileName=Translator.get(ConfigKey);
            if (fileName!=null){
                if (!fileName.endsWith(".cfg"))
                    fileName+=".cfg";
                File previousFile=configuration.getConfigFile();
                configuration=new Configuration(new File(Reference.getConfigDirectory(), Translator.get(Keys.Config.ConfigFolder) + "/" +fileName));
                if (!configuration.getConfigFile().equals(previousFile))
                    if (!previousFile.delete())
                        LogHelper.warn(Keys.Log.FileCanNotDeleteWarn,previousFile.getName());
            }
        }
        load();
        for (Map.Entry<String,Map<String,String[]>> Entry:Properties.entrySet()){
            for (Map.Entry<String,String[]> entry:Entry.getValue().entrySet()){
                if (entry.getValue().length>1)
                    configuration.getCategory(Translator.get(Entry.getKey())).get(Translator.get(entry.getKey())).set(entry.getValue());
                else
                    configuration.getCategory(Translator.get(Entry.getKey())).get(Translator.get(entry.getKey())).set(entry.getValue()[0]);
            }
        }
        configuration.save();
        giveValue=true;
    }
    public void onConfigchangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        giveValue=true;
        load();
        if (configuration.hasChanged())
            configuration.save();
    }
    public void onTranslatorReloadedEvent(){
        reload();
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment) {
        return getString(variable,name, category, defaultValue, comment, name, null);
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment, String langKey) {
        return getString(variable,name, category, defaultValue, comment, langKey, null);
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment, Pattern pattern) {
        return getString(variable,name, category, defaultValue, comment, name, pattern);
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment, String langKey, Pattern pattern) {
        String value=this.configuration.getString(Translator.get(name), Translator.get(category), defaultValue, comment, langKey, pattern);
        if (giveValue){
            registerProperty(category,name,value);
            return value;
        }
        else
            return variable;
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment, String[] validValues) {
        String value=configuration.getString(Translator.get(name), Translator.get(category), defaultValue, comment, validValues, name);
        if (giveValue){
            registerProperty(category,name,value);
            return value;
        }
        else
            return variable;
    }
    public String getString(String variable,String name, String category, String defaultValue, String comment, String[] validValues, String langKey) {
        String value=configuration.getString(Translator.get(name), Translator.get(category), defaultValue, comment, validValues, langKey);
        if (giveValue){
            registerProperty(category,name,value);
            return value;
        }
        else
            return variable;
    }
    public String[] getStringList(String[] variable,String name, String category, String[] defaultValues, String comment) {
        String[] values=configuration.getStringList(Translator.get(name), Translator.get(category), defaultValues, comment);
        registerProperty(category,name,values);
        if (giveValue){
            registerProperty(category,name,values);
            return values;
        }
        else
            return variable;
    }
    public String[] getStringList(String[] variable,String name, String category, String[] defaultValue, String comment, String[] validValues) {
        String[] values=configuration.getStringList(Translator.get(name), Translator.get(category), defaultValue, comment,validValues);
        if (giveValue){
            registerProperty(category,name,values);
            return values;
        }
        else
            return variable;
    }
    public String[] getStringList(String[] variable,String name, String category, String[] defaultValue, String comment, String[] validValues, String langKey) {
        String[] values=configuration.getStringList(Translator.get(name), Translator.get(category), defaultValue, comment,validValues,langKey);
        if (giveValue){
            registerProperty(category,name,values);
            return values;
        }
        else
            return variable;
    }
    public boolean getBoolean(Boolean variable,String name, String category, boolean defaultValue, String comment) {
        Boolean value=configuration.getBoolean(Translator.get(name), Translator.get(category), defaultValue, comment);
        if (giveValue) {
            registerProperty(category, name, value.toString());
            return value;
        }
        return variable;
    }
    public boolean getBoolean(Boolean variable,String name, String category, boolean defaultValue, String comment, String langKey) {
        Boolean value=configuration.getBoolean(Translator.get(name), Translator.get(category), defaultValue, comment,langKey);
        if (giveValue) {
            registerProperty(category, name, value.toString());
            return value;
        }
        return variable;
    }
    public int getInt(int variable,String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
        int value=configuration.getInt(Translator.get(name), Translator.get(category), defaultValue, minValue,maxValue,comment);
        if (giveValue) {
            registerProperty(category, name, String.valueOf(value));
            return value;
        }
        return variable;
    }
    public int getInt(int variable,String name, String category, int defaultValue, int minValue, int maxValue, String comment, String langKey) {
        int value=configuration.getInt(Translator.get(name), Translator.get(category), defaultValue, minValue,maxValue,comment,langKey);
        if (giveValue) {
            registerProperty(category, name, String.valueOf(value));
            return value;
        }
        return variable;
    }
    public float getFloat(float variable,String name, String category, float defaultValue, float minValue, float maxValue, String comment) {
        return getFloat(variable,name, category, defaultValue, minValue, maxValue, comment, name);
    }
    public float getFloat(float variable,String name, String category, float defaultValue, float minValue, float maxValue, String comment, String langKey) {
        float value=configuration.getFloat(Translator.get(name), Translator.get(category), defaultValue, minValue,maxValue,comment,langKey);
        if (giveValue) {
            registerProperty(category, name, String.valueOf(value));
            return value;
        }
        return variable;
    }
    private void registerProperty(String category,String name,String... value){
        if (!this.Properties.containsKey(category))
        {
            this.Properties.put(category,new HashMap<String, String[]>());
        }
        this.Properties.get(category).put(name,value);
    }
}
