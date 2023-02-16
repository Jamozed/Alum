// AlumConfig.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.ConfigData;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Config(name = Alum.ID)
public class AlumConfig implements ConfigData {
	@ConfigEntry.Category("hud")
	@ConfigEntry.Gui.TransitiveObject
	public HudConfig hud = new HudConfig();
	public static class HudConfig {
		public boolean toggleCoords = true;
		public boolean toggleCoordsAlt = true;
		public boolean toggleFps = true;
		public boolean togglePing = true;
		public boolean toggleTps = true;
		
		public boolean toggleMountedHunger = true;
		public boolean toggleMountedXp = true;
		public boolean togglePlayerListPing = true;
	}
	
	public boolean keepChatHistory = true;
	public boolean toggleMapTooltips = true;
	
	public int fastClickSpeed = 1;
}
