package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.estabelecimento.Avaliacao;
import br.com.baladasp.cdp.estabelecimento.Endereco;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EstabelecimentoSerializer implements JsonSerializer<Estabelecimento> {
	@Override
	public JsonElement serialize(Estabelecimento estab, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		processarEstabelecimento(estab, json, context);
		processarEndereco(estab, json);
		processarAvaliacao(estab, context, json);

		return json;
	}

	private void processarAvaliacao(Estabelecimento estab, JsonSerializationContext context, JsonObject json) {
		JsonArray avaliacaoArray = new JsonArray();
		json.add("avaliacoes", avaliacaoArray);
		for (Avaliacao avaliacao : estab.getAvaliacao()) {
			avaliacaoArray.add(context.serialize(avaliacao));
		}
	}

	private void processarEstabelecimento(Estabelecimento estab, JsonObject json, JsonSerializationContext context) {
		json.addProperty("id", estab.getId());
		json.addProperty("nome", estab.getNome());
		json.addProperty("descricaoEstabelecimento", estab.getDescricaoEstabelecimento());
		json.addProperty("urlLogo", estab.getUrlLogo());
		json.addProperty("site", estab.getSite());
		json.addProperty("facebook", estab.getFacebook());
		json.addProperty("twitter", estab.getTwitter());
		json.addProperty("youtube", estab.getYoutube());
		json.addProperty("horarioFuncionamento", estab.gethorarioFuncionamento());
		json.addProperty("telefone", estab.getTelefone());
		json.addProperty("delivery", estab.isDelivery());
		json.addProperty("estacionamento", estab.isEstacionamento());
		json.addProperty("wifi", estab.isWifi());
		json.addProperty("classificacaoEtaria", estab.getClassificacaoEtaria());
		json.addProperty("evento", estab.getEvento());
		json.addProperty("tipoLocal", estab.getCategoria().getCategoria());
		json.addProperty("publico", estab.getPublico());
		json.addProperty("decoracao", estab.getDecoracao());
		json.addProperty("musica", estab.getMusica().getMusica());
		json.addProperty("ambiente", estab.getAmbiente());

		json.add("ranking", context.serialize(estab.getRanking()));

	}

	private void processarEndereco(Estabelecimento estab, JsonObject json) {
		Endereco endereco = estab.getEndereco();

		json.addProperty("endereco", endereco.getEndereco());
		json.addProperty("rua", endereco.getRua());
		json.addProperty("numero", endereco.getNumero());
		json.addProperty("bairro", endereco.getBairro());
		json.addProperty("cep", endereco.getCep());
		json.addProperty("regiao", endereco.getRegiao().getRegiao());
	}

}
