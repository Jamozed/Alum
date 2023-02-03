// HandledScreenMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.omkov.alum.Alum;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
	@Shadow @Nullable
	private Slot focusedSlot;
	
	@Inject(method = "render", at = @At("TAIL"))
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (Alum.modules.tooltips.isEnabled() && Alum.CONFIG.toggleMapTooltips) {
			/* Draw map tooltips if the tooltips module is enabled */
			if (focusedSlot != null && focusedSlot.hasStack() && focusedSlot.getStack().getItem() == Items.FILLED_MAP) {
				Alum.modules.tooltips.drawMap(matrices, FilledMapItem.getMapId(focusedSlot.getStack()), mouseX, mouseY);
			}
		}
	}
}
