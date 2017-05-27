package com.unexistedy.element.mod.proxy.common.components;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public interface IRegisterInteract<T extends IForgeRegistryEntry<T>> extends IRegisterBase {
    void interact(Object...parameters);
}
