package fx7.r2r.commandlistener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fx7.r2r.Coordinator;
import fx7.r2r.itemlistener.ItemListener;

public class CommandListener implements CommandExecutor, PlayerCommandListener
{
	public static final String CMD_R2RS = "r2rs";
	public static final String CMD_R2RC = "r2rc";

	private Coordinator coordinator;

	private Set<UUID> activeSelectingPlayers = new HashSet<>();

	public CommandListener(Coordinator coordinator)
	{
		this.coordinator = coordinator;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		String cmd = command.getName();
		if (CMD_R2RS.equals(cmd))
			return handleR2RS(player);
		else if (CMD_R2RC.equals(cmd))
			return handleR2RC(player, args);

		return false;
	}

	private boolean handleR2RC(Player player, String[] args)
	{
		if (!isInSelectionMode(player))
			return notInSelectionMode(player);

		Location location = getClickedLocation(player);
		if (location == null)
			return nothingSelected(player);

		return storeLocation(player, location, args);
	}

	@Override
	public boolean isInSelectionMode(Player player)
	{
		synchronized (activeSelectingPlayers)
		{
			return activeSelectingPlayers.contains(player.getUniqueId());
		}
	}

	private boolean toggle(Player player)
	{
		UUID uuid = player.getUniqueId();
		synchronized (activeSelectingPlayers)
		{
			if (activeSelectingPlayers.contains(uuid))
			{
				activeSelectingPlayers.remove(uuid);
				return false;
			} else
			{
				activeSelectingPlayers.add(uuid);
				return true;
			}
		}
	}

	private boolean handleR2RS(Player player)
	{
		boolean selectionStarted = toggle(player);

		if (selectionStarted)
			player.sendMessage("You are now in selection mode. Next right clicked block/item will be selected.");
		else
			player.sendMessage("You ended your selection mode.");

		return true;
	}

	private boolean notInSelectionMode(Player player)
	{
		player.sendMessage(
				"Before using '/" + CMD_R2RC + " <name>' please activate selection mode with '/" + CMD_R2RS + "'.");
		return false;
	}

	private boolean nothingSelected(Player player)
	{
		player.sendMessage("Before using '/" + CMD_R2RC + " <name>' please right click on a block/item.");
		return false;
	}

	private boolean storeLocation(Player player, Location location, String[] args)
	{
		for (ItemListener<?> lh : coordinator.getItemListeners())
		{
			if (lh.storeLocation(player, location, args))
			{
				toggle(player);
				return true;
			}
		}
		player.sendMessage("Couldnt store your Location :-/");
		return false;
	}

	private Location getClickedLocation(Player player)
	{
		for (ItemListener<?> lh : coordinator.getItemListeners())
		{
			Location location = lh.getClickedLocation(player);
			if (location != null)
				return location;
		}
		return null;
	}

}
