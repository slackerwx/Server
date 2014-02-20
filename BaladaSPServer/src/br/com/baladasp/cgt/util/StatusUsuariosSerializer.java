package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.StatusUsuarios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StatusUsuariosSerializer implements JsonSerializer<ArrayList<StatusUsuarios>> {

	@Override
	public JsonElement serialize(ArrayList<StatusUsuarios> statusUsuarios, Type arg1, JsonSerializationContext context) {

		JsonObject json = new JsonObject();

		JsonArray statusJsonArray = new JsonArray();
		json.add("statusUsuarios", statusJsonArray);

		for (StatusUsuarios status : statusUsuarios) {
			statusJsonArray.add(context.serialize(status));
		}

		return json;
	}

}
