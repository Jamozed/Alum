// MCCMClient.java
// Client entrypoint class for MCCM
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm;

import net.fabricmc.api.ClientModInitializer;

/** The MCCMClient class provides a client entrypoint for MCCM. */
public final class MCCMClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { MCCM.CM.initialize(); }
}
