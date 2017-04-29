package com.unexistedy.element.mod.components;

import com.unexistedy.element.mod.common.classes.HandlerBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by Unexistedy on 2017/4/26.
 */
public class CreativeTab extends HandlerBase{
    public static CreativeTab instance;
    public static CreativeTabs Element;
    public CreativeTab(){
        instance=this;
        load();
        setReady();
    }

    public static CreativeTab getInstance() {
        if (instance==null)
            new CreativeTab();
        return instance;
    }

    @Override
    public void loadCommon() {

    }

    @Override
    public void loadClient() {
        Element=new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
        new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
        new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
        new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
        new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
        new CreativeTabs(CreativeTabs.getNextID(),"element") {
            @Override
            public Item getTabIconItem() {
                return Items.APPLE;
            }
        };
    }

    @Override
    public void loadServer() {

    }
}
