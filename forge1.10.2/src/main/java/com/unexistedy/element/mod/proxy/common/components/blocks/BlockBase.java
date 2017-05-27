package com.unexistedy.element.mod.proxy.common.components.blocks;


import com.unexistedy.element.mod.proxy.common.components.IRegisterBase;
import com.unexistedy.element.mod.proxy.common.components.IRegisterModel;
import com.unexistedy.element.mod.reference.Reference;
import com.unexistedy.element.mod.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockBase extends Block implements IRegisterModel<Block> {
    private final String name;
    public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn,String name) {
        super(blockMaterialIn, blockMapColorIn);
        this.name=name;
        setUnlocalizedName(name);
        setRegistryName(Reference.ID,name);
    }
    public BlockBase(Material blockMaterialIn,String name) {
        super(blockMaterialIn);
        this.name=name;
        setUnlocalizedName(name);
        setRegistryName(Reference.ID,name);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        LogHelper.info(true,"Registering model for item "+this.getName());
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this),0,new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()),"inventory"));
    }
    @Override
    public void register(RegistryEvent.Register event) {
        LogHelper.info(true,"Registering block "+this.getName());
        event.getRegistry().register(this);
        GameRegistry.register(new ItemBlock(this).setRegistryName(Reference.ID,getName()));
    }
    private String getName(){
        return name;
    }
    @Override
    public IRegisterBase[] getComponents() {
        return new IRegisterBase[]{this};
    }
    @Override
    public String getUnlocalizedName(){
        return Reference.ID+"."+super.getUnlocalizedName();
    }
}
