package com.unexistedy.element.mod.reference;

import java.io.File;

/**
 * Created by Unexistedy on 2017/4/23.
 */
public class Reference {
    public static final String ID="element";
    private static String NAME=ID;
    public static final String ClientProxy="com.unexistedy.element.mod.proxy.ClientProxy";
    public static final String ServerProxy="com.unexistedy.element.mod.proxy.ServerProxy";
    public static final String GuiFactory="";
    public static final File ResourceFile=new File(Reference.class.getResource("/assets/"+ID).getFile());
}
