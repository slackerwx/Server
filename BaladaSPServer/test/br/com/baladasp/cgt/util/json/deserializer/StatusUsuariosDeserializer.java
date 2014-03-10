package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.StatusUsuario;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class StatusUsuariosDeserializer implements JsonDeserializer<ArrayList<StatusUsuario>> {

	@Override
	public ArrayList<StatusUsuario> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("statusUsuarios").getAsJsonArray();

		ArrayList<StatusUsuario> listStatus = new ArrayList<StatusUsuario>();
		for (int i = 0, size = jsonArray.size(); i < size; i++) {

			StatusUsuario statusUsuario = context.deserialize(jsonArray.get(i), StatusUsuario.class);

			listStatus.add(statusUsuario);
		}
		return listStatus;
	}

}
