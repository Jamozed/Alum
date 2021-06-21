// TooltipMap.java
// Map tooltip class for MCCM
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm.tooltip;

import java.util.Optional;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.omkov.mccm.MCCM;

/** The TooltipMap class handles drawing map tooltips. */
public class TooltipMap implements TooltipDataConvertible, TooltipComponent {
	public int map;
	
	public TooltipMap(int map) { this.map = map; }
	
	/** Construct a TooltipMap from a stack, or return an empty Optional instance. */
	public static Optional<TooltipData> of(ItemStack stack) {
		if (!MCCM.CM.modus.tooltips.isEnabled()) { return Optional.empty(); }
		
		Integer map = FilledMapItem.getMapId(stack);
		return (map == null ? Optional.empty() : Optional.of(new TooltipMap(map)));
	}
	
	@Override
	public TooltipComponent getComponent() { return this; }
	
	@Override
	public int getHeight() { return 130; }

	@Override
	public int getWidth(TextRenderer textRenderer) { return 128; }

	@Override
	public void drawItems(
		TextRenderer textRenderer, int x, int y,
		MatrixStack matrices, ItemRenderer itemRenderer,
		int z, TextureManager textureManager
	) {
		Immediate vertices = MCCM.MC.getBufferBuilders().getEffectVertexConsumers();
		MapRenderer mapRenderer = MCCM.MC.gameRenderer.getMapRenderer();
		MapState mapState = FilledMapItem.getMapState(map, MCCM.MC.world);
		
		if (mapState == null) { return; }
		
		matrices.push(); matrices.translate(x, y, z); matrices.scale(1, 1, 0);
		mapRenderer.draw(matrices, vertices, map, mapState, true, 0xF000F0);
		vertices.draw(); matrices.pop();
	}
}
