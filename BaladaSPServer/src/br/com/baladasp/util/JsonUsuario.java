package br.com.baladasp.util;

import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cgt.usuario.AtividadeUsuario;
import br.com.baladasp.cgt.usuario.Avaliacao;
import br.com.baladasp.cgt.usuario.Checkin;
import br.com.baladasp.cgt.util.json.deserializer.AvaliacaoDeserializer;
import br.com.baladasp.cgt.util.json.deserializer.EstabelecimentoDeserializer;
import br.com.baladasp.cgt.util.json.deserializer.StatusUsuariosDeserializer;
import br.com.baladasp.cgt.util.json.serializer.AtividadeUsuarioSerializer;
import br.com.baladasp.cgt.util.json.serializer.AvaliacaoSerializer;
import br.com.baladasp.cgt.util.json.serializer.CheckinSerializer;
import br.com.baladasp.cgt.util.json.serializer.EstabelecimentoSerializer;
import br.com.baladasp.cgt.util.json.serializer.RankingSerializer;

import com.google.gson.GsonBuilder;

public class JsonUsuario extends Json {

	public JsonUsuario() {
		gson = new GsonBuilder().registerTypeAdapter(AtividadeUsuario.class, new AtividadeUsuarioSerializer())
								.registerTypeAdapter(Ranking.class, new RankingSerializer())
								.registerTypeAdapter(Checkin.class, new CheckinSerializer())
								.registerTypeAdapter(ArrayList.class, new StatusUsuariosDeserializer())
								.registerTypeAdapter(Estabelecimento.class, new EstabelecimentoDeserializer())
								.registerTypeAdapter(Estabelecimento.class, new EstabelecimentoSerializer())
								.registerTypeAdapter(Avaliacao.class, new AvaliacaoDeserializer())
								.registerTypeAdapter(Avaliacao.class, new AvaliacaoSerializer())
								.setPrettyPrinting()
								.create();
	}

}
