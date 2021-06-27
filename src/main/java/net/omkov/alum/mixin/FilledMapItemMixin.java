// FilledMapItemMixin.java
// FilledMapItem mixin for Alum
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.mixin;

import java.util.Optional;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.omkov.alum.tooltip.TooltipMap;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(FilledMapItem.class)
public abstract class FilledMapItemMixin extends Item {
	public FilledMapItemMixin(Settings settings) { super(settings); }

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		return TooltipMap.of(stack).or(() -> super.getTooltipData(stack));
	}
}
