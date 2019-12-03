package to.epac.factorycraft.Essencard.Utils;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;
import to.epac.factorycraft.Essencard.VaultHook;

public class DisplayUtils {
	private static Plugin plugin = Main.getInstance();

	/*
	 * Sign text writer
	 * Write different pack of text on signs according to the type
	 *
	 */
	public static void setSignText(Display type, Sign machine) {
		Player player = MachineUtils.getUsingPlayer(machine);
		String uid = player.getUniqueId().toString();
		
		if (type == Display.STAFF_GATE) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&4&lStaff only"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lGate Ready"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.STAFF_PASS) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aStaff pass"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aGate opened"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aPlease enter"));
		} else if (type == Display.STAFF_DENIED) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&cUnauthorised"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cperson"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&4&lAccess denied"));
		}
		
		
		else if (type == Display.STATS_MACHINE) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&a&lMachine OK"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&dPlease Tap"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dEssencard"));
		} else if (type == Display.STATS_PAGE_1) {
			double lastFare = PlayerUtils.getLastFare(uid);
			
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aLast fare:"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a" + lastFare));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&f1/4"));
		} else if (type == Display.STATS_PAGE_2) {
			int zoneIn = PlayerUtils.getZoneIn(uid);
			
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aLast zone in:"));
			if (zoneIn > 0)
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a" + zoneIn));
			else
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aNone"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "2/4"));
		} else if (type == Display.STATS_PAGE_3) {
			int zoneOut = PlayerUtils.getZoneOut(uid);
			
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aLast zone out:"));
			if (zoneOut > 0)
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a" + zoneOut));
			else
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aNone"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&f3/4"));
		} else if (type == Display.STATS_PAGE_4) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass:"));
			if (PlayerUtils.isStaff(player))
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lYES"));
			else
				machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lNO"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&f4/4"));
		}
		
		
		else if (type == Display.CLAIM_MACHINE) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&a&lMachine OK"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&dPlease Tap"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dEssencard"));
		} else if (type == Display.CLAIM_ALREADY) {
			long minute = (Utils.getTimeInt() - PlayerUtils.getLastClaim(uid)) / 60;
			
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&4Claimed in"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&4" + minute + " mins ago"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&4Come back later"));
		} /*else if (type == Display.CLAIM_SUCCESS) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&aClaimed $" + money));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aCome back"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&a" +  minute + " mins later"));
		}*/
		else if (type == Display.CLAIM_MACHINE) {
			machine.setLine(1, ChatColor.translateAlternateColorCodes('&', "&a&lMachine OK"));
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&dPlease Tap"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dEssencard"));
		}
		
		
		else if (type == Display.PAY_MACHINE) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lMachine OK"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.PAY_STAFF) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aDeducted $0"));
		} else if (type == Display.PAY_DEDUCT) {
			double lastFare = PlayerUtils.getLastFare(uid);
			double bal = VaultHook.getEconomy().getBalance(player);
			
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&bFare &0| &aBal."));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b" + lastFare + "&0|&a" + bal));
		} else if (type == Display.PAY_INSUFF_BAL) {			
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lInsufficient"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&c&lbalance"));
		}
		
		else if (type == Display.PNS_GATE) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lMachine OK"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.PNS_STAFF) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aDeducted $0"));
		} else if (type == Display.PNS_DEDUCT) {
			double lastFare = PlayerUtils.getLastFare(uid);
			double bal = VaultHook.getEconomy().getBalance(player);
			
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&bFare &0| &aBal."));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b" + lastFare + "&0|&a" + bal));
		} else if (type == Display.PNS_INSUFF_BAL) {			
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lInsufficient"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&c&lbalance"));
		}
		
		else if (type == Display.IN_GATE) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lGate Ready"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.IN_STAFF) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aGate opened"));
		} else if (type == Display.IN_PASS) {
			double bal = VaultHook.getEconomy().getBalance(player);
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aBal. $" + bal));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aGo in"));
		} else if (type == Display.IN_ONE_WAY_ACCEPTED) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&eTicket accepted"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&eGo in"));
		} else if (type == Display.IN_ONE_WAY_DENIED) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cInvalid zone"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&cCheck ticket"));
		}
		
		
		else if (type == Display.OUT_GATE) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lGate Ready"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.OUT_STAFF) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aGate opened"));
		} else if (type == Display.OUT_DEDUCT) {
			double lastFare = PlayerUtils.getLastFare(uid);
			double bal = VaultHook.getEconomy().getBalance(player);
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&bFare &0| &aBal."));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b" + lastFare + "&0|&a" + bal));
		} else if (type == Display.OUT_INSUFF_BAL) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lInsufficient"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&c&lbalance"));
		} else if (type == Display.OUT_ONEWAY_ACCEPTED) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&eThanks for"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&etravelling"));
		} else if (type == Display.OUT_ONEWAY_DENIED) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lInvalid zone"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&cCheck ticket"));
		} else if (type == Display.OUT_EXITONLY) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&eThanks for"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&etravelling"));
		}
		
		else if (type == Display.INT_GATE) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a&lGate Ready"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&dTap Essencard"));
		} else if (type == Display.INT_STAFF) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&aStaff Pass"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&aGate opened"));
		} else if (type == Display.INT_DEDUCTED) {
			double lastFare = PlayerUtils.getLastFare(uid);
			double bal = VaultHook.getEconomy().getBalance(player);
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&bFare &0| &aBal."));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b" + lastFare + "&0|&a" + bal));
		} else if (type == Display.INT_INSUFF_BAL) {
			machine.setLine(2, ChatColor.translateAlternateColorCodes('&', "&c&lInsufficient"));
			machine.setLine(3, ChatColor.translateAlternateColorCodes('&', "&c&lbalance"));
		}
		
		machine.update(true);
	}
}