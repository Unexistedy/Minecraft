package com.unexistedy.element.mod.proxy.client.gui;

import com.unexistedy.element.Element;
import com.unexistedy.element.mod.proxy.client.gui.container.ContainerExtractionMachine;
import com.unexistedy.element.mod.proxy.client.gui.container.tileEntity.TileExtractionMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{
    public void load(FMLInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(Element.instance,this);
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case com.unexistedy.element.mod.reference.ID.Gui.ExtractionMachine:{
                return new ContainerExtractionMachine(player.inventory,(TileExtractionMachine)world.getTileEntity(new BlockPos(x,y,x)));
            }
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case com.unexistedy.element.mod.reference.ID.Gui.ExtractionMachine: {
                return new GuiExtractionMachine(player.inventory, (TileExtractionMachine)world.getTileEntity(new BlockPos(x,y,z)));
            }
            default:
                return null;
        }
    }
}
