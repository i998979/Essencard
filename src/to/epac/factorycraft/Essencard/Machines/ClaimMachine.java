package to.epac.factorycraft.Essencard.Machines;

import java.text.DecimalFormat;
import java.util.Random;

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
import to.epac.factorycraft.Essencard.Utils.FileUtils;
import to.epac.factorycraft.Essencard.Utils.MachineUtils;
import to.epac.factorycraft.Essencard.Utils.PlayerUtils;
import to.epac.factorycraft.Essencard.Utils.State;
import to.epac.factorycraft.Essencard.Utils.Utils;
import to.epac.factorycraft.Essencard.Utils.DisplayUtils;

public class ClaimMachine {
	private static Plugin plugin = Main.getInstance();

	public static void ClaimMachine(Player player, Sign machine) {
		
		Player using = MachineUtils.getUsingPlayer(machine);
		if (using != null) {
			player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "This machine is currently using by " + ChatColor.GOLD + using.getName() + ChatColor.GREEN + ".");
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
			
			long now = Utils.getTimeInt();
			long lastclaim = PlayerUtils.getLastClaim(uid);
			
			if (now - lastclaim >= FileUtils.getClaimCD()) {
				
				Utils.playAlert(State.ACCESSED, player);
				// Utils.setSignText(Display.CLAIM_SUCCESS, machine);
				
				double min = FileUtils.getClaimMin();
				double max = FileUtils.getClaimMax();
				double money = new Random().nextDouble() * max + min;
				
				DecimalFormat df = new DecimalFormat("#.#");
				money = Double.parseDouble(df.format(money));
				
				VaultHook.getEconomy().depositPlayer(player, money);
				long minute = FileUtils.getClaimCD() / 60;
				machine.setLine(1, ChatColor.GREEN + "Claimed $" + money);
				machine.setLine(2, ChatColor.GREEN + "Come back");
				machine.setLine(3, ChatColor.GREEN + "" +  minute + " mins later");
				machine.update(true);
				
				PlayerUtils.setLastClaim(uid, now);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.CLAIM_MACHINE, machine);
						MachineUtils.unlockGate(machine);
					}
				}, FileUtils.getDelay());
			}
			else {
				Utils.playAlert(State.DENIED, player);
				DisplayUtils.setSignText(Display.CLAIM_ALREADY, machine);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						DisplayUtils.setSignText(Display.CLAIM_MACHINE, machine);
						MachineUtils.unlockGate(machine);
					}
				}, FileUtils.getDelay());
			}
		}
	}
}
