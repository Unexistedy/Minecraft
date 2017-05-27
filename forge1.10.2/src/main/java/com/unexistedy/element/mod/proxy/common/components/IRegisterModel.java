package com.unexistedy.element.mod.proxy.common.components;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRegisterModel<T extends IForgeRegistryEntry<T>> extends IRegisterBase {
    @SideOnly(Side.CLIENT)
    void registerModel();
}
