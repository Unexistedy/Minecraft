package com.unexistedy.element.mod.proxy.common.configuration;

import com.unexistedy.element.mod.reference.Lang;
import com.unexistedy.element.mod.reference.Log;
import com.unexistedy.element.mod.reference.Reference;
import com.unexistedy.element.mod.utility.FileHelper;
import com.unexistedy.element.mod.utility.LogHelper;
import com.unexistedy.element.mod.utility.Translator;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ConfigReloadable implements ReloadableConfig {
    private Configuration ConfigFile;
    private final String FileKey;
    private Boolean EnableInjection=true;
    private Map<String,Map<String,IProperty>> Components;

    public ConfigReloadable(String keyIn){
        FileKey=keyIn;
        Components=new HashMap<String, Map<String, IProperty>>();
        String filePath=Translator.get(Lang.Config.Holder)+"/"+Translator.get(FileKey);
        if (!filePath.endsWith(".cfg"))
            filePath+=".cfg";
        ConfigFile=new Configuration(new File(Reference.ConfigFolder,filePath));
        EnableInjection=true;
        ConfigHandler.register(this);
        work();
    }
    @Override
    public void work(){
        preLoad();
        load();
        postLoad();
        afterLoad();
    }
    @Override
    public void preLoad(){
        LogHelper.info(Log.Info.ConfigLoading,Translator.get(FileKey));
    }
    @Override
    public void postLoad(){
        ConfigFile.save();
        LogHelper.info(Log.Info.ConfigLoaded,Translator.get(FileKey));
    }
    @Override
    public void reload() {
        LogHelper.info(Log.Info.ConfigReloading,ConfigFile.getConfigFile().getName());
        FileHelper.delete(ConfigFile.getConfigFile());
        String filePath=Translator.get(Lang.Config.Holder)+"/"+Translator.get(FileKey);
        if (!filePath.endsWith(".cfg"))
            filePath+=".cfg";
        ConfigFile=new Configuration(new File(Reference.ConfigFolder,filePath));
        LogHelper.info(Log.Info.ConfigAssigned,Translator.get(FileKey));
        EnableInjection=false;
        work();
        injectValues();
        ConfigFile.save();
        EnableInjection=true;
    }
    @Override
    public void save() {
        work();
        ConfigFile.save();
        LogHelper.info(Log.Info.ConfigSaved,Translator.get(FileKey));
    }
    @Override
    public void register(String category,String name,ConfigReloadable.IProperty iProperty){
        if (Components==null)
            Components=new HashMap<String, Map<String, IProperty>>();
        if (!Components.containsKey(category))
            Components.put(category,new HashMap<String, IProperty>());
        Components.get(category).put(name,iProperty);
    }
    @Override
    public void injectValues(){
        LogHelper.info(Log.Info.ConfigInjecting);
        for (Map.Entry<String,Map<String,IProperty>> Category:Components.entrySet()){
            for (Map.Entry<String,IProperty> property:Category.getValue().entrySet()){
                if (property.getValue().isMulti)
                    ConfigFile.getCategory(Category.getKey()).get(property.getKey()).set(property.getValue().getValues());
                else
                    ConfigFile.
                            getCategory(Translator.get(Category.getKey())).
                            get(Translator.get(property.getKey())).
                            set(
                                    property.getValue()
                                            .getValues()[0]);
            }
        }
        ConfigFile.save();
        LogHelper.info(Log.Info.ConfigInjectComplete);
    }

    public String getString(String value,String name, String category, String defaultValue, String comment) {
        return getString(value,name, category, defaultValue, comment, name, null);
    }
    public String getString(String value,String name, String category, String defaultValue, String comment, String langKey) {
        return getString(value,name, category, defaultValue, comment, langKey, null);
    }
    public String getString(String value,String name, String category, String defaultValue, String comment, Pattern pattern) {
        return getString(value,name, category, defaultValue, comment, name, pattern);
    }
    public String getString(String value,String name, String category, String defaultValue, String comment, String langKey, Pattern pattern) {
        String Value=ConfigFile.getString(Translator.get(name),Translator.get(category),Translator.get(defaultValue),Translator.get(comment),langKey,pattern);
        if (EnableInjection){
            register(category,name,new IProperty(false,Value));
            return Value;
        }
        else
            return value;
    }

    public String getString(String value,String name, String category, String defaultValue, String comment, String[] validValues) {
        return getString(value, name, category, defaultValue, comment, validValues,name);
    }
    public String getString(String value,String name, String category, String defaultValue, String comment, String[] validValues, String langKey) {
        String Value=ConfigFile.getString(Translator.get(name),Translator.get(category),Translator.get(defaultValue),Translator.get(comment),Translator.get(validValues),langKey);
        if (EnableInjection){
            register(category,name,new IProperty(false,Value));
            return Value;
        }
        else
            return value;
    }

    public String[] getStringList(String[] values,String name, String category, String[] defaultValues, String comment) {
        return getStringList(values,name, category, defaultValues, comment, null, name);
    }
    public String[] getStringList(String[] values,String name, String category, String[] defaultValue, String comment, String[] validValues) {
        return getStringList(values,name, category, defaultValue, comment, validValues, name);
    }
    public String[] getStringList(String[] values,String name, String category, String[] defaultValue, String comment, String[] validValues, String langKey) {
        String[] Values=ConfigFile.getStringList(Translator.get(name),Translator.get(category),Translator.get(defaultValue),Translator.get(comment),Translator.get(validValues),langKey);
        if (EnableInjection){
            register(category,name,new IProperty(true,Values));
            return Values;
        }
        else
            return values;
    }

    public boolean getBoolean(Boolean value,String name, String category, boolean defaultValue, String comment) {
        return getBoolean(value,name, category, defaultValue, comment, name);
    }
    public boolean getBoolean(Boolean value,String name, String category, boolean defaultValue, String comment, String langKey) {
        Boolean Value=ConfigFile.getBoolean(Translator.get(name),Translator.get(category),defaultValue,Translator.get(comment),langKey);
        if (EnableInjection){
            register(category,name,new IProperty(false,Value));
            return Value;
        }
        else
            return value;
    }

    public int getInt(int value,String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
        return getInt(value,name, category, defaultValue, minValue, maxValue, comment, name);
    }
    public int getInt(int value,String name, String category, int defaultValue, int minValue, int maxValue, String comment, String langKey) {
        int Value=ConfigFile.getInt(Translator.get(name),Translator.get(category),defaultValue,minValue,maxValue,Translator.get(comment),langKey);
        if (EnableInjection){
            register(category,name,new IProperty(false,Value));
            return Value;
        }
        else
            return value;
    }

    public float getFloat(float value,String name, String category, float defaultValue, float minValue, float maxValue, String comment) {
        return getFloat(value,name, category, defaultValue, minValue, maxValue, comment, name);
    }
    public float getFloat(float value,String name, String category, float defaultValue, float minValue, float maxValue, String comment, String langKey)
    {
        float Value=ConfigFile.getFloat(Translator.get(name),Translator.get(category),defaultValue,minValue,maxValue,Translator.get(comment),langKey);
        if (EnableInjection){
            register(category,name,new IProperty(false,Value));
            return Value;
        }
        else
            return value;
    }

    public class IProperty{
        private Boolean isMulti;
        private String[] values;
        public String[] getValues() {
            return values;
        }
        public Boolean isMultiValues() {
            return isMulti;
        }
        public IProperty(Boolean isMulti,Object...values){
            this.isMulti=isMulti;
            List<String> inputValues=new ArrayList<String>();
            for (Object entry:values){
                inputValues.add(entry.toString());
            }
            this.values=inputValues.toArray(new String[inputValues.size()]);
        }
    }
}
