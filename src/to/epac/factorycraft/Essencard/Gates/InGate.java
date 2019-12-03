package to.epac.factorycraft.Essencard.Gates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;
import to.epac.factorycraft.Essencard.Utils.CardUtils;
import to.epac.factorycraft.Essencard.Utils.Display;
import to.epac.factorycraft.Essencard.Utils.DisplayUtils;
import to.epac.factorycraft.Essencard.Utils.FileUtils;
import to.epac.factorycraft.Essencard.Utils.MachineUtils;
import to.epac.factorycraft.Essencard.Utils.PlayerUtils;
import to.epac.factorycraft.Essencard.Utils.State;
import to.epac.factorycraft.Essencard.Utils.Utils;

public class InGate {
	private static Plugin plugin = Main.getInstance();
	
	public static void InGate(Player player, Sign gate) {
		
		Player using = MachineUtils.getUsingPlayer(gate);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This gate is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
			return;
		}
		
		String uid = player.getUniqueId().toString();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		int zone = MachineUtils.getZone(gate);
		
		if (CardUtils.isCard(heldItem)) {
			if (!CardUtils.isCardOwner(player, heldItem)) {
				player.sendMessage(FileUtils.getPrefix() + "&cThis is not your own Essencard. Please return to owner.");
				return;
			}
			
			if (PlayerUtils.isStaff(player)) {
				// Save current ZoneIn
				PlayerUtils.setZoneIn(uid, zone);
				// Clear last ZoneOut
				PlayerUtils.setZoneOut(uid, 0);
				
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.IN_STAFF, gate);
				Utils.playAlert(State.ACCESSED, player);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.IN_GATE, gate);
						MachineUtils.closeGate(gate);
						MachineUtils.unlockGate(gate);
					}
				}, FileUtils.getDelay());
			}
			else {
				if (!PlayerUtils.isInsideStation(player)) {
					// Save current ZoneIn
					PlayerUtils.setZoneIn(uid, zone);
					// Clear last ZoneOut
					PlayerUtils.setZoneOut(uid, 0);
					
					MachineUtils.lockGate(gate, player);
					MachineUtils.openGate(gate);
					DisplayUtils.setSignText(Display.IN_PASS, gate);
					Utils.playAlert(State.ACCESSED, player);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							DisplayUtils.setSignText(Display.IN_GATE, gate);
							MachineUtils.closeGate(gate);
							MachineUtils.unlockGate(gate);
						}
					}, FileUtils.getDelay());
				} else {
					player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "You have already entered the station. Please contact admin if you need assistance.");
				}
			}
		}
		else if (CardUtils.isOneWayTicket(heldItem)) {
			ItemMeta meta = heldItem.getItemMeta();
			String zoneIn = meta.getLore().get(0).substring(meta.getLore().get(0).length() - 1);
			int in = Integer.parseInt(zoneIn);
			if (in == zone) {
				// Save current ZoneIn
				PlayerUtils.setZoneIn(uid, zone);
				// Clear last ZoneOut
				PlayerUtils.setZoneOut(uid, 0);
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.IN_ONE_WAY_ACCEPTED, gate);
				Utils.playAlert(State.ACCESSED, player);
			}
			else {
				MachineUtils.lockGate(gate, player);
				DisplayUtils.setSignText(Display.IN_ONE_WAY_DENIED, gate);
				Utils.playAlert(State.DENIED, player);
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.IN_GATE, gate);
					MachineUtils.closeGate(gate);
					MachineUtils.unlockGate(gate);
				}
			}, FileUtils.getDelay());
		}
	}
}
