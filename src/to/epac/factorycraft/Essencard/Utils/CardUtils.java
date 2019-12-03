package to.epac.factorycraft.Essencard.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CardUtils {
	/**
	 * Check whether specified item is Essencard product
	 * @param item ItemStack to check
	 * @return true/false
	 */
	public static boolean isESC(ItemStack item) {
		if (item.getType() == Material.AIR) return false;
		
		String prefix = ChatColor.translateAlternateColorCodes('&', "&aESC &8| ");
		String name = item.getItemMeta().getDisplayName();
		
		if (name.startsWith(prefix))
			if (item.containsEnchantment(Enchantment.DURABILITY))
				return true;
		return false;
	}
	/**
	 * Check whether specified item is Essencard
	 * @param card ItemStack to check
	 * @return true/false
	 */
	public static boolean isCard(ItemStack card) {
		if (isESC(card))
			if (card.getType() == Material.NAME_TAG)
				return true;
		return false;
	}
	/**
	 * Check whether specified player own specified Essencard
	 * @param player Player to check
	 * @param card Essencard to check
	 * @return true/false
	 */
	public static boolean isCardOwner(Player player, ItemStack card) {
		String code = ChatColor.stripColor(card.getItemMeta().getLore().get(0));
		String uid = player.getUniqueId().toString();
		
		if (code.equals(uid))
			return true;
		
		return false;
	}
	/**
	 * Get specified Essencard's owner
	 * @param card Essencard to check
	 * @return UUID of owner in String
	 */
	public static String getCardOwner(ItemStack card) {
		return ChatColor.stripColor(card.getItemMeta().getLore().get(0));
	}
	/**
	 * Check whether specified item is One-Way Ticket
	 * @param ticket ItemStack to check
	 * @return true/false
	 */
	public static boolean isOneWayTicket(ItemStack ticket) {
		String prefix = ChatColor.GREEN + "ESC " + ChatColor.DARK_GRAY + "| "
				+ ChatColor.BLUE + "One-Way Ticket";
		String name = ticket.getItemMeta().getDisplayName();
		
		if (isESC(ticket))
			if (name.equals(prefix))
				if (ticket.getType() == Material.ITEM_FRAME)
					return true;
		return false;
	}
	/**
	 * Check whether specified item is Exit Only Ticket
	 * @param ticket ItemStack to check
	 * @return true/false
	 */
	public static boolean isExitOnlyTicket(ItemStack ticket) {
		String prefix = ChatColor.GREEN + "ESC " + ChatColor.DARK_GRAY + "| Exit Only Ticket";
		String name = ticket.getItemMeta().getDisplayName();
		
		if (isESC(ticket))
			if (name.equals(prefix))
				if (ticket.getType() == Material.ITEM_FRAME)
					return true;
		return false;
	}	
}
