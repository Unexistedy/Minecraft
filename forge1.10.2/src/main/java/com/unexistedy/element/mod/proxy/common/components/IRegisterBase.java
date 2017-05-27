package com.unexistedy.element.mod.proxy.common.components;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public interface IRegisterBase <T extends IForgeRegistryEntry<T>> {
    IRegisterBase<T>[] getComponents();
    void register(RegistryEvent.Register<T> event);
}
