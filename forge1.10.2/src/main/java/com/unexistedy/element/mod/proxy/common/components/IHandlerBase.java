package com.unexistedy.element.mod.proxy.common.components;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public interface IHandlerBase<T extends IForgeRegistryEntry<T>> extends IRegisterMI {
    void loadComponents();
    void uninstalComponents();
    void add(T component);
}
