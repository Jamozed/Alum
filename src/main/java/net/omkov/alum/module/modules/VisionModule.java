// VisionModule.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
		MC.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, -1));
	}
	
	@Override
	public void onDisable() {
		MC.player.sendMessage(Text.translatable("message.alum.vision.disable"), true);
		MC.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
	}
	
	@Override
	public void onUpdate() {
		if (this.isEnabled()) {
			MC.player.removeStatusEffect(StatusEffects.NAUSEA);
			MC.player.removeStatusEffect(StatusEffects.DARKNESS);
			MC.player.removeStatusEffect(StatusEffects.BLINDNESS);
		}
	}
}
