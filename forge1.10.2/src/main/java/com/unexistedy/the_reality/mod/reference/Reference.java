package com.unexistedy.the_reality.mod.reference;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public class Reference {
    public static final String ID="the_reality";
    private static String NAME=ID;
    public static final String ClientProxy="com.unexistedy.the_reality.mod.proxy.ClientProxy";
    public static final String ServerProxy="com.unexistedy.the_reality.mod.proxy.ServerProxy";
    public static final String GuiFactory="";
    public static final File ResourceFile=new File(Reference.class.getResource("/assets/"+ID).getFile());
}
