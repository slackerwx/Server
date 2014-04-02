package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.Avaliacao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AvaliacoesSerializer implements JsonSerializer<ArrayList<Avaliacao>> {

	@Override
	public JsonElement serialize(ArrayList<Avaliacao> avaliacoes, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		JsonArray avaliacoesArray = new JsonArray();
		json.add("avaliacoes", avaliacoesArray);

		for (Avaliacao avaliacao : avaliacoes) {
			avaliacoesArray.add(context.serialize(avaliacao));
		}

		return json;
	}

}
