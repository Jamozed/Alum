// AlumClient.java
// Client entrypoint class for Alum
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import net.fabricmc.api.ClientModInitializer;

/** The AlumClient class provides a client entrypoint for Alum. */
public final class AlumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { Alum.CS.initialize(); }
}
