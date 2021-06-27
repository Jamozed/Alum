// AlumConfig.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;

@Config(name = "alum")
public class AlumConfig implements ConfigData {
	public boolean toggleCoords = true;
	public boolean toggleCoordsAlt = true;
	public boolean toggleTps = true;
	public boolean toggleFps = true;
	public boolean togglePing = true;
}
