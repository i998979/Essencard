package to.epac.factorycraft.Essencard.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;

public class FileUtils {
	
	private static Plugin plugin = Main.getInstance();
	
	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix"));
	}
	
	/*
	 * Get/Set next card to be assigned to the player
	 */
	public static int getNextId() {
		return plugin.getConfig().getInt("NextID");
	}
	public static void setNextId(int next) {
		plugin.getConfig().set("NextID", next);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set standard fare
	 */
	public static double getStandardFare() {
		return plugin.getConfig().getDouble("StandardFare");
	}
	public static void setStandardFare(double stdfare) {
		plugin.getConfig().set("StandardFare", stdfare);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set Claim cool down
	 */
	public static long getClaimCD() {
		return plugin.getConfig().getLong("ClaimCD");
	}
	public static void setClaimCD(long cd) {
		plugin.getConfig().set("ClaimCD", cd);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set Claim min
	 */
	public static double getClaimMin() {
		return plugin.getConfig().getDouble("ClaimMin");
	}
	public static void setClaimMin(double min) {
		plugin.getConfig().set("ClaimMin", min);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set Claim max
	 */
	public static double getClaimMax() {
		return plugin.getConfig().getDouble("ClaimMax");
	}
	public static void setClaimMax(double max) {
		plugin.getConfig().set("ClaimMax", max);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set Gates/Machines delay
	 */
	public static long getDelay() {
		return plugin.getConfig().getLong("Delay");
	}
	public static void setDelay(long delay) {
		plugin.getConfig().set("Delay", delay);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set Stats Sign delay
	 */
	public static long getStatsDelay() {
		return plugin.getConfig().getLong("StatsDelay");
	}
	public static void setStatsDelay(long delay) {
		plugin.getConfig().set("StatsDelay", delay);
		plugin.saveConfig();
	}
	
	/*
	 * Get player list in the system
	 */
	public static List<Player> getPlayersInSys() {
		List<Player> list = new ArrayList<>();
		ConfigurationSection players = plugin.getConfig().getConfigurationSection("Players");
		
		for (String id : players.getKeys(false)) {
			UUID uuid = UUID.fromString(id);
			Player player = Bukkit.getPlayer(uuid);
			list.add(player);
		}
		return list;
	}
	
	/*
	 * Get card owner with specified card id
	 */
	public static String getOwner(int id) {
		ConfigurationSection players = plugin.getConfig().getConfigurationSection("Players");
		for (String uid : players.getKeys(false)) {
			if (PlayerUtils.getId(uid) == id) return uid;
		}
		return null;
	}
}
