// MuteModule.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.omkov.alum.Alum;
import net.omkov.alum.module.Module;

/** Provides mute functionality. */
@Environment(EnvType.CLIENT)
public class MuteModule extends Module {
	private Double volume;
	
	public MuteModule() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (Alum.bindings.mute.wasPressed()) { toggle(); }
		});
	}
	
	@Override
	protected void onEnable() {
		volume = MC.options.getSoundVolumeOption(SoundCategory.MASTER).getValue();
		MC.options.getSoundVolumeOption(SoundCategory.MASTER).setValue(0.0);
		if (MC.player != null) { MC.player.sendMessage(Text.translatable("message.alum.mute.enable"), true); }
	}
	
	@Override
	protected void onDisable() {
		MC.options.getSoundVolumeOption(SoundCategory.MASTER).setValue(volume); volume = null;
		if (MC.player != null) { MC.player.sendMessage(Text.translatable("message.alum.mute.disable"), true); }
	}
}
