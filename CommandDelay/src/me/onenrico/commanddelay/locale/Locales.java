package me.onenrico.commanddelay.locale;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.main.Core;

public class Locales {
	public static void setup() {
		message_command_nomoney = get("message_command_nomoney", message_command_nomoney);
		message_command_nopermission = get("message_command_nopermission", message_command_nopermission);
		message_command_reload = get("message_command_reload", message_command_reload);
		if (ConfigPlugin.changed) {
			Core.getThis().saveDefaultConfig();
		}
	}

	public static String get(String a, String b) {
		return ConfigPlugin.getStr(a, b);
	}

	public static String message_command_nomoney = "You Don't Have Enough Money";
	public static String message_command_nopermission = "You Don't Have &8[&f{perm}&8] &cPermission !";
	public static String message_command_reload = "&6Config Reloaded";

}
