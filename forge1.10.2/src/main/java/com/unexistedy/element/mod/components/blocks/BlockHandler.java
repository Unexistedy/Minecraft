package com.unexistedy.element.mod.components.blocks;

import com.unexistedy.element.mod.common.classes.HandlerBase;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Unexistedy on 2017/4/26.
 */
@GameRegistry.ObjectHolder(Reference.ID)
@Mod.EventBusSubscriber
public class BlockHandler extends HandlerBase {
    @Override
    public void loadCommon() {

    }

    @Override
    public void loadClient() {

    }

    @Override
    public void loadServer() {

    }
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Block> event){

    }
}
