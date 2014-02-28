package br.com.baladasp.cgt.server;

import java.util.ArrayList;

import br.com.baladasp.cgt.usuario.Avaliacao;
import br.com.baladasp.cgt.util.json.serializer.AvaliacaoSerializer;
import br.com.baladasp.cgt.util.json.serializer.StatusUsuariosSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperacoesEstabelecimento {
	protected static Gson gsonSer = new GsonBuilder().registerTypeAdapter(Avaliacao.class, new AvaliacaoSerializer())
			.registerTypeAdapter(ArrayList.class, new StatusUsuariosSerializer()).setPrettyPrinting().create();

}
