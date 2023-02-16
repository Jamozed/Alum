// InGameHudMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import net.omkov.alum.Alum;
import net.omkov.alum.event.InGameHudEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
abstract class InGameHudMixin {
	@Shadow @Final
	private MinecraftClient client;
	
	@Shadow
	private int getHeartCount(LivingEntity entity) { /* IGNORED */ return 0; }
	
	@Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/render/item/ItemRenderer;)V", at = @At(value = "RETURN"))
	private void onInit(MinecraftClient client, ItemRenderer render, CallbackInfo ci) {
		InGameHudEvents.INIT.invoker().onInit(client);
	}
	
	@Redirect(
		method = "render", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/network/ClientPlayerEntity;getJumpingMount()Lnet/minecraft/entity/JumpingMount;")
	)
	private JumpingMount render_getJumpingMount(ClientPlayerEntity self) {
		/* Only render jump bar when jumping if mounted XP is enabled */
		if (Alum.CONFIG.hud.toggleMountedXp && client.interactionManager.hasExperienceBar()) {
			if (!client.options.jumpKey.isPressed() && self.getMountJumpStrength() <= 0.0f) {
				return null;
			}
		}
		return self.getJumpingMount();
	}
	
	@Redirect(
		method = "renderStatusBars", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I")
	)
	private int renderStatusBars_getHeartCount(InGameHud self, LivingEntity entity) {
		/* Behave as if unmounted when rendering status bars if mounted hunger is enabled */
		if (Alum.CONFIG.hud.toggleMountedHunger) { return 0; }
		return this.getHeartCount(entity);
	}
	
	@ModifyVariable(method = "renderMountHealth", at = @At(value = "STORE"), ordinal = 2)
	private int renderMountHealth_height(int y) {
		/* Move mount health bar up if mounted hunger is enabled */
		if (Alum.CONFIG.hud.toggleMountedHunger && client.interactionManager.hasStatusBars()) { y -= 10; }
		return y;
	}
}
