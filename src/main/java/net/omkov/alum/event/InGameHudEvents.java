// InGameHudEvents.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.event;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public final class InGameHudEvents {
	public static final Event<Init> INIT = EventFactory.createArrayBacked(Init.class, (callbacks) -> (client) -> {
		for (Init event : callbacks) { event.onInit(client); }
	});
	
	@FunctionalInterface
	public interface Init { void onInit(MinecraftClient client); }
}
