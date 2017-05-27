package com.unexistedy.element.mod.proxy.common.components.blocks;

import com.unexistedy.element.mod.proxy.common.components.IHandlerBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterInteract;
import com.unexistedy.element.mod.proxy.common.components.IRegisterModel;
import com.unexistedy.element.mod.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class BlockHandler implements IHandlerBase<Block>{
    private static List<BlockBase> Components;
    @Override
    public void registerModel() {
        if (Components==null)
            loadComponents();
        for (IRegisterBase entry:Components){
            if (entry instanceof IRegisterModel)
                ((IRegisterModel) entry).registerModel();
        }
    }
    @Override
    public void interact(Object... parameters) {
        if (Components==null)
            loadComponents();
        for (IRegisterBase entry:Components){
            if (entry instanceof IRegisterInteract)
                ((IRegisterInteract) entry).interact();
        }
    }
    @Override
    public void loadComponents() {
        add(new BlockExtractionMachine());
    }
    @Override
    public void uninstalComponents() {
        Components=null;
    }
    @Override
    public void add(Block component) {
        if (Components==null)
            Components=new ArrayList<BlockBase>();
        if ((!Components.contains(component))&&component instanceof IRegisterBase){
            Components.add((BlockBase) component);
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
    public static void onRegisteryBlockEvent(RegistryEvent.Register<Block> event){
        LogHelper.info(true,"Registering blocks");
        if (Components==null)
            new BlockHandler().loadComponents();
        for (BlockBase entry:Components){
            entry.register(event);
        }
    }
}