// AbstractInventoryScreenMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
	public AbstractInventoryScreenMixin(T screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(method = "applyStatusEffectOffset", at = @At("RETURN"))
	private void applyStatusEffectOffset(CallbackInfo ci) {
		this.x = (this.width - this.backgroundWidth) / 2;
	}
}
