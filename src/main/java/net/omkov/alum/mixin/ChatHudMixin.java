// CharHudMixin.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.hud.ChatHud;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(ChatHud.class)
public class ChatHudMixin {
	@Inject(method = "clear", at = @At("HEAD"), cancellable = true)
	public void clear(boolean clearHistory, CallbackInfo ci) {
		if (Alum.CONFIG.keepChatHistory) { ci.cancel(); }
	}
}
