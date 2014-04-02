package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Ranking;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class RankingDeserializer implements JsonDeserializer<ArrayList<Ranking>> {

	private ArrayList<Ranking> listRanking = new ArrayList<Ranking>();

	@Override
	public ArrayList<Ranking> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("ranking").getAsJsonArray();

		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			Ranking ranking = context.deserialize(jsonArray.get(i), Ranking.class);

			listRanking.add(ranking);
		}
		return listRanking;
	}

}
