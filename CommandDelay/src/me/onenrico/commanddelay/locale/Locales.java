package me.onenrico.commanddelay.locale;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.utils.MessageUT;

public class Locales {
	public static void setup() {
		message_command_nomoney = get("message_command_nomoney", message_command_nomoney);
		message_command_nopermission = get("message_command_nopermission", message_command_nopermission);
		message_command_reload = get("message_command_reload", message_command_reload);
		if (ConfigPlugin.changed) {
			Core.getThis().saveDefaultConfig();
		}
		config = ConfigPlugin.getConfig();
	}

	private static FileConfiguration config;

	public static String get(String a, String b) {
		return ConfigPlugin.getStr(a, b);
	}

	public static List<String> getStrList(String path) {
		return config.getStringList(path);
	}

	public static List<String> getStrList(String path, List<String> def) {
		if (config.getStringList(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getStringList(path);
	}

	public static int getInt(String path, int def) {
		if (config.getString(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getInt(path, def);
	}

	public static String getStr(String path) {
		return MessageUT.t(config.getString(path));
	}

	public static int getInt(String path) {
		return config.getInt(path);
	}

	public static Boolean getBool(String path) {
		return config.getBoolean(path);
	}

	public static Boolean getBool(String path, Boolean def) {
		if (config.get(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getBoolean(path, def);
	}

	public static String message_command_nomoney = "You Don't Have Enough Money";
	public static String message_command_nopermission = "You Don't Have &8[&f{perm}&8] &cPermission !";
	public static String message_command_reload = "&6Config Reloaded";

}
