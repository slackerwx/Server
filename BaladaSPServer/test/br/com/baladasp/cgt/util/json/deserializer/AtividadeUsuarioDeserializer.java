package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.usuario.AtividadeUsuario;
import br.com.baladasp.cgt.usuario.Avaliacao;
import br.com.baladasp.cgt.usuario.Checkin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AtividadeUsuarioDeserializer implements JsonDeserializer<AtividadeUsuario> {

	private AtividadeUsuario atividadeUsuario;
	private Estabelecimento estabelecimento;
	private Usuario usuario;

	@Override
	public AtividadeUsuario deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = jsonElement.getAsJsonObject();

		usuario = (Usuario) context.deserialize(jsonObject.get("usuario").getAsJsonObject(), Usuario.class);

		estabelecimento = (Estabelecimento) context.deserialize(jsonObject.get("estabelecimento").getAsJsonObject(), Estabelecimento.class);

		String tipoAtividade = jsonObject.get("tipoAtividade").getAsString();

		if (tipoAtividade.equals("Checkin")) {
			Checkin checkin = new Checkin(estabelecimento);
			atividadeUsuario = new AtividadeUsuario(usuario, checkin);
		}
		if (tipoAtividade.equals("Avaliacao")) {
			Avaliacao avaliacao = (Avaliacao) context.deserialize(jsonObject.get("avaliacao").getAsJsonObject(), Avaliacao.class);
			avaliacao.setEstabelecimento(estabelecimento);
			atividadeUsuario = new AtividadeUsuario(usuario, avaliacao);
		}

		return atividadeUsuario;
	}

}
