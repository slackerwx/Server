package br.com.baladasp.util;

import java.util.ArrayList;

import br.com.baladasp.cgt.util.json.deserializer.StatusUsuariosDeserializer;

import com.google.gson.GsonBuilder;

public class JsonUsuario extends Json {

	public JsonUsuario() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new StatusUsuariosDeserializer())
								.excludeFieldsWithoutExposeAnnotation()
								.setPrettyPrinting()
								.create();
	}

}
