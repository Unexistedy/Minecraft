package com.unexistedy.theReality.mod.utility;

import com.unexistedy.theReality.mod.common.classes.Base;
import com.unexistedy.theReality.mod.common.event.listener.TranslatorLoadedListener;
import com.unexistedy.theReality.mod.reference.Keys;
import com.unexistedy.theReality.mod.reference.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Unexistedy on 2017/4/25.
 */
public class LogHelper extends Base implements TranslatorLoadedListener{
    private static Logger logger=null;
    private static LogHelper instance;
    public LogHelper(){
        instance=this;
        init();
        Translator.registerListener(instance);
        setReady();
        info(Keys.Log.LogHelperLoadedInfo);
    }
    private void init(){
        if (logger!=null)
            logger.exit();
        String name=Translator.getName();
        if (Translator.getSTATE().isTranslated())
            logger= LogManager.getLogger(name);
        else
            logger= LogManager.getLogger(Reference.Name);
    }
    public static void log(Level level,String key,Object...parameters){
        if (logger==null)
            new LogHelper();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()){
            logger.log(level, "["+Translator.getName()+"]:"+new Throwable().getStackTrace()[2]);
            logger.log(level, "["+Translator.getName()+"]:"+Translator.get(key, parameters));
        }
        else {
            logger.log(level, new Throwable().getStackTrace()[2]);
            logger.log(level, Translator.get(key, parameters));
        }
    }
    public static void warn(String key,Object...parameters) {
        log(Level.WARN,key,parameters);
    }
    public static void error(String key,Object...parameters) {
        log(Level.ERROR,key,parameters);
    }
    public static void info(String key,Object...parameters) {
        log(Level.INFO,key,parameters);
    }

    @Override
    public void onTranslatorLoaded() {
        init();
        info(Keys.Log.LogHelperReloadedInfo);
    }
}
