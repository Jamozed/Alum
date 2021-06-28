// MinecraftClientInvoker.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.omkov.alum.invoker.MinecraftClientInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements MinecraftClientInvoker {
	@Shadow
	private void doItemUse() {}
	
	@Override
	public void doItemUseInvoker() { doItemUse(); }
}
