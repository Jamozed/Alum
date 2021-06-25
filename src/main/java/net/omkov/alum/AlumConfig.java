// AlumConfig.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "alum")
public class AlumConfig implements ConfigData {
	boolean toggleAlum = true;
}
