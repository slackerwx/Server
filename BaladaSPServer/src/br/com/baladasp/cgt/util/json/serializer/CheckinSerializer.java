package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;

import br.com.baladasp.cgt.usuario.Checkin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CheckinSerializer implements JsonSerializer<Checkin> {

	@Override
	public JsonElement serialize(Checkin checkin, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty("dataAtividade", checkin.getDataAtividade());

		return json;
	}
}
