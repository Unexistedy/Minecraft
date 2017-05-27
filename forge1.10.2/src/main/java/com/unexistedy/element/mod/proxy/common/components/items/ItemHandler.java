package com.unexistedy.element.mod.proxy.common.components.items;

import com.unexistedy.element.mod.proxy.common.components.IHandlerBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterInteract;
import com.unexistedy.element.mod.proxy.common.components.IRegisterModel;
import com.unexistedy.element.mod.proxy.common.components.blocks.BlockHandler;
import com.unexistedy.element.mod.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemHandler implements IHandlerBase<Item> {
    private static List<IRegisterBase<Item>> Components;
    @Override
    public void loadComponents() {
        add(new ItemElementCrystal());
    }
    @Override
    public void uninstalComponents() {
        Components=null;
    }
    @Override
    public void add(Item component) {
        if (Components==null)
            Components=new ArrayList<IRegisterBase<Item>>();
        if (component instanceof IRegisterBase)
            if (!Components.contains(component))
                Components.add((IRegisterBase<Item>) component);
    }
    @Override
    public void registerModel() {
        if (Components==null)
            loadComponents();
        for (IRegisterBase entry:Components){
            if (entry instanceof IRegisterModel)
                ((IRegisterModel<Item>)entry).registerModel();
        }
    }
    @Override
    public void interact(Object... parameters) {
        if (Components==null)
            loadComponents();
        for (IRegisterBase entry:Components){
            if (entry instanceof IRegisterInteract)
                ((IRegisterInteract<Item>)entry).interact();
        }
    }
    @Override
    public IRegisterBase[] getComponents() {
        return Components.toArray(new IRegisterBase[Components.size()]);
    }
    @Override
    public void register(RegistryEvent.Register event) {

    }
    @SubscribeEvent
    public static void onRegisteryItemEvent(RegistryEvent.Register<Item> event){
        LogHelper.info(true,"Registering items");
        if (Components==null)
            new ItemHandler().loadComponents();
        for (IRegisterBase<Item> entry:Components){
            entry.register(event);
        }
    }
}