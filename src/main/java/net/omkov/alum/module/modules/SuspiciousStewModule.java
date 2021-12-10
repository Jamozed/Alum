// SuspiciousStewModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.omkov.alum.module.Module;

/** Provides status effect tooltips for suspicious stew. */
public class SuspiciousStewModule extends Module {
	public SuspiciousStewModule() {
		setEnabled(true);
		
		ItemTooltipCallback.EVENT.register((stack, tooltip, lines) -> {
			if (!isEnabled()) { return; }
			
			if (stack.getItem() == Items.SUSPICIOUS_STEW) { buildTooltip(stack, lines); }
		});
	}
	
	/** Build a status effect tooltip for suspicious stew. */
	public static void buildTooltip(ItemStack stack, List<Text> lines) {
		if (stack.getNbt() == null) { return; }
		
		NbtList nbtList = stack.getNbt().getList("Effects", 10);
		List<Pair<EntityAttribute, EntityAttributeModifier>> attrList = Lists.newArrayList();
		
		if (nbtList.isEmpty()) { lines.add(new TranslatableText("effect.none").formatted(Formatting.GRAY)); }
		else {
			for (int i = 0; i != nbtList.size(); ++i) {
				/* Decode the NBT tags for each status effect */
				StatusEffect sE = StatusEffect.byRawId(nbtList.getCompound(i).getByte("EffectId"));
				TranslatableText text = new TranslatableText(sE.getTranslationKey());
				int duration = nbtList.getCompound(i).getInt("EffectDuration");
				
				Map<EntityAttribute, EntityAttributeModifier> map = sE.getAttributeModifiers();
				for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : map.entrySet()) {
					/* Generate attribute modifiers for each status effect */
					EntityAttributeModifier eAM0 = (EntityAttributeModifier)entry.getValue();
					EntityAttributeModifier eAM1 = new EntityAttributeModifier(eAM0.getName(),
						sE.adjustModifierAmount(0, eAM0), eAM0.getOperation());
					
					attrList.add(new Pair<>(entry.getKey(), eAM1));
				}
				
				if (duration >= 20) {
					/* Append effect duration if longer than a second */
					String time = String.format("%d:%02d", duration / 1200, (duration % 1200 ) / 20);
					text = new TranslatableText("potion.withDuration", new Object[] { text, time });
				}
				
				lines.add(text.formatted(sE.getCategory().getFormatting()));
			}
			
			if (!attrList.isEmpty()) {
				lines.add(LiteralText.EMPTY);
				lines.add((new TranslatableText("potion.whenDrank")).formatted(Formatting.DARK_PURPLE));
				
				for (Pair<EntityAttribute, EntityAttributeModifier> attr : attrList) {
					EntityAttributeModifier eAM = attr.getSecond();
					
					double g = eAM.getValue();
					switch (eAM.getOperation()) { case MULTIPLY_BASE: case MULTIPLY_TOTAL: { g *= 100.0; } default: {} }
					
					if (eAM.getValue() > 0.0) {
						/* Add any positive attribute modifier */
						lines.add((new TranslatableText("attribute.modifier.plus." + eAM.getOperation().getId(), new Object[] {
							ItemStack.MODIFIER_FORMAT.format(g), new TranslatableText(attr.getFirst().getTranslationKey())
						})).formatted(Formatting.BLUE));
					}
					else if (eAM.getValue() < 0.0) {
						/* Add any negative attribute modifier */
						lines.add((new TranslatableText("attribute.modifier.take." + eAM.getOperation().getId(), new Object[] {
							ItemStack.MODIFIER_FORMAT.format(-g), new TranslatableText(attr.getFirst().getTranslationKey())
						})).formatted(Formatting.RED));
					}
				}
			}
		}
	}
}
