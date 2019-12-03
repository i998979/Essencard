package to.epac.factorycraft.Essencard.Machines;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;
import to.epac.factorycraft.Essencard.Utils.CardUtils;
import to.epac.factorycraft.Essencard.Utils.Display;
import to.epac.factorycraft.Essencard.Utils.DisplayUtils;
import to.epac.factorycraft.Essencard.Utils.FileUtils;
import to.epac.factorycraft.Essencard.Utils.MachineUtils;
import to.epac.factorycraft.Essencard.Utils.State;
import to.epac.factorycraft.Essencard.Utils.Utils;

public class StatsMachine {
	private static Plugin plugin = Main.getInstance();

	public static void StatsMachine(Player player, Sign machine) {
		
		Player using = MachineUtils.getUsingPlayer(machine);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This gate is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
			return;
		}
		
		String uid = player.getUniqueId().toString();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		
		if (CardUtils.isCard(heldItem)) {
			if (!CardUtils.isCardOwner(player, heldItem)) {
				player.sendMessage(FileUtils.getPrefix() + "&cThis is not your own Essencard. Please return to owner.");
				return;
			}
			
			MachineUtils.lockGate(machine, player);
			Utils.playAlert(State.ACCESSED, player);
			
			DisplayUtils.setSignText(Display.STATS_PAGE_1, machine);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.STATS_PAGE_2, machine);
				}
			}, 1 * FileUtils.getStatsDelay());
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.STATS_PAGE_3, machine);
				}
			}, 2 * FileUtils.getStatsDelay());
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.STATS_PAGE_4, machine);
				}
			}, 3 * FileUtils.getStatsDelay());
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.STATS_MACHINE, machine);
					MachineUtils.unlockGate(machine);
				}
			}, 4 * FileUtils.getStatsDelay());
		}
	}
}
