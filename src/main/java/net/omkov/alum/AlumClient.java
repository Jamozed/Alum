// AlumClient.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

/** Provides client and modmenu entrypoints. */
@Environment(EnvType.CLIENT)
public final class AlumClient implements ClientModInitializer, ModMenuApi {
	@Override
	public void onInitializeClient() { Alum.init(); }
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent -> AutoConfig.getConfigScreen(AlumConfig.class, parent).get());
	}
}
