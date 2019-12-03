package to.epac.factorycraft.Essencard.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	
	public static ItemStack getCard(String uid) {
		
		int id = PlayerUtils.getId(uid);
        
        if (id == 0) {
            id = FileUtils.getNextId();
            
            PlayerUtils.setId(uid, id);
            FileUtils.setNextId(id + 1);
        }
        
        ItemStack card = new ItemStack(Material.NAME_TAG);
        card.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ItemMeta meta = card.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aESC &8| &3" + id));
        List<String> lores = new ArrayList<>();
        lores.add(ChatColor.GOLD + uid);
        meta.setLore(lores);
        card.setItemMeta(meta);
        
        return card;
	}
	
	public static ItemStack getCard(Player player) {
		return getCard(player.getUniqueId().toString());
	}
	
	/*public static ItemStack getCard(int id) {
		
	}
	
	public static ItemStack getCardIS(int id) {
		ItemStack card = new ItemStack(Material.NAME_TAG);
        card.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ItemMeta meta = card.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aESC &8| &3" + id));
        List<String> lores = new ArrayList<>();
        lores.add(ChatColor.GOLD + FileUtils.getOwner(id));
        meta.setLore(lores);
        card.setItemMeta(meta);
        
        return card;
	}*/
	
	public static ItemStack getOneWayTicket(int in, int out) {
        ItemStack ticket = new ItemStack(Material.ITEM_FRAME);
        ticket.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ItemMeta meta = ticket.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aESC &8| &9One-Way Ticket"));
        List<String> lores = new ArrayList<>();
        lores.add(ChatColor.GREEN + "Zone In: " + in);
        lores.add(ChatColor.GREEN + "Zone Out: " + out);
        meta.setLore(lores);
        ticket.setItemMeta(meta);
        
        return ticket;
	}
	
	public static ItemStack getExitOnlyTicket() {
        ItemStack ticket = new ItemStack(Material.ITEM_FRAME);
        ticket.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ItemMeta meta = ticket.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aESC &8| Exit Only Ticket"));
        ticket.setItemMeta(meta);
        
        return ticket;
	}
}
