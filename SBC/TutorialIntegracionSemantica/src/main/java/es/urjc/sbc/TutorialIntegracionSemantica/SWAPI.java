package es.urjc.sbc.TutorialIntegracionSemantica;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

public class SWAPI {
	private String url = "https://swapi.dev/api/";

	public void readData() {
		ClientConfig cconfig = new ClientConfig();
		Client client = ClientBuilder.newClient(cconfig);
		UriBuilder uri = UriBuilder.fromUri(url);
		WebTarget target = client.target(uri);
		Response response = target.path("people/1/").request().accept(MediaType.APPLICATION_JSON).get();
		System.out.println(new Gson().toJson(response));

	}
}
