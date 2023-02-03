// GameRendererMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
abstract class GameRendererMixin {
	@Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At(value = "RETURN"), cancellable = true)
	private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(Alum.modules.zoom.zoom(cir.getReturnValue()));
	}
}
