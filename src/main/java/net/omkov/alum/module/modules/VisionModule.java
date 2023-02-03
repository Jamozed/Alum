// VisionModule.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.omkov.alum.Alum;
import net.omkov.alum.module.Module;

/** Provides vision functionality. */
public class VisionModule extends Module {
	public VisionModule() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (Alum.bindings.vision.wasPressed() && MC.player != null) { toggle(); }
			if (isEnabled()) { onUpdate(); }
		});
	}
	
	@Override
	public void onEnable() {
		MC.player.sendMessage(Text.translatable("message.alum.vision.enable"), true);
	}
	
	@Override
	public void onDisable() {
		MC.player.sendMessage(Text.translatable("message.alum.vision.disable"), true);
	}
}
