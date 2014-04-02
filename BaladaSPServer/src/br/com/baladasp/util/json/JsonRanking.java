package br.com.baladasp.util.json;

import java.util.ArrayList;

import br.com.baladasp.cgt.util.json.deserializer.RankingDeserializer;

import com.google.gson.GsonBuilder;

public class JsonRanking extends Json {

	private static final JsonRanking INSTANCE = new JsonRanking();

	private JsonRanking() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new RankingDeserializer()).setPrettyPrinting()
				.create();
	}

	public static JsonRanking getInstance() {
		return INSTANCE;
	}

}