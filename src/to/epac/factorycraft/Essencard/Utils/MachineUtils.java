package to.epac.factorycraft.Essencard.Utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;

public class MachineUtils {
	
	private static Plugin plugin = Main.getInstance();
	
	public static int getZone(Sign gate) {
		int zone;
		try {
			zone = Integer.parseInt(gate.getLine(1));
		} catch (NumberFormatException e) {
			// TODO - Log error gate in config
			return -1;
		}
		return zone;
	}
	
	public static double getFare(Sign gate) {
		int fare;
		try {
			fare = Integer.parseInt(gate.getLine(1));
		} catch (NumberFormatException e) {
			// TODO - Log error gate in config
			return 0;
		}
		return fare;
	}
	
	public static boolean isLocked(Sign gate) {
		if (gate.hasMetadata("Essencard:OccupiedBy"))
			return true;
		
		return false;
	}
	
	public static Player getUsingPlayer(Sign gate) {
		String occupiedBy;
		if (isLocked(gate)) {
			occupiedBy = gate.getMetadata("Essencard:OccupiedBy").get(0).asString();
			return Bukkit.getPlayer(UUID.fromString(occupiedBy));
		}
		return null;
	}
	
	public static void lockGate(Sign gate, Player player) {
		gate.setMetadata("Essencard:OccupiedBy", new FixedMetadataValue(plugin, player.getUniqueId()));
	}
	
	public static void unlockGate(Sign gate) {
		gate.removeMetadata("Essencard:OccupiedBy", plugin);
	}
	
	public static void openGate(Sign gate) {
		getControllerLocation(gate).getBlock().setType(Material.GREEN_WOOL);
	}
	
	public static void closeGate(Sign gate) {
		getControllerLocation(gate).getBlock().setType(Material.REDSTONE_BLOCK);
	}
	
	public static Location getControllerLocation(Sign gate) {
		
		Location loc = gate.getLocation();
		loc.setY(loc.getY() - 1);
		
		return loc;
	}
}
