package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cgt.usuario.StatusUsuario;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class StatusUsuariosDeserializer implements JsonDeserializer<ArrayList<StatusUsuario>> {

	@Override
	public ArrayList<StatusUsuario> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
 
		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("statusUsuarios").getAsJsonArray();

		ArrayList<StatusUsuario> listStatus = new ArrayList<StatusUsuario>();
		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			JsonObject statusJson = jsonArray.get(i).getAsJsonObject();

			long userID = statusJson.get("userID").getAsLong();
			String screenName = statusJson.get("screenName").getAsString();
			String text = statusJson.get("text").getAsString();
			String profileImageURL = statusJson.get("profileImageURL").getAsString();
			String createdAt = statusJson.get("createdAt").getAsString();

			StatusUsuario statusUsuario = new StatusUsuario(userID, screenName, createdAt, profileImageURL, text);

			listStatus.add(statusUsuario);
		}
		return listStatus;
	}

}
