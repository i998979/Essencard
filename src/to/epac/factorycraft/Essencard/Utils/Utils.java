package to.epac.factorycraft.Essencard.Utils;

import java.util.Date;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Utils {
	
	public static long getTimeInt() {
		java.util.Date now = new Date();
		long i = now.getTime();
		
		return i / 1000;
	}
	
	/*
	 *
	 * Sound player
	 * Play sound specifically to a player in different situation
	 *
	 */
	public static void playAlert(State state, Player player) {
		if (state == State.ACCESSED)
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
		else if (state == State.DENIED)
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
	}
}
