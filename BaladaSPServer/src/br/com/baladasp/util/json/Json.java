package br.com.baladasp.util.json;

import com.google.gson.Gson;

public class Json {

	protected Gson gson;

	public String[] jsonToStringArray(Object... args) {
		String[] jsonToString = new String[args.length];
		int i = 0;
		for (Object object : args) {
			jsonToString[i] = gson.toJson(object);
			i++;
		}
		return jsonToString;
	}

	public <T> Object deserializar(String jsonSerialized, Class<T> deserializarClasse) {
		return (deserializarClasse != null) ? gson.fromJson(jsonSerialized, deserializarClasse) : null;
	}

}