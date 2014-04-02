package br.com.baladasp.util.json;

import java.util.ArrayList;


import br.com.baladasp.cgt.util.json.deserializer.AvaliacoesDeserializer;

import com.google.gson.GsonBuilder;

public class JsonAvaliacoes extends Json {

	private static final JsonAvaliacoes INSTANCE = new JsonAvaliacoes();

	private JsonAvaliacoes() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new AvaliacoesDeserializer()).setPrettyPrinting()
				.create();
	}

	public static JsonAvaliacoes getInstance() {
		return INSTANCE;
	}

}