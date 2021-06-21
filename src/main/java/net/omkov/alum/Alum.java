// MCCM.java
// Singleton class for MCCM
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.omkov.alum.module.ModuleTooltips;
import net.omkov.alum.module.ModuleZoom;
import org.lwjgl.glfw.GLFW;

/** The MCCM singleton provides global data storage. */
public final class Alum {
	public static final Alum CS = new Alum(); private Alum() {}
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	
	public BindList binds = new BindList();
	public ModuList modus = new ModuList();
	
	/** Initialise the MCCM singleton. */
	public void initialize() {}
	
	/** The BindList class stores keybindings. */
	public final class BindList {
		public final KeyBinding zoom = bind("key.alum.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.misc");
		
		/** Construct and register a keybinding. */
		private KeyBinding bind(String key, InputUtil.Type type, int code, String category) {
			KeyBinding bind = new KeyBinding(key, type, code, category);
			return KeyBindingHelper.registerKeyBinding(bind);
		}
	}
	
	/** The ModuList class stores modules. */
	public final class ModuList {
		public final ModuleZoom zoom = new ModuleZoom();
		public final ModuleTooltips tooltips = new ModuleTooltips();
	}
}
