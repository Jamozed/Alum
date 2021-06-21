// ModuleZoom.java
// Zoom module class for MCCM
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.mccm.module;

import net.omkov.mccm.MCCM;

/** The ModuleZoom class provides zoom functionality. */
public final class ModuleZoom extends Module {
	private static final double factor = 4.0;
	private Double sensitivity;
	
	/** Update FOV and mouse sensitivity for zoom. */
	public double zoom(double fov) {
		if (!MCCM.CM.binds.zoom.isPressed()) {
			for (; sensitivity != null; sensitivity = null) {
				setEnabled(false); MCCM.MC.options.mouseSensitivity = sensitivity;
			}
			
			return fov;
		}
		
		if (sensitivity == null) {
			setEnabled(true); sensitivity = MCCM.MC.options.mouseSensitivity;
			MCCM.MC.options.mouseSensitivity = sensitivity * (fov / factor / fov);
		}
		
		return fov / factor;
	}
}
