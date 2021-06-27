// InGameHudMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.omkov.alum.event.InGameHudEvents;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;)V", at = @At(value = "RETURN"))
	private void onInit(MinecraftClient client, CallbackInfo ci) {
		InGameHudEvents.INIT.invoker().onInit(client);
	}
}
