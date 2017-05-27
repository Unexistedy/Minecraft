package com.unexistedy.element.mod.proxy.common.components.items;

import com.unexistedy.element.mod.proxy.common.components.IRegisterBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterModel;
import com.unexistedy.element.mod.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemBase extends Item implements IRegisterModel<Item> {
    private final String name;
    public ItemBase(String name){
        this.setUnlocalizedName(name);
        this.setRegistryName(Reference.ID,name);
        this.name=name;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
    }
    @Override
    public void register(RegistryEvent.Register event) {
        event.getRegistry().register(this);
    }
    @Override
    public IRegisterBase[] getComponents() {
        return new IRegisterBase[]{this};
    }
    public String getName(){
        return this.name;
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return Reference.ID+super.getUnlocalizedName(stack);
    }
    @Override
    public String getUnlocalizedName(){
        return Reference.ID+"."+super.getUnlocalizedName();
    }
}