// AlumModMenu.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

/** Provides the modmenu entrypoint. */
@Environment(EnvType.CLIENT)
public final class AlumModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent -> AutoConfig.getConfigScreen(AlumConfig.class, parent).get());
	}
}
