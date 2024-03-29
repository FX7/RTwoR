package fx7.r2r;

import java.util.List;

import fx7.r2r.commandlistener.PlayerCommandListener;
import fx7.r2r.itemlistener.ItemListener;
import fx7.r2r.itemlistener.lever.LeverConfig;
import fx7.r2r.rest.RestStub;

public interface Coordinator
{
	public List<LeverConfig> getLeverConfigs();

	public List<ItemListener<?>> getItemListeners();

	public List<PlayerCommandListener> getPlayerCommandListeners();

	public List<RestStub> getRestStubs();

	public default RestStub getRestStub(String stubName)
	{
		return getRestStubs().stream().filter(s -> s.getName().equals(stubName)).findFirst().orElse(null);
	}
}
