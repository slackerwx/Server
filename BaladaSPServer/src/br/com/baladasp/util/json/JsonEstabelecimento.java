package br.com.baladasp.util.json;

import java.util.ArrayList;

import br.com.baladasp.cgt.util.json.deserializer.EstabelecimentosDeserializer;

import com.google.gson.GsonBuilder;

public class JsonEstabelecimento extends Json {

	private static final JsonEstabelecimento INSTANCE = new JsonEstabelecimento();

	private JsonEstabelecimento() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new EstabelecimentosDeserializer())
				.setPrettyPrinting().create();
	}

	public static JsonEstabelecimento getInstance() {
		return INSTANCE;
	}

}
