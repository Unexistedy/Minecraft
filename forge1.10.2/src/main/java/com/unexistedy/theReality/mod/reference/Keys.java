package com.unexistedy.theReality.mod.reference;

/**
 * Created by Unexistedy on 2017/4/24.
 */
public class Keys {
    private static final String TAG=Reference.ID+".";
    public static final String Name=TAG+"name";
    public class Translator{
        public static final String LanguageCode="language.code";
    }
    public class Config{
        private static final String ConfigTag=TAG+"config.";
        public static final String ConfigFolder=ConfigTag+"folder";
        public class ConfigSystem{
            private static final String LocalTag=ConfigTag+"configSystem.";
            public static final String File=LocalTag+"file";
            public class SystemLanguage{
                private static final String T=LocalTag+"systemLanguage.";
                public static final String name=T+"name";
                public static final String category=T+"category";
                public static final String comment=T+"comment";
            }
            public class ShowTrace{
                private static final String T=LocalTag+"showTrace.";
                public static final String name=T+"name";
                public static final String category=T+"category";
                public static final String comment=T+"comment";
            }
        }
    }
    public class Log{
        private static final String LogTag=TAG+"log.";
        public static final String LangFileExistWarn=LogTag+"langFileExistWarn";
        public static final String FileCanNotDeleteWarn=LogTag+"fileCanNotDeleteWarn";
        public static final String LogHelperLoadedInfo=LogTag+"logHelperLoadedInfo";
        public static final String LogHelperReloadedInfo=LogTag+"logHelperReloadedInfo";
        public static final String ConfigHandlerLoadedInfo=LogTag+"configHandlerLoadedInfo";
        public static final String ConfigHandlerReloadedInfo=LogTag+"configHandlerReloadedInfo";
        public static final String TranslatorLoadedInfo=LogTag+"translatorLoadedInfo";
        public static final String TranslatorReloadedInfo=LogTag+"translatorReloadedInfo";
    }
}
