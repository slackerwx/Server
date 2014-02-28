package br.com.baladasp.cgt.util.json.deserializer;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Endereco;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Musica;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.estabelecimento.Regiao;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EstabelecimentoDeserializer implements JsonDeserializer<Estabelecimento> {

	@Override
	public Estabelecimento deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

		JsonObject json = jsonElement.getAsJsonObject();

		long id = json.get("id").getAsLong();
		String nome = json.get("nome").getAsString();
		String descricaoEstabelecimento = json.get("descricaoEstabelecimento").getAsString();
		String urlLogo = json.get("urlLogo").getAsString();
		String site = json.get("site").getAsString();
		String facebook = json.get("facebook").getAsString();
		String twitter = json.get("twitter").getAsString();
		String youtube = json.get("youtube").getAsString();
		String horarioFuncionamento = json.get("horarioFuncionamento").getAsString();
		String telefone = json.get("telefone").getAsString();
		boolean delivery = json.get("delivery").getAsBoolean();
		boolean estacionamento = json.get("estacionamento").getAsBoolean();
		boolean wifi = json.get("wifi").getAsBoolean();
		String classificacaoEtaria = json.get("classificacaoEtaria").getAsString();
		String evento = json.get("evento").getAsString();
		String publico = json.get("publico").getAsString();
		String decoracao = json.get("decoracao").getAsString();
		String ambiente = json.get("ambiente").getAsString();
		int qtdAvaliacoes = json.get("qtdAvaliacoes").getAsInt();

		JsonObject jsnObjMusica = json.get("musica").getAsJsonObject();
		Musica musica = new Musica(jsnObjMusica.get("musica").getAsString());
		JsonObject jsnObjCategoria = json.get("categoria").getAsJsonObject();
		Categoria categoria = new Categoria(jsnObjCategoria.get("categoria").getAsString());

		JsonElement elementRanking = json.get("ranking");

		Ranking ranking = null;
		if (elementRanking != null) {
			JsonObject jsnObj = elementRanking.getAsJsonObject();
			ranking = processarRanking(jsnObj);
		}

		Endereco endereco = processarEndereco(json.get("endereco").getAsJsonObject());

		Estabelecimento estabelecimento = new Estabelecimento(id, nome, descricaoEstabelecimento, urlLogo, site, facebook, twitter, youtube, horarioFuncionamento, telefone,
				delivery, estacionamento, wifi, classificacaoEtaria, evento, publico, decoracao, ambiente, musica, categoria,  endereco, qtdAvaliacoes);
		
		//TODO decidir a melhor maneira p/ implementar isso
		ranking.setEstabelecimento(estabelecimento);
		estabelecimento.setRanking(ranking);
		
		return estabelecimento; 

	}

	private Endereco processarEndereco(JsonObject jsonObject) {

		long endereco = (jsonObject.get("endereco").getAsLong());
		String rua = (jsonObject.get("rua").getAsString());
		String numero = (jsonObject.get("numero").getAsString());
		String bairro = (jsonObject.get("bairro").getAsString());
		String cep = (jsonObject.get("cep").getAsString());

		JsonObject jsnObjRegiao = jsonObject.get("regiao").getAsJsonObject();
		Regiao regiao = new Regiao(jsnObjRegiao.get("regiao").getAsString());

		return new Endereco(endereco, rua, numero, bairro, cep, regiao);
	}

	private Ranking processarRanking(JsonObject jsonObject) {
		long id = jsonObject.get("id").getAsLong();
		long pontos = jsonObject.get("pontos").getAsLong();
		float mediaAvaliacoes = jsonObject.get("mediaAvaliacoes").getAsFloat();
		Categoria categoria = new Categoria(jsonObject.get("categoria").getAsString());

		return new Ranking(id, pontos, categoria, mediaAvaliacoes);
	}
}
