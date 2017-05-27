package com.unexistedy.element.mod.proxy.common.components.items;

import com.unexistedy.element.mod.proxy.common.components.ICreativeTabs;
import com.unexistedy.element.mod.proxy.common.components.blocks.Blocks;
import com.unexistedy.element.mod.reference.ID;
import com.unexistedy.element.mod.reference.Reference;
import com.unexistedy.element.mod.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemElementCrystal extends ItemBase {
    public ItemElementCrystal() {
        super(ID.Item.ElementCrystal);
        setHasSubtypes(true);

        setCreativeTab(ICreativeTabs.DarkTab);
        this.addPropertyOverride(new ResourceLocation("meta"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return stack.getMetadata();
            }
        });
    }
    @Override
    public void registerModel() {
        if (Items.ElementCrystal!=null) {
            LogHelper.info(true,"Registering model for item "+this.getName());
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 0, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 1, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 2, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 3, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 4, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.ElementCrystal, 5, new ModelResourceLocation(new ResourceLocation(Reference.ID,this.getName()), "inventory"));
        }
    }
    @Override
    public boolean isDamageable()
    {
        return false;
    }
    @Override
    public void register(RegistryEvent.Register event) {
        event.getRegistry().register(this);
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        Element element = Element.byItemStack(stack);
        return this.getUnlocalizedName() + "." + element.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (Element entry:Element.values()){
            subItems.add(new ItemStack(this,1,entry.getMeta()));
        }
    }
    public static enum Element{
        J(1,"metal"),M(2,"wood"),S(3,"liquid"),H(4,"fire"),T(5,"ground"),A(0,"darkness");
        private final int meta;
        private final String name;
        Element(int metaIn,String nameIn){
            this.meta=metaIn;
            this.name=nameIn;
        }
        public String getUnlocalizedName()
        {
            return this.name;
        }
        public int getMeta() {
            return meta;
        }

        public static Element byMetadata(int meta){
            for (Element entry:Element.values()){
                if (entry.meta==meta)
                    return entry;
            }
            return A;
        }
        public static Element byItemStack(ItemStack stack){
            return stack.getItem() instanceof ItemElementCrystal? byMetadata(stack.getMetadata()) : A;
        }
    }
}
