package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.AtividadeUsuario;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AtividadesUsuarioDeserializer implements JsonDeserializer<ArrayList<AtividadeUsuario>> {

	private ArrayList<AtividadeUsuario> atividades = new ArrayList<AtividadeUsuario>();

	@Override
	public ArrayList<AtividadeUsuario> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("atividades").getAsJsonArray();

		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			AtividadeUsuario atividade = (AtividadeUsuario) context.deserialize(jsonArray.get(i),
					AtividadeUsuario.class);

			atividades.add(atividade);
		}
		return atividades;
	}

}
