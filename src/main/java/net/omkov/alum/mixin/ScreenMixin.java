// ScreenMixin.java
// Screen mixin for Alum
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import java.util.List;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.omkov.alum.tooltip.TooltipDataConvertible;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Screen.class)
public class ScreenMixin {
	@Inject(method = "method_32635", at = @At("HEAD"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void onComponentConstruct(List<TooltipComponent> list, TooltipData data, CallbackInfo info) {
		if (data instanceof TooltipDataConvertible convertible) {
			list.add(convertible.getComponent()); info.cancel();
		}
	}
}
