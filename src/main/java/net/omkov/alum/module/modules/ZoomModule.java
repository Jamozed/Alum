// ZoomModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.omkov.alum.Alum;
import net.omkov.alum.module.Module;

/** Provides zoom functionality. */
public final class ZoomModule extends Module {
	private static final double factor = 4.0;
	private Double sensitivity;
	
	/** Update FOV and mouse sensitivity for zoom. */
	public double zoom(double fov) {
		if (Alum.bindings.zoom.isPressed()) {
			if (sensitivity == null) {
				/* Decrease mouse sensitivity on zoom enable */
				setEnabled(true); sensitivity = MC.options.mouseSensitivity;
				MC.options.mouseSensitivity = sensitivity * (fov / factor / fov);
			} return fov / factor;
		}
		
		for (; sensitivity != null; sensitivity = null) {
			/* Restore mouse sensitivity on zoom disable */
			setEnabled(false); MC.options.mouseSensitivity = sensitivity;
		} return fov;
	}
}
