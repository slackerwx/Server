package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;

import br.com.baladasp.cgt.usuario.Avaliacao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AvaliacaoSerializer implements JsonSerializer<Avaliacao> {

	@Override
	public JsonElement serialize(Avaliacao avaliacao, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty("preco", avaliacao.getPreco());
		json.addProperty("atendimento", avaliacao.getAtendimento());
		json.addProperty("cardapio", avaliacao.getCardapio());
		json.addProperty("ambiente", avaliacao.getAmbiente());
		json.addProperty("estacionamento", avaliacao.getEstacionamento());
		json.addProperty("geral", avaliacao.getGeral());
		json.addProperty("atracao", avaliacao.getAtracao());
		json.addProperty("localizacao", avaliacao.getLocalizacao());
		json.addProperty("total", avaliacao.getMediaAvaliacao());
		json.addProperty("comentario", avaliacao.getComentario());
		json.addProperty("dataAvaliacao", avaliacao.getDataAtividade().toString());

		//json.add("atividadeUsuario", context.serialize(avaliacao.getAtividadeUsuario()));

		return json;
	}

}
