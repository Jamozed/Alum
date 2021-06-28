// AlumClient.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

/** Provides the client entrypoint. */
@Environment(EnvType.CLIENT)
public final class AlumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { Alum.init(); }
}
