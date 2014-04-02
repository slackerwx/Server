package br.com.baladasp.util.json;

import java.util.ArrayList;

import br.com.baladasp.cgt.util.json.deserializer.AtividadesUsuarioDeserializer;

import com.google.gson.GsonBuilder;

public class JsonAtividadeUsuario extends Json {
	private static final JsonAtividadeUsuario INSTANCE = new JsonAtividadeUsuario();

	private JsonAtividadeUsuario() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new AtividadesUsuarioDeserializer())
				.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	}

	public static JsonAtividadeUsuario getInstance() {
		return INSTANCE;
	}
}
