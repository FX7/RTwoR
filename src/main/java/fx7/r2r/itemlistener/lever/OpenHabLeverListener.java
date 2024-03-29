package fx7.r2r.itemlistener.lever;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fx7.r2r.Coordinator;
import fx7.r2r.commandlistener.CommandListener;
import fx7.r2r.commandlistener.PlayerCommandListener;
import fx7.r2r.itemlistener.ItemListener;
import fx7.r2r.itemlistener.WorldCoordinates;
import fx7.r2r.rest.RestStub;

public class OpenHabLeverListener implements Listener, ItemListener<OpenHabLeverConfig>
{
	private Coordinator coordinator;

	private List<OpenHabLeverConfig> configs;

	private Map<UUID, Location> preparedLocations = new HashMap<>();

	public OpenHabLeverListener(Coordinator coordinator)
	{
		this.coordinator = coordinator;
		this.configs = coordinator.getLeverConfigs().stream().filter(c -> c instanceof OpenHabLeverConfig)
				.map(c -> (OpenHabLeverConfig) c).collect(Collectors.toList());
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK //
				&& block.getType() == Material.LEVER)
		{
			Location location = block.getLocation();
			OpenHabLeverConfig config = getMatch(location);
			if (config != null)
				handleConfig(player, config, block);
			else if (isInSelectionMode(player))
				prepareLocation(player, location);
		}
	}

	private boolean isInSelectionMode(Player player)
	{
		for (PlayerCommandListener listener : coordinator.getPlayerCommandListeners())
		{
			if (listener.isInSelectionMode(player))
				return true;
		}
		return false;
	}

	@Override
	public Location getClickedLocation(Player player)
	{
		if (player == null)
			return null;
		return preparedLocations.get(player.getUniqueId());
	}

	@Override
	public boolean storeLocation(Player player, Location location, String[] args)
	{
		Location preparedLocation = getClickedLocation(player);
		if (preparedLocation != location)
			return false; // somethings strange

		String stubName = args != null && args.length > 0 ? args[0] : null;
		String itemName = args != null && args.length > 1 ? args[1] : null;

		RestStub restStub = coordinator.getRestStub(stubName);
		if (restStub == null)
			return invalidStubName(player, stubName, null);

		WorldCoordinates coordinates = new WorldCoordinates(location);
		this.configs.add(new OpenHabLeverConfig(stubName, itemName, coordinates));
		preparedLocations.remove(player.getUniqueId());

		player.sendMessage("Stored Lever at location " + location);
		return true;
	}

	@Override
	public List<OpenHabLeverConfig> getConfigs()
	{
		return this.configs;
	}

	private void handleConfig(Player player, OpenHabLeverConfig config, Block block)
	{
		String stubName = config.getRestStubName();
		RestStub restStub = coordinator.getRestStub(stubName);
		if (restStub == null)
		{
			invalidStubName(player, stubName, config);
			return;
		}

		try
		{
			int returnCode = restStub.call(config, block);
			Bukkit.getConsoleSender().sendMessage("Player " + player + " clicked on OpenHabLeverConfig " + config
					+ "; httpStatusCode was " + returnCode);
		} catch (IOException e)
		{
			player.sendMessage("IOException during clock on Lever!");
			player.sendMessage(e.getMessage());
		}
	}

	private boolean invalidStubName(Player player, String stubname, OpenHabLeverConfig config)
	{
		String configDisplay = " ";
		if (config != null)
			configDisplay = " from OpenHabLeverConfig '" + config + "' ";
		player.sendMessage("RestStubName '" + stubname + "'" + configDisplay + "is unknown!");
		return false;
	}

	private void prepareLocation(Player player, Location location)
	{
		preparedLocations.put(player.getUniqueId(), location);

		player.sendMessage("Clicked on Lever at location " + location + ".");
		player.sendMessage(
				"Use '/" + CommandListener.CMD_R2RC + " <stubName> <itemName>' to store this lever location.");
	}

	private OpenHabLeverConfig getMatch(Location location)
	{
		if (location == null)
			return null;

		for (OpenHabLeverConfig config : configs)
		{
			if (config.getCoordinates().matches(location))
				return config;
		}
		return null;
	}
}
