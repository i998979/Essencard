package to.epac.factorycraft.Essencard.Gates;

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
import to.epac.factorycraft.Essencard.Utils.PlayerUtils;
import to.epac.factorycraft.Essencard.Utils.State;
import to.epac.factorycraft.Essencard.Utils.Utils;

public class StaffGate {
	private static Plugin plugin = Main.getInstance();

	public static void StaffGate(Player player, Sign gate) {
		
		Player using = MachineUtils.getUsingPlayer(gate);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This gate is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
			return;
		}
		
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		
		if (CardUtils.isCard(heldItem)) {
			if (!CardUtils.isCardOwner(player, heldItem)) {
				player.sendMessage(FileUtils.getPrefix() + "&cThis is not your own Essencard. Please return to owner.");
				return;
			}
			
			if (PlayerUtils.isStaff(player)) {
				MachineUtils.lockGate(gate, player);
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.STAFF_PASS, gate);
				Utils.playAlert(State.ACCESSED, player);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.STAFF_GATE, gate);
						MachineUtils.closeGate(gate);
						MachineUtils.unlockGate(gate);
					}
				}, FileUtils.getDelay());
			}
			else {
				MachineUtils.lockGate(gate, player);
				DisplayUtils.setSignText(Display.STAFF_DENIED, gate);
				Utils.playAlert(State.DENIED, player);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.STAFF_GATE, gate);
						MachineUtils.unlockGate(gate);
					}
				}, FileUtils.getDelay());
			}
		}
	}
}
