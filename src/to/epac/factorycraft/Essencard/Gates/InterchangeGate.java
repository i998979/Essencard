package to.epac.factorycraft.Essencard.Gates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;
import to.epac.factorycraft.Essencard.VaultHook;
import to.epac.factorycraft.Essencard.Utils.CardUtils;
import to.epac.factorycraft.Essencard.Utils.Display;
import to.epac.factorycraft.Essencard.Utils.DisplayUtils;
import to.epac.factorycraft.Essencard.Utils.FileUtils;
import to.epac.factorycraft.Essencard.Utils.MachineUtils;
import to.epac.factorycraft.Essencard.Utils.PlayerUtils;
import to.epac.factorycraft.Essencard.Utils.State;
import to.epac.factorycraft.Essencard.Utils.Utils;

public class InterchangeGate {
	private static Plugin plugin = Main.getInstance();
	
	public static void InterchangeGate(Player player, Sign gate) {
		
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
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.INT_STAFF, gate);
				Utils.playAlert(State.ACCESSED, player);
				
				PlayerUtils.setZoneOut(uid, zone);
				PlayerUtils.setZoneIn(uid, zone);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.INT_GATE, gate);
						MachineUtils.closeGate(gate);
						MachineUtils.unlockGate(gate);
					}
				}, FileUtils.getDelay());
			}
			else {
				if (PlayerUtils.isInsideStation(player)) {
					MachineUtils.lockGate(gate, player);
					
					int in = PlayerUtils.getZoneIn(uid);
					int out = zone;
					int zones = Math.abs(in - out);
					if (zones == 0) zones = 1;
					double standard = FileUtils.getStandardFare();
					
					double fare = zones * standard;
					
					if (VaultHook.getEconomy().getBalance(player) >= fare) {
						VaultHook.getEconomy().withdrawPlayer(player, fare);
						
						PlayerUtils.setLastFare(uid, fare);
						MachineUtils.openGate(gate);
						DisplayUtils.setSignText(Display.INT_DEDUCTED, gate);
						Utils.playAlert(State.ACCESSED, player);
						
						PlayerUtils.setZoneIn(uid, zone);
						
						// With closeGate
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								DisplayUtils.setSignText(Display.INT_GATE, gate);
								MachineUtils.closeGate(gate);
								MachineUtils.unlockGate(gate);
							}
						}, FileUtils.getDelay());
					}
					else {
						DisplayUtils.setSignText(Display.INT_INSUFF_BAL, gate);
						Utils.playAlert(State.DENIED, player);
						
						// No closeGate
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								DisplayUtils.setSignText(Display.INT_GATE, gate);
								MachineUtils.unlockGate(gate);
							}
						}, FileUtils.getDelay());
					}
				} else {
					player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "You have already left the station. Please contact admin if you need assistance.");
				}
			}
		}
	}
	
}
