// AlumClient.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import net.fabricmc.api.ClientModInitializer;

/** The AlumClient class provides a client entrypoint. */
public final class AlumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { Alum.init(); }
}
