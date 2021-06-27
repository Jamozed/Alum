// Alum.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.omkov.alum.event.InGameHudEvents;
import net.omkov.alum.module.modules.GammaModule;
import net.omkov.alum.module.modules.HudModule;
import net.omkov.alum.module.modules.SuspiciousStewModule;
import net.omkov.alum.module.modules.TooltipModule;
import net.omkov.alum.module.modules.ZoomModule;
import org.lwjgl.glfw.GLFW;

/** Provides global data storage for Alum. */
public final class Alum {
	private static final Alum INSTANCE = new Alum(); private Alum() {}
	
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final AlumConfig CONFIG = AutoConfig.register(AlumConfig.class, Toml4jConfigSerializer::new).getConfig();
	
	public static BindingList bindings;
	public static ModuleList modules;
	
	/** Intialise the Alum singleton. */
	public static void init() {
		bindings = new BindingList();
		modules = new ModuleList();
		
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (bindings.gamma.wasPressed()) { modules.gamma.toggle(); }
		});
	}
	
	/** Return the Alum singleton instance. */
	public static Alum getInstance() { return INSTANCE; }
	
	/** Stores keybinding instances for Alum. */
	public static final class BindingList {
		public final KeyBinding gamma = bind("key.alum.gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.misc");
		public final KeyBinding zoom = bind("key.alum.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.misc");
		
		/** Construct and register a keybinding. */
		private KeyBinding bind(String key, InputUtil.Type type, int code, String category) {
			KeyBinding bind = new KeyBinding(key, type, code, category);
			return KeyBindingHelper.registerKeyBinding(bind);
		}
	}
	
	/** Stores module instances for Alum. */
	public static final class ModuleList {
		public final GammaModule gamma = new GammaModule();
		private HudModule hudModule;
		public final SuspiciousStewModule suspiciousStewModule = new SuspiciousStewModule();
		public final TooltipModule tooltips = new TooltipModule();
		public final ZoomModule zoom = new ZoomModule();
		
		public ModuleList() {
			InGameHudEvents.INIT.register((client) -> { hudModule = new HudModule(); });
			HudRenderCallback.EVENT.register((matrices, tickDelta) -> { hudModule.draw(matrices); });
		}
	}
}
