// AutoClickModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.TranslatableText;
import net.omkov.alum.Alum;
import net.omkov.alum.MinecraftClientInvoker;
import net.omkov.alum.module.Module;

/** Provides auto click functionality. */
public class FastClickModule extends Module {
	public FastClickModule() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> { onUpdate(); });
	}
	
	@Override
	public void onEnable() {
		MC.player.sendMessage(new TranslatableText("message.alum.fast_click.enable"), true);
	}
	
	@Override
	public void onDisable() {
		MC.player.sendMessage(new TranslatableText("message.alum.fast_click.disable"), true);
	}
	
	@Override
	public void onUpdate() {
		if (!isEnabled() || MC.player == null || MC.currentScreen != null) { return; }
		
		if (MC.options.keyUse.isPressed()) {
			for (int i = 0; i != Alum.CONFIG.fastClickSpeed; ++i) {
				((MinecraftClientInvoker)MC).doItemUseInvoker();
			}
		}
	}
}
