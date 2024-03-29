package fx7.r2r.rest;

import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface RestCallable extends ConfigurationSerializable
{
	public Map<String, String> getHeader(Block block);

	public List<String> getPath(Block block);

	public String getPostData(Block block);
}
