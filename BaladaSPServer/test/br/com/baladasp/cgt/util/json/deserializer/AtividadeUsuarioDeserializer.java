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

	@Override
	public AtividadeUsuario deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = jsonElement.getAsJsonObject();

		Usuario usuario = (Usuario) context.deserialize(jsonObject.get("usuario").getAsJsonObject(), Usuario.class);
		estabelecimento = (Estabelecimento) context.deserialize(jsonObject.get("estabelecimento").getAsJsonObject(), Estabelecimento.class);

		String tipoAtividade = jsonObject.get("tipoAtividade").getAsString();

		if (tipoAtividade.equals("Checkin")) {
			Checkin checkin = new Checkin(estabelecimento);
			atividadeUsuario = new AtividadeUsuario(usuario, checkin);
		}
		if (tipoAtividade.equals("Avaliacao")) {
			Avaliacao avaliacao = processarAvaliacao(jsonObject);
			atividadeUsuario = new AtividadeUsuario(usuario, avaliacao);
		}

		return atividadeUsuario;
	}

	private Avaliacao processarAvaliacao(JsonObject jsonObject) {

		int preco = (jsonObject.get("preco").getAsInt());
		int atendimento = (jsonObject.get("atendimento").getAsInt());
		int cardapio = (jsonObject.get("cardapio").getAsInt());
		int ambiente = jsonObject.get("ambiente").getAsInt();
		int estacionamento = (jsonObject.get("estacionamento").getAsInt());
		int geral = (jsonObject.get("geral").getAsInt());
		int atracao = (jsonObject.get("atracao").getAsInt());
		int localizacao = (jsonObject.get("localizacao").getAsInt());
		String comentario = (jsonObject.get("comentario").getAsString());

		return new Avaliacao(preco, atendimento, cardapio, ambiente, estacionamento, geral, atracao, localizacao, comentario, estabelecimento);
	}

}
