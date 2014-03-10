package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EstabelecimentosSerializer implements JsonSerializer<ArrayList<Estabelecimento>> {

	@Override
	public JsonElement serialize(ArrayList<Estabelecimento> estabsArray, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		JsonArray estabsJsonArray = new JsonArray();
		json.add("estabelecimentos", estabsJsonArray);

		for (Estabelecimento estabelecimento : estabsArray) {
			estabsJsonArray.add(context.serialize(estabelecimento));
		}
		return json;
	}

}
