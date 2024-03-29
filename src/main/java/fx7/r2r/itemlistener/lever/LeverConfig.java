package fx7.r2r.itemlistener.lever;

import java.util.HashMap;
import java.util.Map;

import fx7.r2r.itemlistener.WorldCoordinates;
import fx7.r2r.rest.RestCallable;

public abstract class LeverConfig implements RestCallable
{
	private static final String PARAM_REST_STUB_NAME = "restStubName";
	private String restStubName;

	private static final String PARAM_COORDINATES = "coordinates";
	private WorldCoordinates coordinates;

	protected LeverConfig(String restStubName, WorldCoordinates coordinates)
	{
		this.restStubName = restStubName;
		this.coordinates = coordinates;
	}

	protected LeverConfig(Map<String, Object> serialized)
	{
		this(//
				(String) serialized.get(PARAM_REST_STUB_NAME), //
				(WorldCoordinates) serialized.get(PARAM_COORDINATES));
	}

	public WorldCoordinates getCoordinates()
	{
		return coordinates;
	}

	public String getRestStubName()
	{
		return restStubName;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> serialized = new HashMap<String, Object>();

		serialized.put(PARAM_REST_STUB_NAME, getRestStubName());
		serialized.put(PARAM_COORDINATES, getCoordinates());

		return serialized;
	}
}
