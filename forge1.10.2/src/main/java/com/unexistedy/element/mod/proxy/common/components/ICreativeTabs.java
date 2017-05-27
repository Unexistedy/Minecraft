package com.unexistedy.element.mod.proxy.common.components;

import com.unexistedy.element.mod.proxy.common.components.items.Items;
import com.unexistedy.element.mod.reference.Lang;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ICreativeTabs {
    public static final CreativeTabs DarkTab=new CreativeTabs(Lang.Name) {
        @Override
        public Item getTabIconItem() {
            return Items.ElementCrystal;
        }
    };
}
