package br.com.baladasp.util;

import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cgt.usuario.AtividadeUsuario;
import br.com.baladasp.cgt.usuario.Checkin;
import br.com.baladasp.cgt.util.json.deserializer.EstabelecimentoDeserializer;
import br.com.baladasp.cgt.util.json.deserializer.StatusUsuariosDeserializer;
import br.com.baladasp.cgt.util.json.serializer.AtividadeUsuarioSerializer;
import br.com.baladasp.cgt.util.json.serializer.CheckinSerializer;
import br.com.baladasp.cgt.util.json.serializer.RankingSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

	static Gson gson = new GsonBuilder().registerTypeAdapter(AtividadeUsuario.class, new AtividadeUsuarioSerializer())
									.registerTypeAdapter(Ranking.class, new RankingSerializer())
									.registerTypeAdapter(Checkin.class, new CheckinSerializer())
									.registerTypeAdapter(Estabelecimento.class, new EstabelecimentoDeserializer())
									.registerTypeAdapter(ArrayList.class, new StatusUsuariosDeserializer())
									.setPrettyPrinting().create();

	public static String[] jsonToString(Object... args) {
		String[] jsonToString = new String[args.length];
		int i = 0;
		for (Object object : args) {
			jsonToString[i] = gson.toJson(object);
			i++;
		}
		return jsonToString;
	}

	public static <T> Object deserializar(String jsonSerialized, Class<T> deserializarClasse) {
		return (deserializarClasse != null) ? gson.fromJson(jsonSerialized, deserializarClasse) : null;
	}
}
