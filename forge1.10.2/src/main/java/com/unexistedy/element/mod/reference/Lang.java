package com.unexistedy.element.mod.reference;

public class Lang {
    public static final String CodeKey="language.code";
    public static final String NameKey="language.name";
    private static final String Tag=Reference.ID+".";
    public static final String Name=Tag+"name";
    public class Config{
        private static final String Tag=Lang.Tag+"config.";
        public static final String Holder=Tag+"holder";
        public class System{
            private static final String Tag= Config.Tag+"system.";
            public static final String File=Tag+"file";
            public class Category{
                private static final String Tag= Config.System.Tag+"category.";
                public static final String Language=Tag+"language";
            }
            public class Name{
                private static final String Tag= Config.System.Tag+"name.";
                public static final String Language=Tag+"language";
            }
            public class Comment{
                private static final String Tag= Config.System.Tag+"comment.";
                public static final String Language=Tag+"language";
            }
        }

    }
}
