// ModuleZoom.java
// Zoom module class for Alum
// Copyright (C) 2021, Jakob Wakeling
// All rights reserved.

package net.omkov.alum.module;

import net.omkov.alum.Alum;

/** The ModuleZoom class provides zoom functionality. */
public final class ModuleZoom extends Module {
	private static final double factor = 4.0;
	private Double sensitivity;
	
	/** Update FOV and mouse sensitivity for zoom. */
	public double zoom(double fov) {
		if (!Alum.CS.binds.zoom.isPressed()) {
			for (; sensitivity != null; sensitivity = null) {
				setEnabled(false); Alum.MC.options.mouseSensitivity = sensitivity;
			}
			
			return fov;
		}
		
		if (sensitivity == null) {
			setEnabled(true); sensitivity = Alum.MC.options.mouseSensitivity;
			Alum.MC.options.mouseSensitivity = sensitivity * (fov / factor / fov);
		}
		
		return fov / factor;
	}
}
