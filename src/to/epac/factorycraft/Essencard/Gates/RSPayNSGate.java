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

public class RSPayNSGate {
	private static Plugin plugin = Main.getInstance();

	public static void RSPayNSGate(Player player, Sign gate) {
		
		Player using = MachineUtils.getUsingPlayer(gate);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This gate is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
			return;
		}
		
		String uid = player.getUniqueId().toString();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		double fare = MachineUtils.getFare(gate);
		
		if (CardUtils.isESC(heldItem)) {
			if (!CardUtils.isCardOwner(player, heldItem)) {
				player.sendMessage(FileUtils.getPrefix() + "&cThis is not your own Essencard. Please return to owner.");
				return;
			}
			
			MachineUtils.lockGate(gate, player);
			
			// Is Staff
			if (PlayerUtils.isStaff(player)) {
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.PNS_STAFF, gate);
				Utils.playAlert(State.ACCESSED, player);
			}
			// Has enough balance
			else if (VaultHook.getEconomy().getBalance(player) >= fare) {
				VaultHook.getEconomy().withdrawPlayer(player, fare);
				PlayerUtils.setLastFare(uid, fare);
				
				MachineUtils.openGate(gate);
				DisplayUtils.setSignText(Display.PNS_DEDUCT, gate);
				Utils.playAlert(State.ACCESSED, player);
			}
			// Don't have enough balance
			else {
				MachineUtils.lockGate(gate, player);
				DisplayUtils.setSignText(Display.PNS_INSUFF_BAL, gate);
				Utils.playAlert(State.DENIED, player);
			}
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DisplayUtils.setSignText(Display.PNS_GATE, gate);
					MachineUtils.unlockGate(gate);
					MachineUtils.closeGate(gate);
				}
			}, FileUtils.getDelay());
		}
	}
}
