package me.onenrico.commanddelay.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.events.OtherEvent;
import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.nms.sound.SoundManager;
import me.onenrico.commanddelay.utils.LoadingbarUT.barType;

public class DelayUT {
	public static void cancelTeleport(Player player) {
		if (MetaUT.isThere(player, "CDelay:Teleporting")) {
			player.removeMetadata("CDelay:Teleporting", Core.getThis());
		}
	}

	public static Boolean message_actionbar_reverse = ConfigPlugin.getConfig().getBoolean("message_actionbar_reverse",
			true);
	public static String default_message_actionbar_cooldown;
	public static String default_message_actionbar_dispatched;
	public static String default_message_titlebar_delaying;
	public static String default_message_titlebar_delaying_subtitle;
	public static String default_message_titlebar_delaying_canceled;
	public static String default_message_titlebar_delaying_canceled_subtitle;
	public static String default_message_titlebar_dispatched;
	public static String default_message_titlebar_dispatched_subtitle;

	public static String default_message_chat_delaying;
	public static String default_message_chat_delaying_subtitle;
	public static String default_message_chat_delaying_canceled;
	public static String default_message_chat_dispatched;

	public static String message_actionbar_cooldown;
	public static String message_actionbar_dispatched;
	public static String message_titlebar_delaying;
	public static String message_titlebar_delaying_subtitle;
	public static String message_titlebar_delaying_canceled;
	public static String message_titlebar_delaying_canceled_subtitle;
	public static String message_titlebar_dispatched;
	public static String message_titlebar_dispatched_subtitle;

	public static String message_chat_delaying;
	public static String message_chat_delaying_subtitle;
	public static String message_chat_delaying_canceled;
	public static String message_chat_dispatched;
	public static Boolean chat = true;
	public static Boolean titlebar = true;
	public static Boolean actionbar = true;

	public static void setup() {
		DelayUT.default_message_actionbar_cooldown = ConfigPlugin.getStr("message_actionbar_cooldown");
		DelayUT.default_message_actionbar_dispatched = ConfigPlugin.getStr("message_actionbar_dispatched");
		DelayUT.default_message_titlebar_dispatched = ConfigPlugin.getStr("message_titlebar_dispatched");
		DelayUT.default_message_titlebar_dispatched_subtitle = ConfigPlugin
				.getStr("message_titlebar_dispatched_subtitle");
		DelayUT.default_message_titlebar_delaying = ConfigPlugin.getStr("message_titlebar_delaying");
		DelayUT.default_message_titlebar_delaying_subtitle = ConfigPlugin.getStr("message_titlebar_delaying_subtitle");
		DelayUT.default_message_titlebar_delaying_canceled = ConfigPlugin.getStr("message_titlebar_delaying_canceled");
		DelayUT.default_message_titlebar_delaying_canceled_subtitle = ConfigPlugin
				.getStr("message_titlebar_" + "delaying_canceled_subtitle");

		DelayUT.default_message_chat_dispatched = ConfigPlugin.getStr("message_chat_dispatched");
		DelayUT.default_message_chat_delaying = ConfigPlugin.getStr("message_chat_delaying");
		DelayUT.default_message_chat_delaying_subtitle = ConfigPlugin.getStr("message_chat_delaying_subtitle");
		DelayUT.default_message_chat_delaying_canceled = ConfigPlugin.getStr("message_chat_delaying_canceled");

		DelayUT.chat = ConfigPlugin.getChat("chat");
		DelayUT.titlebar = ConfigPlugin.getChat("titlebar");
		DelayUT.actionbar = ConfigPlugin.getChat("actionbar");
	}

	public static List<String> themeColor = ConfigPlugin.getConfig().getStringList("themecolor");
	public static List<String> flatColor = ConfigPlugin.getConfig().getStringList("themecolor");

	private static HashMap<String, String> listPlaceholder = new HashMap<>();

	public static void refreshString(String flat, String theme, int leftInt, Player player, String command) {
		Integer xstr = player.getLocation().getBlockX();
		String x = xstr.toString();
		Integer ystr = player.getLocation().getBlockY();
		String y = ystr.toString();
		Integer zstr = player.getLocation().getBlockZ();
		String z = zstr.toString();
		String playername = player.getName();
		String left = "" + leftInt;
		listPlaceholder.clear();
		listPlaceholder.put("flatcolor", flat);
		listPlaceholder.put("themecolor", theme);
		listPlaceholder.put("player", playername);
		listPlaceholder.put("left", left);
		listPlaceholder.put("x", x);
		listPlaceholder.put("y", y);
		listPlaceholder.put("z", z);
		listPlaceholder.put("cmd", command);
		PlaceholderUT p = new PlaceholderUT(listPlaceholder);
		message_titlebar_dispatched = p.t(default_message_titlebar_dispatched);
		message_titlebar_dispatched_subtitle = p.t(default_message_titlebar_dispatched_subtitle);
		message_titlebar_delaying = p.t(default_message_titlebar_delaying);
		message_titlebar_delaying_subtitle = p.t(default_message_titlebar_delaying_subtitle);
		message_titlebar_delaying_canceled = p.t(default_message_titlebar_delaying_canceled);
		message_titlebar_delaying_canceled_subtitle = p.t(default_message_titlebar_delaying_canceled_subtitle);

		message_chat_dispatched = p.t(default_message_chat_dispatched);
		message_chat_delaying = p.t(default_message_chat_delaying);
		message_chat_delaying_subtitle = p.t(default_message_chat_delaying_subtitle);
		message_chat_delaying_canceled = p.t(default_message_chat_delaying_canceled);

		message_actionbar_cooldown = p.t(default_message_actionbar_cooldown);
		message_actionbar_dispatched = p.t(default_message_actionbar_dispatched);

	}

