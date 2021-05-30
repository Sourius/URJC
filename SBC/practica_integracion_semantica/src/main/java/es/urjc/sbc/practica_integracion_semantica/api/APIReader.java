package es.urjc.sbc.practica_integracion_semantica.api;

import java.util.Map;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;

public class APIReader {
	protected Gson gson;
	protected Client client;
	protected UriBuilder baseURI;

	public APIReader(String url) {
		gson = new GsonBuilder().setPrettyPrinting().create();
		client = ClientBuilder.newClient(new ClientConfig());
		baseURI = UriBuilder.fromUri(url);
	}

	// get data from baseURL and return as string
	public String readData() {
		WebTarget target = client.target(baseURI);
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JsonObject json = gson.fromJson(response, JsonObject.class);
		return gson.toJson(json);
	}
	
	// get data from baseURL/path with the specified params and return as string
	public String readData(String path, Map<String, String> params) {
		WebTarget target = client.target(baseURI).path(path);
		for (String param : params.keySet()) {
			target = target.queryParam(param, params.get(param));
		}
		System.out.println(target.getUri());
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JsonObject json = gson.fromJson(response, JsonObject.class);
		return gson.toJson(json);
	}

	// get data from baseURL/path and return as string
	public String readData(String path) {
		WebTarget target = client.target(baseURI).path(path);
		System.out.println(target.getUri());
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JsonObject json = gson.fromJson(response, JsonObject.class);
		return gson.toJson(json);
	}
}
