// GameRendererMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow @Final
	private MinecraftClient client;
	
	@Inject(
		method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
		at = @At(value = "RETURN"), cancellable = true
	)
	private double getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(Alum.CS.modus.zoom.zoom(cir.getReturnValue()));
		return cir.getReturnValue();
	}
}