	public static void Delayed(Player player, int cooldown, String cmd, double cost) {
		if (!EconomyUT.has(player, cost)) {
			MessageUT.plmessage(player, Locales.message_command_nomoney, true);
			SoundManager.playSound(player, "ENTITY_BLAZE_DEATH", player.getLocation(), 5f, 25f);
			return;
		}
		int min = cooldown;
		int max = min;
		int totalBar = 18;
		player.setMetadata("CDelay:Teleporting", MetaUT.createMetadata(true));
		SoundManager.playSound(player, "ENTITY_ENDERMEN_TELEPORT", player.getLocation(), 1f, -50f);
		BukkitRunnable task = new BukkitRunnable() {
			int updateMin = min + 1;
			Random random = new Random();
			String theme = null;
			String flat = null;
			int update = 1;
			int current = 0;

			private void varUpdate() {
				current++;
				updateMin--;
			}

			boolean first = true;

			@Override
			public void run() {
				if (current == update) {
					theme = null;
					current = 0;
				}
				if (theme == null) {
					theme = themeColor.get(random.nextInt(themeColor.size()));
					flat = flatColor.get(random.nextInt(flatColor.size()));
				}
				varUpdate();
				refreshString(flat, theme, updateMin, player, cmd);
				if (updateMin == -1) {
					cancel();
					Core.getThis();
					EconomyUT.subtractBal(player, cost);
					SoundManager.playSound(player, "ENTITY_ENDERMEN_TELEPORT", player.getLocation(), 1f, 50f);
					SoundManager.playSound(player, "ENTITY_ENDERMEN_TELEPORT", player.getLocation(), 1f, 40f);
					SoundManager.playSound(player, "ENTITY_ENDERMEN_TELEPORT", player.getLocation(), 1f, 20f);
					SoundManager.playSound(player, "ENTITY_ENDERMEN_TELEPORT", player.getLocation(), 1f, 10f);
					refreshString(flat, theme, updateMin, player, cmd);
					MessageUT.acmessage(player, "");
					if (titlebar) {
						MessageUT.tfullmessage(player, message_titlebar_dispatched,
								message_titlebar_dispatched_subtitle, 0, 40, 20);
					}
					if (chat) {
						MessageUT.pmessage(player, message_chat_dispatched);
					}
					if (actionbar) {
						MessageUT.acmessage(player, message_actionbar_dispatched);
					}

					// The Command
					OtherEvent.delayed.add(player);
					Bukkit.getServer().dispatchCommand(player, cmd);
					//

					return;
				}
				if (chat) {
					if (first) {
						first = false;
						MessageUT.pmessage(player, message_chat_delaying);
					}
					MessageUT.pmessage(player, message_chat_delaying_subtitle);
				}
				if (titlebar) {
					MessageUT.tfullmessage(player, message_titlebar_delaying, message_titlebar_delaying_subtitle, 0, 40,
							0);
				}
				if (actionbar) {
					MessageUT.acmessage(player,
							LoadingbarUT.getBar(totalBar, updateMin, max, barType.FULL_HIGH,
									ConfigPlugin.getConfig().getBoolean("message_actionbar_reverse"), theme, flat)
									+ "&r " + message_actionbar_cooldown);
				}
				SoundManager.playSound(player, "ENTITY_ITEM_PICKUP", player.getLocation(), 1f, 29f);
			}

		};
		task.runTaskTimer(Core.getThis(), 0, 20);
		new BukkitRunnable() {

			String theme = null;
			Random random = new Random();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (OtherEvent.delayed.contains(player)) {
					OtherEvent.delayed.remove(player);
					cancelTeleport(player);
					cancel();
					task.cancel();
					return;
				}
				if (!MetaUT.isThere(player, "CDelay:Teleporting")) {
					theme = themeColor.get(random.nextInt(themeColor.size()));
					String flat = flatColor.get(random.nextInt(flatColor.size()));
					refreshString(flat, theme, 0, player, cmd);
					if (chat) {
						MessageUT.pmessage(player, message_chat_delaying_canceled);
					}
					if (titlebar) {
						MessageUT.tfullmessage(player, message_titlebar_delaying_canceled,
								message_titlebar_delaying_canceled_subtitle);
					}
					if (actionbar) {
						MessageUT.acmessage(player, "");
					}
					SoundManager.playSound(player, "ENTITY_BLAZE_DEATH", player.getLocation(), 1f, 60f);
					cancel();
					task.cancel();
					return;
				}

			}

		}.runTaskTimer(Core.getThis(), 0, 2);
	}
}
