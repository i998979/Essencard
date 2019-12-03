package to.epac.factorycraft.Essencard.Utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;

public class PlayerUtils {
	
	private static Plugin plugin = Main.getInstance();
	
	public static boolean hasData(Player player) {
		return FileUtils.getPlayersInSys().contains(player);
	}
	
	public static boolean isStaff(Player player) {
		return player.hasPermission("Essencard.StaffPass");
	}
	
	public static boolean isInsideStation(Player player) {
		int zoneIn = PlayerUtils.getZoneIn(player.getUniqueId().toString());
		int zoneOut = PlayerUtils.getZoneOut(player.getUniqueId().toString());
		
		if (zoneIn <= 0 && zoneOut <= 0) return false;
		if (zoneIn <= 0 && zoneOut > 0) return false;
		
		if (zoneIn > 0 && zoneOut <= 0) return true;
		if (zoneIn > 0 && zoneOut > 0) return false;
		
		return false;
	}
	
	/*
	 * Get/Set player's Id
	 */
	public static int getId(String uid) {
		// The last integer is the define that if the path's value doesn't exist
		// The statement will return -1 itself
		// return plugin.getConfig().getInt("Players." + uid + ".ID", -1);
		return plugin.getConfig().getInt("Players." + uid + ".ID");
	}
	public static void setId(String uid, int id) {
		plugin.getConfig().set("Players." + uid + ".ID", id);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set player's last fare
	 */
	public static double getLastFare(String uid) {
		return plugin.getConfig().getDouble("Players." + uid + ".LastFare");
	}
	public static void setLastFare(String uid, double fare) {
		plugin.getConfig().set("Players." + uid + ".LastFare", fare);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set player's Zone In
	 */
	public static int getZoneIn(String uid) {
		return plugin.getConfig().getInt("Players." + uid + ".ZoneIn");
	}
	public static void setZoneIn(String uid, int zone) {
		plugin.getConfig().set("Players." + uid + ".ZoneIn", zone);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set player's Zone Out
	 */
	public static int getZoneOut(String uid) {
		return plugin.getConfig().getInt("Players." + uid + ".ZoneOut");
	}
	public static void setZoneOut(String uid, int zone) {
		plugin.getConfig().set("Players." + uid + ".ZoneOut", zone);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set player's Last Claim
	 */
	public static long getLastClaim(String uid) {
		return plugin.getConfig().getLong("Players." + uid + ".LastClaim");
	}
	public static void setLastClaim(String uid, long time) {
		plugin.getConfig().set("Players." + uid + ".LastClaim", time);
		plugin.saveConfig();
	}
	
	/*
	 * Get/Set player's Fix Permit
	 */
	public static boolean getFixPermit(String uid) {
		// The last boolean is to define that if the path's value doesn't exist
		// The statement will return false itself
		return plugin.getConfig().getBoolean("Players." + uid + ".FixPermit", false);
	}
	public static void setFixPermit(String uid, boolean permit) {
		plugin.getConfig().set("Players." + uid + ".FixPermit", permit);
		plugin.saveConfig();
	}
	
	public static void fix(String uid) {
		plugin.getConfig().set("Players." + uid + ".FixPermit", null);
        plugin.getConfig().set("Players." + uid + ".ZoneIn", null);
        plugin.getConfig().set("Players." + uid + ".ZoneOut", null);
        plugin.saveConfig();
	}
}
