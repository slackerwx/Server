package br.com.baladasp.util.json;

import java.util.ArrayList;

import br.com.baladasp.cgt.util.json.deserializer.StatusUsuariosDeserializer;

import com.google.gson.GsonBuilder;

public class JsonUsuario extends Json {
	private static final JsonUsuario INSTANCE = new JsonUsuario();

	private JsonUsuario() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new StatusUsuariosDeserializer())
				.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	}

	public static JsonUsuario getInstance() {
		return INSTANCE;
	}

}
