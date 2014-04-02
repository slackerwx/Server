package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EstabelecimentosDeserializer implements JsonDeserializer<ArrayList<Estabelecimento>> {

	private ArrayList<Estabelecimento> listEstab = new ArrayList<Estabelecimento>();

	@Override
	public ArrayList<Estabelecimento> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("estabelecimentos").getAsJsonArray();

		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			Estabelecimento estab = context.deserialize(jsonArray.get(i), Estabelecimento.class);

			listEstab.add(estab);
		}
		return listEstab;
	}

}
