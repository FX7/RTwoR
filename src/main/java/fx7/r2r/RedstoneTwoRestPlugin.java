package fx7.r2r;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import fx7.r2r.commandlistener.CommandListener;
import fx7.r2r.commandlistener.PlayerCommandListener;
import fx7.r2r.itemlistener.ItemListener;
import fx7.r2r.itemlistener.WorldCoordinates;
import fx7.r2r.itemlistener.lever.LeverConfig;
import fx7.r2r.itemlistener.lever.OpenHabLeverListener;
import fx7.r2r.itemlistener.lever.OpenHabLeverConfig;
import fx7.r2r.rest.RestStub;

public class RedstoneTwoRestPlugin extends JavaPlugin implements Coordinator
{
	private static final String STORE_KEY_LEVERS = "levers";
	private List<LeverConfig> leverConfigs;

	private static final String STORE_KEY_REST_STUB = "restStubs";
	private List<RestStub> restStubs;

	private List<ItemListener<?>> itemListeners;
	private List<PlayerCommandListener> playerCommandListeners;

	@Override
	public void onEnable()
	{
		OpenHabLeverListener leverListener = new OpenHabLeverListener(this);
		this.itemListeners = new ArrayList<>();
		this.itemListeners.add(leverListener);

		CommandListener commandListener = new CommandListener(this);
		this.playerCommandListeners = new ArrayList<>();
		this.playerCommandListeners.add(commandListener);

		getServer().getPluginManager().registerEvents(leverListener, this);

		getCommand(CommandListener.CMD_R2RS).setExecutor(commandListener);
		getCommand(CommandListener.CMD_R2RC).setExecutor(commandListener);
	}

	@Override
	public void onDisable()
	{
		for (ItemListener<?> itemListener : this.itemListeners)
		{
			List<?> configs = itemListener.getConfigs();
			getConfig().set(STORE_KEY_LEVERS, configs);
		}
		saveConfig();
	}

	@Override
	public List<LeverConfig> getLeverConfigs()
	{
		List<LeverConfig> leverConfigs = this.leverConfigs;
		if (leverConfigs == null)
		{
			leverConfigs = (List<LeverConfig>) getConfig().getList(STORE_KEY_LEVERS);
			if (leverConfigs == null)
				leverConfigs = new ArrayList<>();
			this.leverConfigs = leverConfigs;
		}
		return leverConfigs;
	}

	@Override
	public List<ItemListener<?>> getItemListeners()
	{
		return this.itemListeners;
	}

	@Override
	public List<PlayerCommandListener> getPlayerCommandListeners()
	{
		return this.playerCommandListeners;
	}

	@Override
	public List<RestStub> getRestStubs()
	{
		List<RestStub> restStubs = this.restStubs;
		if (restStubs == null)
		{
			restStubs = (List<RestStub>) getConfig().getList(STORE_KEY_REST_STUB);
			if (restStubs == null)
				restStubs = new ArrayList<>();
			this.restStubs = restStubs;
		}
		return restStubs;
	}

	@Override
	public FileConfiguration getConfig()
	{
		// TODO why not in onEnable? tried it many times, but doesnt work
		registerConfigurationSerializables();
		return super.getConfig();
	}

	private void registerConfigurationSerializables()
	{
		ConfigurationSerialization.registerClass(OpenHabLeverConfig.class);
		ConfigurationSerialization.registerClass(WorldCoordinates.class);
		ConfigurationSerialization.registerClass(RestStub.class);
	}
}
