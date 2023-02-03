// LightmapTextureManagerMixin.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.LightmapTextureManager;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(LightmapTextureManager.class)
abstract class LightmapTextureManagerMixin {
	@ModifyVariable(method = "update(F)V", at = @At(value = "STORE"), ordinal = 6)
	private float lVariable(float x) {
		if (Alum.modules.vision.isEnabled()) { return 1.0f; } return x;
	}
}
