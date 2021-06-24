// ZoomModule.java
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module.modules;

import net.omkov.alum.Alum;
import net.omkov.alum.module.Module;

/** The ModuleZoom class provides zoom functionality. */
public final class ZoomModule extends Module {
	private static final double factor = 4.0;
	private Double sensitivity;
	
	/** Update FOV and mouse sensitivity for zoom. */
	public double zoom(double fov) {
		if (Alum.CS.binds.zoom.isPressed()) {
			if (sensitivity == null) {
				/* Decrease mouse sensitivity on zoom enable */
				setEnabled(true); sensitivity = Alum.MC.options.mouseSensitivity;
				Alum.MC.options.mouseSensitivity = sensitivity * (fov / factor / fov);
			} return fov / factor;
		}
		
		for (; sensitivity != null; sensitivity = null) {
			/* Restore mouse sensitivity on zoom disable */
			setEnabled(false); Alum.MC.options.mouseSensitivity = sensitivity;
		} return fov;
	}
}
