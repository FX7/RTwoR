package fx7.r2r.rest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("RestStub")
public class RestStub implements ConfigurationSerializable
{
	private static String PARAM_NAME = "name";
	private String name;

	private static String PARAM_PROTOCOL = "protocol";
	private Protocol protocol = Protocol.HTTP;

	private static String PARAM_IP_OR_DOMAIN = "ipOrDomain";
	private String ipOrDomain;

	private static String PARAM_PORT = "port";
	private int port = -1;

	private static String PARAM_METHOD = "method";
	private Method method = Method.POST;

	private static String PARAM_HEADER = "header";
	private Map<String, String> header = new HashMap<String, String>();

	private static String PARAM_PATH = "path";
	private List<String> path = new ArrayList<String>();

	public RestStub(String name, Protocol protocol, String ipOrDomain, int port, Method method)
	{
		this.name = name;
		this.protocol = protocol;
		this.ipOrDomain = ipOrDomain;
		this.port = port;
		this.method = method;
	}

	public RestStub addPath(String path)
	{
		this.path.add(path);
		return this;
	}

	public RestStub addHeader(String key, String value)
	{
		this.header.put(key, value);
		return this;
	}

	public String getName()
	{
		return name;
	}

	// public int bla()
	// {
	// String url = "http://192.168.10.3:8080/rest/items/poweroutletASwitch";
	// URL urlObj = new URL(url);
	// HttpURLConnection connection = (HttpURLConnection)
	// urlObj.openConnection();
	//
	// connection.setRequestMethod("POST");
	// connection.setRequestProperty("Content-Type", "text/plain");
	// connection.setRequestProperty("Accept", "application/json");
	// connection.setDoOutput(true);
	//
	// DataOutputStream outputStream = new
	// DataOutputStream(connection.getOutputStream());
	//
	// if (off)
	// outputStream.writeBytes("OFF");
	// else
	// outputStream.writeBytes("ON");
	//
	// outputStream.flush();
	// outputStream.close();
	//
	// return connection.getResponseCode();
	// }

	public int call(RestCallable callable, Block block) throws IOException
	{
		List<String> completePath = new ArrayList<>();
		completePath.addAll(path);
		List<String> callablePath = callable.getPath(block);
		if (callablePath != null)
			completePath.addAll(callablePath);

		URL url = buildURL(completePath);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method.name());
		appendHeader(connection, header);
		appendHeader(connection, callable.getHeader(block));

		if (method == Method.POST)
		{
			connection.setDoOutput(true);
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			String postData = callable.getPostData(block);
			if (postData != null)
				outputStream.writeBytes(postData);
			outputStream.flush();
			outputStream.close();
		}

		
		
		return connection.getResponseCode();
	}

	private void appendHeader(HttpURLConnection connection, Map<String, String> header)
	{
		if (header == null)
			return;

		for (Entry<String, String> head : header.entrySet())
		{
			connection.setRequestProperty(head.getKey(), head.getValue());
		}
	}

	private URL buildURL(List<String> path) throws MalformedURLException
	{
		String url = protocol.forURL() + "://" + ipOrDomain + ":" + getPort() + "/" + buildPath(path);
		return new URL(url);
	}

	private String buildPath(List<String> path)
	{
		if (path == null)
			return "";
		return path.stream().collect(Collectors.joining("/"));
	}

	private int getPort()
	{
		if (port <= 0)
			return protocol.getDefaultPort();
		return port;
	}

	public static RestStub deserialize(Map<String, Object> serialized)
	{
		RestStub stub = new RestStub(//
				(String) serialized.get(PARAM_NAME), //
				Protocol.valueOf(((String) serialized.get(PARAM_PROTOCOL))), //
				(String) serialized.get(PARAM_IP_OR_DOMAIN), //
				((Integer) serialized.get(PARAM_PORT)).intValue(), //
				Method.valueOf(((String) serialized.get(PARAM_METHOD))));

		List<String> paths = (List<String>) serialized.get(PARAM_PATH);
		for (String path : paths)
			stub.addPath(path);

		Map<String, String> headers = (Map<String, String>) serialized.get(PARAM_HEADER);
		for (Entry<String, String> header : headers.entrySet())
			stub.addHeader(header.getKey(), header.getValue());

		return stub;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> serialized = new HashMap<String, Object>();

		serialized.put(PARAM_NAME, name);
		serialized.put(PARAM_PROTOCOL, protocol.name());
		serialized.put(PARAM_IP_OR_DOMAIN, ipOrDomain);
		serialized.put(PARAM_PORT, getPort());
		serialized.put(PARAM_METHOD, method.name());
		serialized.put(PARAM_PATH, path);
		serialized.put(PARAM_HEADER, header);

		return serialized;
	}
}
