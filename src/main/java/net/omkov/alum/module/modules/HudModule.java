// HudModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.Window;
import net.omkov.alum.mixin.MinecraftClientAccessor;
import net.omkov.alum.module.Module;

/** Provides additional HUD elements. */
@Environment(EnvType.CLIENT)
public class HudModule extends Module {
	public static HudModule INSTANCE;
	
	private final MinecraftClient client;
	private final Window window;
	private final TextRenderer textRenderer;
	
	public HudModule(MinecraftClient client) {
		setEnabled(true); this.client = client;
		this.window = client.getWindow();
		this.textRenderer = client.textRenderer;
	}
	
	public void draw(MatrixStack matrices) {
		if (!isEnabled()) { return; }
		
		// RenderSystem.enableBlend();
		
		drawInfo(matrices);
		
		// this.client.getProfiler().pop();
	}
	
	/** Draw game info in the bottom left corner. */
	private void drawInfo(MatrixStack matrices) {
		int lineH = textRenderer.fontHeight + 2;
		int lineY = window.getScaledHeight() - lineH;
		
		int fps = ((MinecraftClientAccessor)client).getCurrentFps();
		
		{
			int lineX = 2;
			
			double x = client.player.getX(), y = client.player.getY(), z = client.player.getZ();
			
			String format = "%.0f %.0f %.0f", xyz = String.format(format, x, y, z);
			
			textRenderer.drawWithShadow(matrices, "XYZ: ", lineX, lineY, 0x00E0E0E0);
			lineX += textRenderer.getWidth("XYZ: ");
			
			textRenderer.drawWithShadow(matrices, xyz, lineX, lineY, 0x0000AA00);
			lineX += textRenderer.getWidth(xyz);
			
			// {
			// 	xyz = " [" + String.format(format, x / 8, y / 8, z / 8) + "]";
				
			// 	textRenderer.drawWithShadow(matrices, xyz, lineX, lineY, 0x00AA0000);
			// }
			
			lineX = 2; lineY -= lineH;
			textRenderer.drawWithShadow(matrices, "FPS: " + String.valueOf(fps), lineX, lineY, 0x00E0E0E0);
		}
	}
}
