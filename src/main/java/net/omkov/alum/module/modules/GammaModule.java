// GammaModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.minecraft.text.TranslatableText;
import net.omkov.alum.module.Module;

/** Provides fullbright functionality. */
public class GammaModule extends Module {
	private Double gamma;
	
	@Override
	public void onEnable() {
		gamma = MC.options.gamma; MC.options.gamma = 16;
		MC.player.sendMessage(new TranslatableText("message.alum.gamma.enable"), true);
	}
	
	@Override
	public void onDisable() {
		MC.options.gamma = gamma; gamma = null;
		MC.player.sendMessage(new TranslatableText("message.alum.gamma.disable"), true);
	}
}
