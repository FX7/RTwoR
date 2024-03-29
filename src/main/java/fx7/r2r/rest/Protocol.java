package fx7.r2r.rest;

public enum Protocol
{
	HTTP(80), HTTPS(433);

	private final int defaultPort;

	private Protocol(int defaultPort)
	{
		this.defaultPort = defaultPort;
	}

	public int getDefaultPort()
	{
		return defaultPort;
	}

	public String forURL()
	{
		return name().toLowerCase();
	}
}
