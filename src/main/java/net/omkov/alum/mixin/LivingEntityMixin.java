// LivingEntityMixin.java
// Copyright (C) 2023, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;
import net.omkov.alum.Alum;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
	@Inject(method = "hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", at = @At(value = "RETURN"), cancellable = true)
	private void hasStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (Alum.modules.vision.isEnabled()) {
			if (effect.equals(StatusEffects.BLINDNESS)) { cir.setReturnValue(false); }
			if (effect.equals(StatusEffects.DARKNESS)) { cir.setReturnValue(false); }
			if (effect.equals(StatusEffects.NAUSEA)) { cir.setReturnValue(false); }
		}
	}
}
