// MixinSimpleOption.java
// Copyright (C) 2022, Jakob Wakeling
// MIT Licence

package net.omkov.alum.mixin;

import com.mojang.serialization.Codec;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.SimpleOption.Callbacks;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.omkov.alum.callback.GammaSliderCallbacks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(SimpleOption.class)
abstract class SimpleOptionMixin {
	@Shadow @Final private Text text;
	@Shadow @Final @Mutable private Callbacks<Double> callbacks;
	@Shadow @Final @Mutable private Codec<Double> codec;
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(CallbackInfo info) throws Exception {
		TextContent content = this.text.getContent();
		if (!(content instanceof TranslatableTextContent)) { return; }
		
		String key = ((TranslatableTextContent) content).getKey();
		if (!key.equals("options.gamma")) { return; }
		
		this.callbacks = GammaSliderCallbacks.INSTANCE;
		this.codec = this.callbacks.codec();
	}
}
