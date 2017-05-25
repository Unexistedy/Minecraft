package com.unexistedy.element.mod.utility;

import com.unexistedy.element.mod.proxy.common.events.events.TranslatorLoadedEvent;
import com.unexistedy.element.mod.misc.Stage;
import com.unexistedy.element.mod.reference.Log;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class LogHelper {
    private static Logger logger= Reference.DefaultLogger;
    public LogHelper(){
        logger=Reference.DefaultLogger;
        if (logger==null)
            logger=LogManager.getLogger(Reference.ID);
        if (Translator.getStage().equals(Stage.Warn)||Translator.getStage().isReady()){
            setLogger(Reference.Name);
        }
        info(true,"Logger loaded.");
    }
    private static void setLogger(String loggerName){
        if (!logger.getName().equals(loggerName)) {
            info(Log.Info.SetLogger, loggerName);
            logger = LogManager.getLogger(loggerName);
        }
    }
    private static void log(Boolean force,Level level,String key,Object...parameters){
        if (logger==null)
            new LogHelper();
        if (key!=null)
        {
            if (force)
                logger.log(level,String.format(key,parameters));
            else
            {
                String translate=Translator.get(key, parameters);
                if (FMLCommonHandler.instance().getEffectiveSide().isClient())
                    translate="["+Reference.Name+"]:"+translate;
                if (Translator.getResults().isTranslated())
                    logger.log(level,translate);
            }
        }
    }
    public static void info(Boolean force,String key,Object...parameters){
        log(force,Level.INFO,key,parameters);
    }
    public static void info(String key,Object...parameters){
        info(false,key,parameters);
    }
    public static void error(Boolean force,String key,Object...parameters){
        log(force,Level.ERROR,key,parameters);
    }
    public static void error(String key,Object...parameters){
        error(false,key,parameters);
    }
    public static void warn(Boolean force,String key,Object...parameters){
        log(force,Level.WARN,key,parameters);
    }
    public static void warn(String key,Object...parameters){
        warn(false,key,parameters);
    }
    @SubscribeEvent
    public static void onTranslatorLoadedEvent(TranslatorLoadedEvent event){
        setLogger(Reference.Name);
    }
}
