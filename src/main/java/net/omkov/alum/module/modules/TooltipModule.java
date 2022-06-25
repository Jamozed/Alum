// TooltipModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.map.MapState;
import net.minecraft.item.ToolItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.omkov.alum.module.Module;

/** Provides additional tooltip functionality. */
public class TooltipModule extends Module {
	private final Identifier MAP_BACKGROUND = new Identifier("textures/map/map_background.png");
	
	private float mapOffset = 0.0f;
	
	public TooltipModule() {
		setEnabled(true);
		
		ItemTooltipCallback.EVENT.register((stack, tooltip, lines) -> {
			if (!isEnabled()) { return; }
			
			int offset = (tooltip.isAdvanced() ? (stack.hasNbt() ? 2 : 1) : 0);
			
			/* Durability */
			if (stack.isDamageable()) {
				/* Always show a durability tooltip if an item is damageable */
				MutableText durability = Text.translatable("item.durability", new Object[] {
					stack.getMaxDamage() - stack.getDamage(), stack.getMaxDamage()
				});
				
				if (!lines.contains(durability)) { lines.add(lines.size() - offset, durability); }
			}
			
			/* Mining Level */
			if (stack.getItem() instanceof ToolItem) {
				int miningLevel = ((ToolItem)stack.getItem()).getMaterial().getMiningLevel();
				lines.add(Text.translatable("item.mining_level", new Object[] { miningLevel }));
			}
		});
	}
	
	public void setMapOffset(float offset) { this.mapOffset = offset; }
	
	/** Draw a map tooltip at the specified coordinates. */
	public void drawMap(MatrixStack matrices, Integer id, int x, int y) {
		Tessellator tessellator = Tessellator.getInstance();
		MapState mapState = FilledMapItem.getMapState(id, MC.world);
		
		matrices.push();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, MAP_BACKGROUND);
		
		matrices.translate(x + 11.5f, y + mapOffset, 300);
		matrices.scale(0.5f, 0.5f, 0);
		
		BufferBuilder buffer = tessellator.getBuffer();
		Matrix4f matrix4f = matrices.peek().getPositionMatrix();
		
		buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		buffer.vertex(matrix4f, -7.5f,    135.5f, 0).texture(0, 1).next();
		buffer.vertex(matrix4f,  135.5f,  135.5f, 0).texture(1, 1).next();
		buffer.vertex(matrix4f,  135.5f, -7.5f,   0).texture(1, 0).next();
		buffer.vertex(matrix4f, -7.5f,   -7.5f,   0).texture(0, 0).next();
		
		tessellator.draw();
		
		if (id != null && mapState != null) {
			matrices.translate(0, 0, 1);
			
		    VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(buffer);
		    MC.gameRenderer.getMapRenderer().draw(matrices, immediate, id, mapState, true, 0xF000F0);
			
		    immediate.draw();
		}
		
		matrices.pop();
	}
}
