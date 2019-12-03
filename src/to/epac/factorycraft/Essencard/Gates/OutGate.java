package to.epac.factorycraft.Essencard.Gates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

public class OutGate {
	private static Plugin plugin = Main.getInstance();
	
	public static void OutGate(Player player, Sign gate) {
		
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
				PlayerUtils.setZoneOut(uid, zone);
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.OUT_STAFF, gate);
				Utils.playAlert(State.ACCESSED, player);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.OUT_GATE, gate);
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

						PlayerUtils.setZoneOut(uid, zone);
						MachineUtils.openGate(gate);
						PlayerUtils.setLastFare(uid, fare);
						DisplayUtils.setSignText(Display.OUT_DEDUCT, gate);
						Utils.playAlert(State.ACCESSED, player);
						
						// With closeGate
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								DisplayUtils.setSignText(Display.OUT_GATE, gate);
								MachineUtils.closeGate(gate);
								MachineUtils.unlockGate(gate);
							}
						}, FileUtils.getDelay());
					}
					else {
						DisplayUtils.setSignText(Display.OUT_INSUFF_BAL, gate);
						Utils.playAlert(State.DENIED, player);
						
						// No closeGate
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								DisplayUtils.setSignText(Display.OUT_GATE, gate);
								MachineUtils.unlockGate(gate);
							}
						}, FileUtils.getDelay());
					}
				} else {
					player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "You have already left the station. Please contact admin if you need assistance.");
				}
			}
		}
		else if (CardUtils.isOneWayTicket(heldItem)) {
			ItemMeta meta = heldItem.getItemMeta();
			String zoneOut = meta.getLore().get(1).substring(meta.getLore().get(1).length() - 1);
			int out = Integer.parseInt(zoneOut);
			if (out == zone) {
				player.getInventory().setItemInMainHand(null);
				PlayerUtils.setZoneOut(uid, zone);
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.OUT_ONEWAY_ACCEPTED, gate);
				Utils.playAlert(State.ACCESSED, player);
			}
			else {
				MachineUtils.lockGate(gate, player);
				DisplayUtils.setSignText(Display.OUT_ONEWAY_DENIED, gate);
				Utils.playAlert(State.DENIED, player);
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.OUT_GATE, gate);
					MachineUtils.closeGate(gate);
					MachineUtils.unlockGate(gate);
				}
			}, FileUtils.getDelay());
		}
		else if (CardUtils.isExitOnlyTicket(heldItem)) {
			player.getInventory().setItemInMainHand(null);
			PlayerUtils.setZoneOut(uid, zone);
			MachineUtils.lockGate(gate, player);
			MachineUtils.openGate(gate);
			DisplayUtils.setSignText(Display.OUT_EXITONLY, gate);
			Utils.playAlert(State.ACCESSED, player);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.OUT_GATE, gate);
					MachineUtils.closeGate(gate);
					MachineUtils.unlockGate(gate);
				}
			}, FileUtils.getDelay());
		}
	}
}
