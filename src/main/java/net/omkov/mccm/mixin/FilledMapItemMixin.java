// FilledMapItemMixin.java
// FilledMapItem mixin for MCCM
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm.mixin;

import java.util.Optional;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.omkov.mccm.tooltip.TooltipMap;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FilledMapItem.class)
public abstract class FilledMapItemMixin extends Item {
	public FilledMapItemMixin(Settings settings) { super(settings); }

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		return TooltipMap.of(stack).or(() -> super.getTooltipData(stack));
	}
}
