package to.epac.factorycraft.Essencard.Machines;

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

public class RSPayMachine {
	private static Plugin plugin = Main.getInstance();
	
	public static void RSPayMachine(Player player, Sign machine) {
		
		Player using = MachineUtils.getUsingPlayer(machine);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This gate is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
			return;
		}
		
		String uid = player.getUniqueId().toString();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		double fare = MachineUtils.getFare(machine);
		
		if (CardUtils.isESC(heldItem)) {
			if (!CardUtils.isCardOwner(player, heldItem)) {
				player.sendMessage(FileUtils.getPrefix() + "&cThis is not your own Essencard. Please return to owner.");
				return;
			}
			
			MachineUtils.lockGate(machine, player);
			
			// Is Staff
			if (PlayerUtils.isStaff(player)) {
				DisplayUtils.setSignText(Display.PAY_STAFF, machine);
				Utils.playAlert(State.ACCESSED, player);				
			}
			// Has enough balance
			else if (VaultHook.getEconomy().getBalance(player) >= fare) {
				VaultHook.getEconomy().withdrawPlayer(player, fare);
				PlayerUtils.setLastFare(uid, fare);
				DisplayUtils.setSignText(Display.PAY_DEDUCT, machine);
				Utils.playAlert(State.ACCESSED, player);
			}
			// Don't have enough balance
			else {
				MachineUtils.lockGate(machine, player);
				DisplayUtils.setSignText(Display.PAY_INSUFF_BAL, machine);
				Utils.playAlert(State.DENIED, player);
			}
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.PAY_MACHINE, machine);
					MachineUtils.unlockGate(machine);
				}
			}, FileUtils.getDelay());
		}
	}
}
