// TooltipModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.ToolItem;
import net.minecraft.text.TranslatableText;
import net.omkov.alum.module.Module;

/** Provides additional tooltip functionality. */
public class TooltipModule extends Module {
	public TooltipModule() {
		setEnabled(true);
		
		ItemTooltipCallback.EVENT.register((stack, tooltip, lines) -> {
			if (!isEnabled()) { return; }
			
			int offset = (tooltip.isAdvanced() ? (stack.hasTag() ? 2 : 1) : 0);
			
			/* Durability */
			if (stack.isDamageable()) {
				/* Always show a durability tooltip if an item is damageable */
				TranslatableText durability = new TranslatableText("item.durability", new Object[] {
					stack.getMaxDamage() - stack.getDamage(), stack.getMaxDamage()
				});
				
				if (!lines.contains(durability)) { lines.add(lines.size() - offset, durability); }
			}
			
			/* Mining Level */
			if (stack.getItem() instanceof ToolItem) {
				int miningLevel = ((ToolItem)stack.getItem()).getMaterial().getMiningLevel();
				lines.add(new TranslatableText("item.mining_level", new Object[] { miningLevel }));
			}
		});
	}
}
