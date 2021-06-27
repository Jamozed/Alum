// ClientConnectionMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.omkov.alum.event.ClientConnectionEvents;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	@Shadow
	private Channel channel;
	
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
		if (channel.isOpen() && packet != null) {
			ClientConnectionEvents.START_CHANNEL_READ.invoker().onChannelRead(packet);
		}
	}
}
