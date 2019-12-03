package to.epac.factorycraft.Essencard.Events;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import to.epac.factorycraft.Essencard.Gates.InGate;
import to.epac.factorycraft.Essencard.Gates.InterchangeGate;
import to.epac.factorycraft.Essencard.Gates.OutGate;
import to.epac.factorycraft.Essencard.Gates.RSPayNSGate;
import to.epac.factorycraft.Essencard.Gates.StaffGate;
import to.epac.factorycraft.Essencard.Machines.ClaimMachine;
import to.epac.factorycraft.Essencard.Machines.RSPayMachine;
import to.epac.factorycraft.Essencard.Machines.StatsMachine;
import to.epac.factorycraft.Essencard.Utils.CardUtils;

public class InteractHandler implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			/*
			 * Cancel event if player is holding Essencard product
			 * 
			 */
			
			if (event.getClickedBlock().getState() instanceof Sign) {
				
				if (CardUtils.isESC(item))
					event.setCancelled(true);
				
				Sign sign = (Sign) event.getClickedBlock().getState();
				String line1 = sign.getLine(0);

				if (line1.equals("[Essencard Stf]")) {
					StaffGate.StaffGate(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard Pay]")) {
					RSPayMachine.RSPayMachine(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard PNS]")) {
					RSPayNSGate.RSPayNSGate(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard In]")) {
					InGate.InGate(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard Out]")) {
					OutGate.OutGate(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard Int]")) {
					InterchangeGate.InterchangeGate(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard Stat]")) {
					StatsMachine.StatsMachine(event.getPlayer(), sign);
				}
				else if (line1.equals("[Essencard Cla]")) {
					ClaimMachine.ClaimMachine(event.getPlayer(), sign);
				}
			}
		}
	}
}
