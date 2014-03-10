package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.StatusUsuario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StatusUsuariosSerializer implements JsonSerializer<ArrayList<StatusUsuario>> {

	@Override
	public JsonElement serialize(ArrayList<StatusUsuario> statusUsuarios, Type arg1, JsonSerializationContext context) {

		JsonObject json = new JsonObject();

		JsonArray statusJsonArray = new JsonArray();
		json.add("statusUsuarios", statusJsonArray);

		for (StatusUsuario status : statusUsuarios) {
			statusJsonArray.add(context.serialize(status));
		}

		return json;
	}

}
