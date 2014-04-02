package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.Avaliacao;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AvaliacoesDeserializer implements JsonDeserializer<ArrayList<Avaliacao>> {

	private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	@Override
	public ArrayList<Avaliacao> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("avaliacoes").getAsJsonArray();

		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			Avaliacao estab = (Avaliacao) context.deserialize(jsonArray.get(i), Avaliacao.class);

			avaliacoes.add(estab);
		}
		return avaliacoes;
	}

}
