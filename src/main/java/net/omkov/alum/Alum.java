// Alum.java
// Copyright (C) 2020, Jakob Wakeling
// All rights reserved.

package net.omkov.alum;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.omkov.alum.event.InGameHudEvents;
import net.omkov.alum.module.modules.FastClickModule;
import net.omkov.alum.module.modules.GammaModule;
import net.omkov.alum.module.modules.HudModule;
import net.omkov.alum.module.modules.MuteModule;
import net.omkov.alum.module.modules.SuspiciousStewModule;
import net.omkov.alum.module.modules.TooltipModule;
import net.omkov.alum.module.modules.VisionModule;
import net.omkov.alum.module.modules.ZoomModule;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Provides global data storage for Alum. */
@Environment(EnvType.CLIENT)
public final class Alum implements ClientModInitializer, ModMenuApi {
	public static final String ID = "alum";
	public static final Logger LOGGER = LoggerFactory.getLogger("Alum");
	
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final AlumConfig CONFIG = AutoConfig.register(AlumConfig.class, Toml4jConfigSerializer::new).getConfig();
	
	public static BindingList bindings;
	public static ModuleList modules;
	
	@Override
	public void onInitializeClient() {
		bindings = new BindingList();
		modules = new ModuleList();
	}
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent -> AutoConfig.getConfigScreen(AlumConfig.class, parent).get());
	}
	
	/** Stores keybinding instances for Alum. */
	public static final class BindingList {
		public final KeyBinding fastClick = bind("key.alum.fast_click", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.alum");
		public final KeyBinding gamma = bind("key.alum.gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.alum");
		public final KeyBinding mute = bind("key.alum.mute", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.alum");
		public final KeyBinding vision = bind("key.alum.vision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.alum");
		public final KeyBinding zoom = bind("key.alum.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.misc");
		
		/** Construct and register a keybinding. */
		private KeyBinding bind(String key, InputUtil.Type type, int code, String category) {
			KeyBinding bind = new KeyBinding(key, type, code, category);
			return KeyBindingHelper.registerKeyBinding(bind);
		}
	}
	
	/** Stores module instances for Alum. */
	public static final class ModuleList {
		private HudModule hudModule;
		
		public final FastClickModule fastClick = new FastClickModule();
		public final GammaModule gamma = new GammaModule();
		public final MuteModule mute = new MuteModule();
		public final SuspiciousStewModule suspiciousStew = new SuspiciousStewModule();
		public final TooltipModule tooltips = new TooltipModule();
		public final VisionModule vision = new VisionModule();
		public final ZoomModule zoom = new ZoomModule();
		
		public ModuleList() {
			InGameHudEvents.INIT.register((client) -> { hudModule = new HudModule(); });
			HudRenderCallback.EVENT.register((context, tickDelta) -> { hudModule.draw(context); });
		}
	}
}
