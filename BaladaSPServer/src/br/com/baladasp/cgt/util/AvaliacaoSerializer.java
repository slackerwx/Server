package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.estabelecimento.Avaliacao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AvaliacaoSerializer implements JsonSerializer<Avaliacao> {

	@Override
	public JsonElement serialize(Avaliacao avaliacao, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty("avaliacao", avaliacao.getAvaliacao());
		json.addProperty("preco", avaliacao.getPreco());
		json.addProperty("atendimento", avaliacao.getAtendimento());
		json.addProperty("cardapio", avaliacao.getCardapio());
		json.addProperty("ambiente", avaliacao.getAmbiente());
		json.addProperty("estacionamento", avaliacao.getEstacionamento());
		json.addProperty("geral", avaliacao.getGeral());
		json.addProperty("atracao", avaliacao.getAtracao());
		json.addProperty("localizacao", avaliacao.getLocalizacao());
		json.addProperty("total", avaliacao.getMedia());
		json.addProperty("comentario", avaliacao.getComentario());
		json.addProperty("dataAvaliacao", avaliacao.getDataAvaliacao().toString());

		json.add("usuario", context.serialize(avaliacao.getUsuario()));

		return json;
	}

}
