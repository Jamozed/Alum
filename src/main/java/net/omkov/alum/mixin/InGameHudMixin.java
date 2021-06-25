// InGameHudMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.omkov.alum.module.modules.HudModule;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow @Final
	private MinecraftClient client;
	
	@Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;)V", at = @At(value = "RETURN"))
	private void onInit(MinecraftClient client, CallbackInfo ci) {
		HudModule.INSTANCE = new HudModule(client);
	}
	
	@Inject(method = "render", at = @At(value = "HEAD"))
	private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		HudModule.INSTANCE.draw(matrices);
	}
}
