package me.onenrico.commanddelay.object;

import java.util.ArrayList;
import java.util.List;

import me.onenrico.commanddelay.config.ConfigPlugin;

public class Category {
	private double cost = 0;
	private int delay = 5;
	private String blacklist = "disabled";
	private List<String> worlds = new ArrayList<>();
	private List<String> commands = new ArrayList<>();

	public Category(String path) {
		String pref = "category.";
		cost = ConfigPlugin.getConfig().getDouble(pref + path + ".cost");
		delay = ConfigPlugin.getConfig().getInt(pref + path + ".delay");
		blacklist = ConfigPlugin.getConfig().getString(pref + path + ".blacklist");
		worlds = ConfigPlugin.getConfig().getStringList(pref + path + ".worlds");
		commands = ConfigPlugin.getConfig().getStringList(pref + path + ".commands");
	}

	public double getCost() {
		return cost;
	}

	public int getDelay() {
		return delay;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public List<String> getWorlds() {
		return worlds;
	}

	public List<String> getCommands() {
		return commands;
	}
}
