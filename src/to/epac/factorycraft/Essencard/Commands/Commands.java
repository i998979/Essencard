package to.epac.factorycraft.Essencard.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.Essencard.Main;
import to.epac.factorycraft.Essencard.Utils.FileUtils;
import to.epac.factorycraft.Essencard.Utils.ItemUtils;
import to.epac.factorycraft.Essencard.Utils.PlayerUtils;

public class Commands implements CommandExecutor {
    Plugin plugin = Main.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            HelpPage(sender);
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String uid = player.getUniqueId().toString();            

            if (args[0].equalsIgnoreCase("get")) {
                player.getInventory().addItem(ItemUtils.getCard(player));
            }
            else if (args[0].equalsIgnoreCase("fix")) {
                if (player.hasPermission("Essencard.Admin") || PlayerUtils.getFixPermit(uid)) {
                    PlayerUtils.fix(uid);

                    player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Your Essencard record has been fixed. Please check your card session.");
                    //Lang.ESC_FIXED.sendWP(player);
                } else {
                    player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "You are not authorised to fix Essencard record. Please contact admin if you need assistance.");
                }
            }
            else if (player.hasPermission("Essencard.Admin")) {
                if (args[0].equalsIgnoreCase("ticket")) {
                    if (args.length == 1) {
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter zone in.");
                        return false;
                    }
                    if (args.length == 2) {
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter zone out.");
                        return false;
                    }
                    
                    int in;
                    int out;
                    try {
                    	in = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                    	player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please specify a valid integer for Zone In.");
                    	return false;
                    }
                    try {
                    	out = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                    	player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please specify a valid integer for Zone Out.");
                    	return false;
                    }
                    player.getInventory().addItem(ItemUtils.getOneWayTicket(in, out));
                }
                else if (args[0].equalsIgnoreCase("exitonly")) {
                    player.getInventory().addItem(ItemUtils.getExitOnlyTicket());
                }
                // TODO
                else if (args[0].equalsIgnoreCase("fixpermit")) {
                    if (args.length == 1) {
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please specify a player.");
                        return false;
                    }
                    if (PlayerUtils.getFixPermit(uid)) {
                        plugin.getConfig().set("Players." + player.getUniqueId() + ".FixPermit", null);
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "You no longer permit " + ChatColor.GREEN + args[1] + ChatColor.YELLOW + " to execute " + ChatColor.LIGHT_PURPLE + "/esc fix " + ChatColor.YELLOW + "command.");
                    } else {
                        plugin.getConfig().set("Players." + player.getUniqueId() + ".FixPermit", true);
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "You permitted " + ChatColor.GREEN + args[1] + ChatColor.YELLOW + " to execute " + ChatColor.LIGHT_PURPLE + "/esc fix " + ChatColor.YELLOW + "command.");
                    }
                    plugin.saveConfig();
                }
                else if (args[0].equalsIgnoreCase("standardfare")) {
                    if (args.length == 1) {
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Please specify a number.");
                        return false;
                    }
                    double stdfare;
                    try {
                        stdfare = Double.parseDouble(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Please specify a valid number.");
                        return false;
                    }
                    FileUtils.setStandardFare(stdfare);
                    player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Standard fare has set to " + args[1]);
                }
                else if (args[0].equalsIgnoreCase("reset")) {
                    plugin.saveResource("config.yml", true);
                    plugin.saveConfig();
                    player.sendMessage(ChatColor.GREEN + "Configurations and datas has reset to default.");
                }
                else {
                    HelpPage(player);
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "You don't have permission to execute this command.");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
        }
        return false;
    }

    public void HelpPage(CommandSender sender) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------" + ChatColor.AQUA + "Essencard 6.0" + ChatColor.LIGHT_PURPLE + "----------");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Author: i998979");
        sender.sendMessage(ChatColor.GOLD + "Ideas originally by MinecraftSBC");
        sender.sendMessage(ChatColor.RED + "Commands");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Aliases: " + ChatColor.DARK_AQUA + "/esc");
        sender.sendMessage(ChatColor.AQUA + "/esc help: " + ChatColor.DARK_AQUA + "Show help page.");
        sender.sendMessage(ChatColor.AQUA + "/esc get: " + ChatColor.DARK_AQUA + "Get your own Essencard.");
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "*Admin permission required*");
        sender.sendMessage(ChatColor.AQUA + "/esc fix: " + ChatColor.DARK_AQUA + "Fix your Essencard's invalid session.");
        
        if (sender.hasPermission("Essencard.Admin")) {
        	sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_RED + "Admin Commands:");
            sender.sendMessage(ChatColor.AQUA + "/esc ticket [Zone In] [Zone Out]: " + ChatColor.DARK_AQUA + "Get a One-Time use ticket with specified zone.");
            sender.sendMessage(ChatColor.AQUA + "/esc exitonly: " + ChatColor.DARK_AQUA + "Get an Exit-Only ticket.");
            sender.sendMessage(ChatColor.AQUA + "/esc fixpermit [player]: " + ChatColor.DARK_AQUA + "Authorise a player to fix Essencard record.");
            sender.sendMessage(ChatColor.AQUA + "/esc standardfare [number]: " + ChatColor.DARK_AQUA + "Set the standard fare for each journey.");
            sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "*USE WITH CAUTION*");
            sender.sendMessage(ChatColor.AQUA + "/esc reset: " + ChatColor.DARK_AQUA + "Reset configurations and datas to default.");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_PURPLE + "Permissions:");
            sender.sendMessage(ChatColor.AQUA + "Essencard.Admin: " + ChatColor.DARK_AQUA + "Access to all admin commands.");
            sender.sendMessage(ChatColor.AQUA + "Essencard.StaffPass: " + ChatColor.DARK_AQUA + "No fare deducted when using gates, access to staff gates.");
        }
    }
}