// TooltipDataConvertible.java
// TooltipData convertible interface for MCCM
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;

/** The TooltipDataConvertible interface allows conversion to TooltipData. */
public interface TooltipDataConvertible extends TooltipData {
	TooltipComponent getComponent();
}
