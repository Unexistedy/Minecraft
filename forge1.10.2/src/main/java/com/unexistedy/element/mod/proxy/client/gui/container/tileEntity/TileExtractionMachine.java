package com.unexistedy.element.mod.proxy.client.gui.container.tileEntity;

import com.unexistedy.element.mod.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public class TileExtractionMachine extends TileEntity implements ITickable,IInventory{
    private ItemStack[] inventory;
    private String customName;
    public TileExtractionMachine(){
        this.inventory=new ItemStack[this.getSizeInventory()];
    }
    public String getCustomName(){
        return this.customName;
    }
    public void setCustomName(String customName){
        this.customName=customName;
    }


    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index<0||index>=this.getSizeInventory())
            return null;
        return this.inventory[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.getStackInSlot(index)!=null){
            ItemStack itemStack;
            if (this.getStackInSlot(index).stackSize<=count){
                itemStack=this.getStackInSlot(index);
                this.setInventorySlotContents(index,null);
                this.markDirty();
                return itemStack;
            }
            else {
                itemStack=this.getStackInSlot(index).splitStack(count);
                if (this.getStackInSlot(index).stackSize<=0)
                    this.setInventorySlotContents(index,null);
                return itemStack;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return decrStackSize(index,getStackInSlot(index).stackSize);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        if (index<0||index>=this.getSizeInventory())
            return;
        if (stack!=null && stack.stackSize>this.getInventoryStackLimit())
            stack.stackSize=this.getInventoryStackLimit();
        if (stack!=null&&stack.stackSize==0)
            stack=null;
        this.inventory[index]=stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos())==this&&player.getDistanceSq(this.pos.add(0.5,0.5,0.5))<=16;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i=0;i<this.getSizeInventory();i++)
            this.setInventorySlotContents(i,null);
    }

    @Override
    public String getName() {
        return this.hasCustomName()?this.customName:"Default_tile";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName!=null&&!this.customName.equals("");
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag(Reference.ID+"Items", list);
        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.getCustomName());
        }
        return nbt;
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList(Reference.ID+"Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }
    }

    @Override
    public void update() {

    }
}
