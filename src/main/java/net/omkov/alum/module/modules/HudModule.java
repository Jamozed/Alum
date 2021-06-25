// HudModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.util.math.MatrixStack;
import net.omkov.alum.mixin.MinecraftClientAccessor;
import net.omkov.alum.module.Module;

/** Provides additional HUD elements. */
@Environment(EnvType.CLIENT)
public class HudModule extends Module {
	public HudModule() { setEnabled(true); }
	
	public void draw(MatrixStack matrices) {
		if (!isEnabled()) { return; }
		
		if (MC.getWindow() == null || MC.textRenderer == null) { return; }
		
		// RenderSystem.enableBlend();
		
		drawInfo(matrices);
		
		// this.client.getProfiler().pop();
	}
	
	/** Draw game info in the bottom left corner. */
	private void drawInfo(MatrixStack matrices) {
		int lineH = MC.textRenderer.fontHeight + 2;
		int lineY = MC.getWindow().getScaledHeight() - lineH;
		
		int fps = ((MinecraftClientAccessor)MC).getCurrentFps();
		
		{
			int lineX = 2;
			
			double x = MC.player.getX(), y = MC.player.getY(), z = MC.player.getZ();
			
			String format = "%.0f %.0f %.0f", xyz = String.format(format, x, y, z);
			
			MC.textRenderer.drawWithShadow(matrices, "XYZ: ", lineX, lineY, 0x00E0E0E0);
			lineX += MC.textRenderer.getWidth("XYZ: ");
			
			MC.textRenderer.drawWithShadow(matrices, xyz, lineX, lineY, 0x0000AA00);
			lineX += MC.textRenderer.getWidth(xyz);
			
			// {
			// 	xyz = " [" + String.format(format, x / 8, y / 8, z / 8) + "]";
				
			// 	textRenderer.drawWithShadow(matrices, xyz, lineX, lineY, 0x00AA0000);
			// }
			
			lineX = 2; lineY -= lineH;
			MC.textRenderer.drawWithShadow(matrices, "FPS: " + String.valueOf(fps), lineX, lineY, 0x00E0E0E0);
		}
	}
}
