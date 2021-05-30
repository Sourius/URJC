package es.urjc.sbc.practica_integracion_semantica.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WildLifeAPIReader extends APIReader {
	private ObjectMapper mapper;
	private Map<String, List<String>> group_family;
	private Map<String, List<String>> family_species;
	private Map<String, JsonNode> specie_info;

	public WildLifeAPIReader() {
		super("https://apps.des.qld.gov.au/");
		mapper = new ObjectMapper();
		group_family = new HashMap<>();
		family_species = new HashMap<>();
		specie_info = new HashMap<>();
	}

	private boolean isDesiredGroup(String term) {
		switch (term) {
		case "amphibia":
		case "aves":
		case "mammalia":
		case "reptilia":
			return true;
		default:
			return false;
		}
	}

	public void loadDataFromAPI() {
		loadGroupData();
		loadFamilyDataDemo();
		loadSpeciesDataDemo();
	}

	// load group (class) data from api 
	private void loadGroupData() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("op", "getclassnames");
		params.put("kingdom", "animals");
		String json = readData("species/", params);

		try {
			JsonNode groups = mapper.readTree(json).get("Class");
			for (JsonNode group : groups) {
				String groupName = group.get("ClassName").asText().toLowerCase();

				if (isDesiredGroup(groupName)) {
					List<String> values = new LinkedList<>();
					group_family.put(groupName, values);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// load family data from api
	private void loadFamilyData() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("op", "getfamilynames");
		params.put("kingdom", "animals");

		for (String groupName : group_family.keySet()) {
			params.put("class", groupName);
			String json = readData("species/", params);

			try {
				JsonNode families = mapper.readTree(json).get("Family");

				for (JsonNode family : families) {
					String familyName = family.get("FamilyName").asText().toLowerCase();
					group_family.get(groupName).add(familyName);
					List<String> values = new LinkedList<>();
					family_species.put(familyName, values);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// laod specie data from api
	private void loadSpeciesData() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("op", "getspecies");

		for (String familyName : family_species.keySet()) {
			params.put("family", familyName);
			String json = readData("species/", params);

			try {
				JsonNode species = mapper.readTree(json).get("Species");

				for (JsonNode specie : species) {
					String specieName = specie.get("ScientificName").asText().toLowerCase();
					family_species.get(familyName).add(specieName);
					specie_info.put(specieName, specie);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// load 5 family at most of each group
	private void loadFamilyDataDemo() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("op", "getfamilynames");
		params.put("kingdom", "animals");

		for (String groupName : group_family.keySet()) {
			params.put("class", groupName);
			String json = readData("species/", params);
			int i = 0;

			try {
				JsonNode families = mapper.readTree(json).get("Family");

				for (JsonNode family : families) {
					// get 5 family names from each group
					i++;
					if (i == 5) {
						break;
					}

					String familyName = family.get("FamilyName").asText().toLowerCase();
					group_family.get(groupName).add(familyName);
					List<String> values = new LinkedList<>();
					family_species.put(familyName, values);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// load 20 species at most from each family
	private void loadSpeciesDataDemo() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("op", "getspecies");

		for (String familyName : family_species.keySet()) {
			int i = 0;

			params.put("family", familyName);
			String json = readData("species/", params);

			try {
				JsonNode species = mapper.readTree(json).get("Species");

				for (JsonNode specie : species) {
					// get 5 species from each family
					i++;
					if (i == 20) {
						break;
					}

					String specieName = specie.get("ScientificName").asText().toLowerCase();
					family_species.get(familyName).add(specieName);
					specie_info.put(specieName, specie);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* 
	 * return species info given a family name
	 */
	public List<String> getSpecies(String familyName) {
		return family_species.get(familyName);
	}

	/*
	 * return families given a group name
	 */
	public List<String> getFamilies(String groupName) {
		return group_family.get(groupName);
	}

	// return the groups of animals in api
	public Set<String> getGroups() {
		return group_family.keySet();
	}

	// return information given scientific name and the field
	public String getSpecieInfo(String specieName, String field) {
		return specie_info.get(specieName).get(field).asText();
	}

	// filter name used in api so it can be used in wikipedia
	public String filterSpecieName(String specieName) {
		String name = filterName(specieName);
		if (name.endsWith("_sp.")) {
			name = name.substring(0, name.length() - 4);
		} else if (name.endsWith("_spp.")) {
			name = name.substring(0, name.length() - 5);
		}
		return name;
	}

	// filter name used in api so it can be used in wikipedia
	public String filterName(String name) {
		String aux = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		aux = aux.replaceAll("\s+", "_");
		return aux;
	}

	// tranlate group name to the name in the ontology
	public String translateGroupName(String groupName) {
		switch (groupName) {
		case "amphibia":
			return "Anfibio";
		case "reptilia":
			return "Reptil";
		case "mammalia":
			return "Mamifero";
		case "aves":
			return "Aves";
		}
		return null;
	}
}
