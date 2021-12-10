// ItemStackMixin.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import java.util.List;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "getTooltip", at = @At("RETURN"))
	public void getTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
		if (Alum.modules.tooltips.isEnabled()) {
			/* Calculate map tooltip offset if the tooltips module is enabled */
			float offset = 3.5f + (cir.getReturnValue().size() - 1) * 12;
			Alum.modules.tooltips.setMapOffset(context.isAdvanced() ? offset - 8.0f : offset);
		}
	}
}
