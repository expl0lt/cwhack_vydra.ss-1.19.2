package net.io.fabric;

import net.io.fabric.config.core.CrystalDataTracker;
import net.io.fabric.config.core.PlayerActionScheduler;
import net.io.fabric.config.core.Rotator;
import net.io.fabric.loader.event.EventManager;
import net.io.fabric.loader.gui.ClickGui;
import net.io.fabric.keybind.KeybindManager;
import net.io.fabric.loader.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public enum antarctica
{

	INSTANCE;

	public static MinecraftClient MC;

	private EventManager eventManager;
	private ModuleManager moduleManager;
	private KeybindManager keybindManager;
	private ClickGui gui;
	private Boolean guiInitialized = false;
	private CrystalDataTracker crystalDataTracker;
	private PlayerActionScheduler playerActionScheduler;
	private Rotator rotator;
	private Session session;
	private String hwid;

	public void init() {
		MC = MinecraftClient.getInstance();
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		keybindManager = new KeybindManager();
		gui = new ClickGui();
		crystalDataTracker = new CrystalDataTracker();
		playerActionScheduler = new PlayerActionScheduler();
		rotator = new Rotator();
		session = MinecraftClient.getInstance().getSession();

	}

	public void panic() {
		guiInitialized = null;
		MC = null;
		eventManager = null;
		moduleManager = null;
		keybindManager = null;
		gui = null;
		crystalDataTracker = null;
		playerActionScheduler = null;
		rotator = null;
		session = null;
		hwid = null;
	}

	public EventManager getEventManager() { return eventManager; }

	public ModuleManager getModuleManager()
	{
		return moduleManager;
	}

	public KeybindManager getKeybindManager()
	{
		return keybindManager;
	}

	public ClickGui getClickGui() {
		if (!guiInitialized) {
			gui.init();
			guiInitialized = true;
		}

		return gui;
	}

	public void updateClickGui() {
		this.gui = new ClickGui();

		if (!guiInitialized) {
			gui.init();
			guiInitialized = true;
		}
	}

	public CrystalDataTracker getCrystalDataTracker() {
		return crystalDataTracker;
	}

	public PlayerActionScheduler getPlayerActionScheduler() {
		return playerActionScheduler;
	}

	public Rotator getRotator() {
		return rotator;
	}

	public Session getSession() {
		return session;
	}

	public String getHwid() {
		return hwid;
	}

}
