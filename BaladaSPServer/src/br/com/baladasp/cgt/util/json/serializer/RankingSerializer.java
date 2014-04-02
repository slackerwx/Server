package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Ranking;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RankingSerializer implements JsonSerializer<ArrayList<Ranking>> {

	@Override
	public JsonElement serialize(ArrayList<Ranking> rankingArray, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		JsonArray rankingJsonArray = new JsonArray();
		json.add("ranking", rankingJsonArray);

		for (Ranking ranking : rankingArray) {
			rankingJsonArray.add(context.serialize(ranking));
		}
		return json;
	}

}
