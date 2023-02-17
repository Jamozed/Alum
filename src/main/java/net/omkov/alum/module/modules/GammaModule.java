// GammaModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.omkov.alum.Alum;
import net.omkov.alum.module.Module;

/** Provides fullbright functionality. */
@Environment(EnvType.CLIENT)
public class GammaModule extends Module {
	private Double gamma;
	
	public GammaModule() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (Alum.bindings.gamma.wasPressed()) { toggle(); }
		});
	}
	
	@Override
	public void onEnable() {
		gamma = MC.options.getGamma().getValue(); MC.options.getGamma().setValue(16.0);
		if (MC.player != null) { MC.player.sendMessage(Text.translatable("message.alum.gamma.enable"), true); }
	}
	
	@Override
	public void onDisable() {
		MC.options.getGamma().setValue(gamma); gamma = null;
		if (MC.player != null) { MC.player.sendMessage(Text.translatable("message.alum.gamma.disable"), true); }
	}
}
