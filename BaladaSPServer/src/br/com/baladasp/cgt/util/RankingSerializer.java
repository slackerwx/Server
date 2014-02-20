package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.estabelecimento.Ranking;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RankingSerializer implements JsonSerializer<Ranking> {

	@Override
	public JsonElement serialize(Ranking ranking, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("id", ranking.getId());
		json.addProperty("pontos", ranking.getPontos());
		json.addProperty("mediaAvaliacoes", ranking.getMediaAvaliacoes());
		json.addProperty("categoria", ranking.getCategoria().getCategoria());
		return json;
	}
}