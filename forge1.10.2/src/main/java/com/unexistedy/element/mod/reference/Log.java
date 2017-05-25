package com.unexistedy.element.mod.reference;

public class Log {
    private static final String Tag=Reference.ID+".log.";
    public class Info{
        private static final String InfoTag=Tag+"info.";
        public static final String SetLogger=InfoTag+"setLogger";
        public static final String TranslatorLoaded=InfoTag+"translatorLoaded";
        public static final String TranslatorLanguageChangedEvent=InfoTag+"translatorLanguageChangedEvent";
        public static final String ListenerLanguageChanged=InfoTag+"listenerLanguageChanged";
        public static final String ConfigSaved=InfoTag+"configSaved";
        public static final String ConfigReloading=InfoTag+"configReloading";
        public static final String ConfigAssigned=InfoTag+"configAssigned";
        public static final String ConfigLoading=InfoTag+"configLoading";
        public static final String ConfigLoaded=InfoTag+"configLoaded";
        public static final String ConfigInjecting=InfoTag+"configInjecting";
        public static final String ConfigInjectComplete=InfoTag+"configInjectComplete";
        public static final String ConfigHandlerReloading=InfoTag+"configHandlerReloading";
        public static final String ConfigHandlerReloaded=InfoTag+"configHandlerReloaded";
    }
    public class Warn{
        private static final String WarnTag=Tag+"warn.";
        public static final String TranslatorLoaded=WarnTag+"translatorLoaded";
    }
}
