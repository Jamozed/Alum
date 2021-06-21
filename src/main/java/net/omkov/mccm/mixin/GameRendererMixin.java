// GameRendererMixin.java
// GameRenderer mixin for MCCM
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm.mixin;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.omkov.mccm.MCCM;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Redirect(
		method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
		at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;fov:D")
	)
	private double getFov(GameOptions options) {
		return MCCM.CM.modus.zoom.zoom(MCCM.MC.options.fov);
	}
}
