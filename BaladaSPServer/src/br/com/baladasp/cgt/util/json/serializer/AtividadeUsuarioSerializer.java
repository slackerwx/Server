package br.com.baladasp.cgt.util.json.serializer;

import java.lang.reflect.Type;

import br.com.baladasp.cgt.usuario.AtividadeUsuario;
import br.com.baladasp.cgt.usuario.Avaliacao;
import br.com.baladasp.cgt.usuario.Checkin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AtividadeUsuarioSerializer implements JsonSerializer<AtividadeUsuario> {

	@Override
	public JsonElement serialize(AtividadeUsuario atividadeUsuario, Type arg1, JsonSerializationContext context) {
		JsonObject jsonAtividadeUsuario = new JsonObject();

		jsonAtividadeUsuario.addProperty("tipoAtividade", atividadeUsuario.getTipoAtividadeString());
		jsonAtividadeUsuario.addProperty("dataAtividade", atividadeUsuario.getDataAtividade());

		Avaliacao avaliacao = atividadeUsuario.getAvaliacao();
		if (avaliacao != null) {
			processarAvaliacao(avaliacao, jsonAtividadeUsuario, context);
		}
		Checkin checkin = atividadeUsuario.getCheckin();
		if (checkin != null) {
			processarCheckin(checkin, jsonAtividadeUsuario, context);
		}

		jsonAtividadeUsuario.add("usuario", context.serialize(atividadeUsuario.getUsuario()));

		return jsonAtividadeUsuario;
	}

	private void processarCheckin(Checkin checkin, JsonObject jsonAtividadeUsuario, JsonSerializationContext context) {
		jsonAtividadeUsuario.add("checkin", context.serialize(checkin));
		
		jsonAtividadeUsuario.add("estabelecimento", context.serialize(checkin.getEstabelecimento()));
	}

	private void processarAvaliacao(Avaliacao avaliacao, JsonObject jsonAtividadeUsuario, JsonSerializationContext context) {
		jsonAtividadeUsuario.add("avaliacao", context.serialize(avaliacao));

		jsonAtividadeUsuario.add("estabelecimento", context.serialize(avaliacao.getEstabelecimento()));
	}

}
