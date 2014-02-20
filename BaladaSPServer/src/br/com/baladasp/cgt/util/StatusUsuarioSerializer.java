package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.usuario.StatusUsuarios;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StatusUsuarioSerializer implements JsonSerializer<StatusUsuarios> {

	@Override
	public JsonElement serialize(StatusUsuarios statusUsuarios, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("userID", statusUsuarios.getUserID());
		json.addProperty("screenName", statusUsuarios.getScreenName());
		json.addProperty("text", statusUsuarios.getText());
		json.addProperty("profileImageURL", statusUsuarios.getProfileImageURL());
		json.addProperty("createdAt", statusUsuarios.getCreatedAt());

		return json;
	}

}
