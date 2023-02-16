// PlayerListHudMixin.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.omkov.alum.Alum;
import net.omkov.alum.module.modules.HudModule;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
	@Shadow @Final
	private MinecraftClient client;
	
	@Shadow
	protected abstract void renderLatencyIcon(MatrixStack matrices, int width, int x, int y, PlayerListEntry entry);
	
	@ModifyConstant(method = "render", constant = @Constant(intValue = 13))
	private int modifySlotWidth(int x) { if(Alum.CONFIG.hud.togglePlayerListPing) { return x + 24; } return x; }
	
	@Redirect(
		method = "render", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/gui/hud/PlayerListHud;renderLatencyIcon" +
			"(Lnet/minecraft/client/util/math/MatrixStack;IIILnet/minecraft/client/network/PlayerListEntry;)V")
	)
	protected void renderLatencyIcon(
		PlayerListHud self, MatrixStack matrices, int width, int x, int y, PlayerListEntry entry
	) {
		if (!Alum.CONFIG.hud.togglePlayerListPing) { renderLatencyIcon(matrices, width, x, y, entry); return; }
		
		String ping = HudModule.getColour(entry.getLatency(), 600, 400, 200, 100, 50, true);
		ping += entry.getLatency() + "ms§r"; x += width - client.textRenderer.getWidth(ping);
		
		client.textRenderer.drawWithShadow(matrices, ping, x, y, 0x00E0E0E0);
		
		/* Reset the shader colour for other rendering */
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
