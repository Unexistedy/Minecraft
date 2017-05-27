package com.unexistedy.element.mod.proxy.client.gui;

import com.unexistedy.element.mod.proxy.client.gui.container.ContainerExtractionMachine;
import com.unexistedy.element.mod.proxy.client.gui.container.tileEntity.TileExtractionMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GuiExtractionMachine extends GuiContainer {
    public GuiExtractionMachine(IInventory playerInventory, TileExtractionMachine tile) {
        super(new ContainerExtractionMachine(playerInventory,tile));
        this.xSize=256;
        this.ySize=256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
