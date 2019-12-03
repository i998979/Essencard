package to.epac.factorycraft.Essencard;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import to.epac.factorycraft.Essencard.Commands.Commands;
import to.epac.factorycraft.Essencard.Events.InteractHandler;
import to.epac.factorycraft.Essencard.Utils.FileUtils;

public class Main extends JavaPlugin {
	
	private static Main instance;

	public void onEnable() {
		instance = this;

		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new InteractHandler(), this);

		File configFile;
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			getServer().getConsoleSender().sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Configuration not found. Generating the default one.");
			
			getConfig().options().copyDefaults(true);
			saveConfig();
		}		
		
		getCommand("essencard").setExecutor(new Commands());
		
		VaultHook.HookIntoVault();

	}

	public void onDisable() {
		instance = null;
	}
	
	public static Plugin getInstance() {
		return instance;
	}
}
