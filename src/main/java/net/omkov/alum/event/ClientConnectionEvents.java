// ClientConnectionEvents.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.event;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;

@Environment(EnvType.CLIENT)
public final class ClientConnectionEvents {
	public static final Event<ChannelRead> START_CHANNEL_READ = EventFactory.createArrayBacked(ChannelRead.class, (callbacks) -> (packet) -> {
		for (ChannelRead event : callbacks) { event.onChannelRead(packet); }
	});
	
	@FunctionalInterface
	public interface ChannelRead { void onChannelRead(Packet<?> packet); }
}
