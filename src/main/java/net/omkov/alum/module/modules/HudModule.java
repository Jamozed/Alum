// HudModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.omkov.alum.Alum;
import net.omkov.alum.event.ClientConnectionEvents;
import net.omkov.alum.module.Module;

/** Provides additional HUD elements. */
@Environment(EnvType.CLIENT)
public class HudModule extends Module {
	private long prevTime = 0;
	private double tps = 20;
	
	public HudModule() {
		setEnabled(true);
		
		ClientConnectionEvents.START_CHANNEL_READ.register((packet) -> {
			if (packet instanceof WorldTimeUpdateS2CPacket) {
				long time = System.currentTimeMillis();
				long timeOffset = Math.abs(1000 - (time - prevTime)) + 1000;
				tps = Math.round(MathHelper.clamp(20 / (timeOffset / 1000d), 0, 20) * 100d) / 100d;
				prevTime = time;
			}
		});
	}
	
	public void draw(MatrixStack matrices) {
		if (!isEnabled()) { return; }
		
		/* Draw game info in the bottom left corner */
		int lineH = MC.textRenderer.fontHeight + 2;
		int lineX = 2, lineY = MC.getWindow().getScaledHeight() - lineH;
		
		if (Alum.CONFIG.hud.toggleCoords || Alum.CONFIG.hud.toggleCoordsAlt) {
			MC.textRenderer.drawWithShadow(matrices, getXYZ(), lineX, lineY, 0x00E0E0E0);
			lineX = 2; lineY -= lineH;
		}
		
		if (Alum.CONFIG.hud.toggleTps || Alum.CONFIG.hud.togglePing || Alum.CONFIG.hud.toggleFps) {
			MC.textRenderer.drawWithShadow(matrices, getPerf(), lineX, lineY, 0x00E0E0E0);
			lineX = 2; lineY -= lineH;
		}
	}
	
	/** Generate coordinate string. */
	private String getXYZ() {
		double x = MC.player.getX(), y = MC.player.getY(), z = MC.player.getZ();
		boolean nether = MC.world.getRegistryKey().getValue().getPath().contains("nether");
		
		String str = "XYZ: ", format = "%.1f %.1f %.1f";
		
		if (Alum.CONFIG.hud.toggleCoords) {
			str += (nether ? "§4" : "§2") + String.format(format, x, y, z) + "§r ";
		}
		if (Alum.CONFIG.hud.toggleCoordsAlt) {
			x = (nether ? x * 8 : x / 8); z = (nether ? z * 8 : z / 8);
			str += (nether ? "§2[" : "§4[") + String.format(format, x, y, z) + "]§r";
		}
		
		return str;
	}
	
	/** Generate performance string. */
	private String getPerf() {
		PlayerListEntry entry = MC.player.networkHandler.getPlayerListEntry(MC.player.getGameProfile().getId());
		
		int fps = MC.getCurrentFps();
		int ping = (entry == null ? 0 : entry.getLatency());
		
		String str = "";
		
		if (Alum.CONFIG.hud.toggleTps) {
			str += "TPS: " + getColour((int)tps, 8, 12, 16, 18, 20, false) + tps + "§r ";
		}
		if (Alum.CONFIG.hud.toggleFps) {
			str += "FPS: " + getColour(fps, 15, 30, 60, 90, 120, false) + fps + "§r ";
		}
		if (Alum.CONFIG.hud.togglePing) {
			str += "Ping: " + getColour(ping, 600, 400, 200, 100, 50, true) + ping + "§r ";
		}
		
		return str;
	}
	
	/** Return an appropriate colour string for an integer. */
	private String getColour(int value, int l1, int l2, int l3, int l4, int l5, boolean rev) {
		if (rev ? value > l1 : value < l1) { return "§4"; }
		else if (rev ? value > l2 : value < l2) { return "§c"; }
		else if (rev ? value > l3 : value < l3) { return "§6"; }
		else if (rev ? value > l4 : value < l4) { return "§e"; }
		else if (rev ? value > l5 : value < l5) { return "§a"; }
		else { return "§2"; }
	}
}
