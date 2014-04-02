package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.AtividadeUsuario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AtividadesUsuarioSerializer implements JsonSerializer<ArrayList<AtividadeUsuario>> {

	@Override
	public JsonElement serialize(ArrayList<AtividadeUsuario> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		JsonArray avaliacoesArray = new JsonArray();
		json.add("atividades", avaliacoesArray);

		for (AtividadeUsuario atividades : src) {
			avaliacoesArray.add(context.serialize(atividades));
		}

		return json;
	}

}
