package fx7.r2r.itemlistener.lever;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.configuration.serialization.SerializableAs;

import fx7.r2r.itemlistener.WorldCoordinates;

@SerializableAs("OpenHabLeverConfig")
public class OpenHabLeverConfig extends LeverConfig
{
	private static final String PARAM_ITEM_NAME = "itemName";
	private String itemName;

	public OpenHabLeverConfig(String stubName, String itemName, WorldCoordinates coordinates)
	{
		super(stubName, coordinates);
		this.itemName = itemName;
	}

	private OpenHabLeverConfig(Map<String, Object> serialized)
	{
		super(serialized);
		this.itemName = (String) serialized.get(PARAM_ITEM_NAME);
	}

	@Override
	public Map<String, String> getHeader(Block block)
	{
		return null;
	}

	@Override
	public List<String> getPath(Block block)
	{
		return Arrays.asList(itemName);
	}

	@Override
	public String getPostData(Block block)
	{
		BlockData blockData = block.getBlockData();
		if (blockData instanceof Powerable)
		{
			if (((Powerable) blockData).isPowered())
				return "OFF";
			return "ON";
		}
		return null;
	}

	public static OpenHabLeverConfig deserialize(Map<String, Object> serialized)
	{
		return new OpenHabLeverConfig(serialized);
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();

		serialized.put(PARAM_ITEM_NAME, itemName);

		return serialized;
	}

	@Override
	public String toString()
	{
		return "OpenHabLeverConfig [itemName=" + itemName + ", getCoordinates()=" + getCoordinates()
				+ ", getRestStubName()=" + getRestStubName() + "]";
	}
}
