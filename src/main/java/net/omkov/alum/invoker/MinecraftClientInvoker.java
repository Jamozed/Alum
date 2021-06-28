// MinecraftClientInvoker.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.invoker;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public interface MinecraftClientInvoker {
	public void doItemUseInvoker();
}
