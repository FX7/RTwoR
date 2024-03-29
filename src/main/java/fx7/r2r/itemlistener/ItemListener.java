package fx7.r2r.itemlistener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fx7.r2r.rest.RestCallable;

public interface ItemListener<T extends RestCallable>
{
	public Location getClickedLocation(Player player);

	public boolean storeLocation(Player player, Location location, String[] args);

	public List<T> getConfigs();
}
