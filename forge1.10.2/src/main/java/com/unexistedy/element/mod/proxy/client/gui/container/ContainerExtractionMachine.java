package com.unexistedy.element.mod.proxy.client.gui.container;

import com.unexistedy.element.mod.proxy.client.gui.container.tileEntity.TileExtractionMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerExtractionMachine extends Container{
    private TileExtractionMachine tile;
    public ContainerExtractionMachine(IInventory playerInventory,TileExtractionMachine tile){
        this.tile=tile;
        this.addSlotToContainer(new Slot(tile,1,12,66));
        this.addSlotToContainer(new Slot(tile,2,190,32));
        this.addSlotToContainer(new Slot(tile,3,190,80));
        for (int y=0;y<3;y++){
            for (int x=0;x<3;x++){
                this.addSlotToContainer(new Slot(playerInventory,x+y*9+9,8+18*x,84+y*18));
            }
        }
        for (int x=0;x<9;x++){
            this.addSlotToContainer(new Slot( playerInventory,x,8+x*18,142));
        }
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tile.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < 9) {
                if (!this.mergeItemStack(current, 3, 39, true))
                    return null;
            } else {
                if (!this.mergeItemStack(current, 0, 3, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack( null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }
}
