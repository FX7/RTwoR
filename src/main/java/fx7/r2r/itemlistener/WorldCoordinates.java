package fx7.r2r.itemlistener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("WorldCoordinates")
public class WorldCoordinates implements ConfigurationSerializable
{
	private static final String PARAM_WORLD_NAME = "worldName";
	private String worldName;

	private static final String PARAM_X = "x";
	private double x;

	private static final String PARAM_Y = "y";
	private double y;

	private static final String PARAM_Z = "z";
	private double z;

	public WorldCoordinates(Location location)
	{
		this(//
				location.getWorld().getName(), //
				location.getX(), //
				location.getY(), //
				location.getZ());
	}

	public WorldCoordinates(String worldName, double x, double y, double z)
	{
		super();
		this.worldName = worldName;
		this.x = Math.ceil(x);
		this.y = Math.ceil(y);
		this.z = Math.ceil(z);
	}

	public String getWorldName()
	{
		return worldName;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public static WorldCoordinates deserialize(Map<String, Object> serialized)
	{
		return new WorldCoordinates(//
				(String) serialized.get(PARAM_WORLD_NAME), //
				((Double) serialized.get(PARAM_X)).doubleValue(), //
				((Double) serialized.get(PARAM_Y)).doubleValue(), //
				((Double) serialized.get(PARAM_Z)).doubleValue());
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> serialized = new HashMap<String, Object>();

		serialized.put(PARAM_WORLD_NAME, getWorldName());
		serialized.put(PARAM_X, getX());
		serialized.put(PARAM_Y, getY());
		serialized.put(PARAM_Z, getZ());

		return serialized;
	}

	public boolean matches(Location location)
	{
		if (location == null)
			return false;

		if (Math.ceil(location.getX()) != getX() //
				|| Math.ceil(location.getY()) != getY() //
				|| Math.ceil(location.getZ()) != getZ())
			return false;

		World world = location.getWorld();
		return world != null && getWorldName().equals(world.getName());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((worldName == null) ? 0 : worldName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldCoordinates other = (WorldCoordinates) obj;
		if (worldName == null)
		{
			if (other.worldName != null)
				return false;
		} else if (!worldName.equals(other.worldName))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Coordinates [worldName=" + worldName + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
