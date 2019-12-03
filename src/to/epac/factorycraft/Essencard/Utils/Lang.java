package to.epac.factorycraft.Essencard.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public enum Lang {
	
	PREFIX("prefix", "&9[&bEssencard&9]&r "),
	ESC_FIXED("esc_fixed", "&aYour Essencard record has been fixed. Please check your card session."),
	ESC_FIX_NOPERM("esc_fix_noperm", "&cYou are not authorised to fix Essencard record. Please contact admin if you need assistance."),
	NO_ZONE_IN("no_zone_in", "&cPlease enter zone in."),
	NO_ZONE_OUT("no_zone_out", "&cPlease enter zone out."),
	NO_PLAYER("no_player", "&cPlease specify a player."),
	INVALID_NUMBER("invalid_number", "&cPlease enter a valid number."),
	STD_FARE_SET("std_fare_set", "&aStandard fare has set to &e{0}&a."),
	SYS_RESET("sys_reset", "&aConfiguration and datas has reset to default."),
	NO_PERM("no_perm", "&cYou don't have permission to execute this command."),
	NOT_PLAYER("not_player", "&cYou must be a player to execute this command."),
	OCCUPIED_MACHINE("occupied", "&aThis machine is using by &e{0}&a."),
	OCCUPIED_GATE("occupied", "&aThis gate is using by &e{0}&a."),
	NON_STAFF("non_staff", "&cYou are not authorised to use Staff Gate."),
	GATE_ERROR("gate_error", "&cEssencard gate error, please contact admin. &7&l{0}"),
	
	
	
	
	
	
	;

	
    private String path, def;
    private static FileConfiguration LANG;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
    public String getDefault() {
        return this.def;
    }
    public String getPath() {
        return this.path;
    }
    public String format(Object...args) {
        String value = ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.def));

        if (args == null) return value;
        else {
            if (args.length == 0) return value;

            for (int i = 0; i < args.length; i++) {
                String arg = ChatColor.translateAlternateColorCodes('&', args[i].toString());
                value = value.replace("{" + i + "}", arg);
            }
        }
        return value;
    }
    /**
     * Send message
     */
    public void send(CommandSender sender, Object...args) {
        sender.sendMessage(format(args));
    }
    /**
     * Send message with prefix
     */
    public void sendWP(CommandSender sender, Object...args) {
        sender.sendMessage(Lang.PREFIX.format() + format(args));
    }
}
