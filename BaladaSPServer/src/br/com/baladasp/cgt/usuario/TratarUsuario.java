package br.com.baladasp.cgt.usuario;

import java.util.ArrayList;
import java.util.Date;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.ConstantesUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.AtividadesUsuarioBO;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.cgt.server.OperacoesEstabelecimento;
import br.com.baladasp.cgt.util.json.deserializer.AtividadeUsuarioDeserializer;
import br.com.baladasp.cgt.util.json.deserializer.EstabelecimentoDeserializer;
import br.com.baladasp.cgt.util.json.serializer.AvaliacaoSerializer;
import br.com.baladasp.cgt.util.json.serializer.EstabelecimentosSerializer;
import br.com.baladasp.util.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import br.com.baladasp.cgt.util.UsuarioSerializer;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Objeto
 */
public class TratarUsuario extends OperacoesEstabelecimento {

	private Gson gsonUtils = new GsonBuilder().registerTypeAdapter(Avaliacao.class, new AvaliacaoSerializer())
			.registerTypeAdapter(ArrayList.class, new EstabelecimentosSerializer())
			.registerTypeAdapter(AtividadeUsuario.class, new AtividadeUsuarioDeserializer())
			.registerTypeAdapter(Estabelecimento.class, new EstabelecimentoDeserializer()).setPrettyPrinting().create();

	public Object operacoes(String[] receivedJsonString) {
		final String operacao = gsonUtils.fromJson(receivedJsonString[1], String.class);

		if (operacao.equalsIgnoreCase(ConstantesUsuario.VERIFICACAO)) {
			System.out.println(ConstantesUsuario.VERIFICACAO);
			return TratarConsultaAvaliacaoCheckin.verificarSeUsuarioJaAvaliouEstabelecimento(gsonUtils, receivedJsonString);
		}

		if (operacao.equalsIgnoreCase(ConstantesUsuario.CHECKIN)) {
			System.out.println(ConstantesUsuario.CHECKIN);
			return tratarCheckin(receivedJsonString);
		}

		if (operacao.equalsIgnoreCase(ConstantesUsuario.AVALIACAO)) {
			System.out.println(ConstantesUsuario.AVALIACAO);
			return tratarAvaliacao(receivedJsonString);
		}

		if (operacao.equalsIgnoreCase(ConstantesUsuario.PERFIL)) {
			System.out.println(ConstantesUsuario.PERFIL);
			return tratarPerfil(receivedJsonString);
		}

		if (operacao.equalsIgnoreCase(ConstantesUsuario.LIST_STATUS)) {
			System.out.println(ConstantesUsuario.LIST_STATUS);
			return tratarListStatus(receivedJsonString);
		}
		return null;
	}

	private Object tratarListStatus(String[] receivedJsonString) {
		StatusUsuariosBO statusBO = new StatusUsuariosBO();

		ArrayList<StatusUsuario> listStatus = statusBO.consultaStatusTimeline();

		return gsonSer.toJson(listStatus);
	}

	private Object tratarPerfil(String[] receivedJsonString) {
		Usuario usuario = gsonUtils.fromJson(receivedJsonString[2], Usuario.class);

		UsuarioBO usuarioBO = new UsuarioBO();
		Usuario user = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());

		if (user == null) {
			usuarioBO.inserirUsuario(usuario);
			usuario = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());
		} else {
			usuario = user;
		}

		return usuario == null ? null : gsonSer.toJson(usuario);
	}

	private String tratarCheckin(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonUtils.fromJson(receivedJsonString[2], AtividadeUsuario.class);

		AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
		atividadesUsuarioBO.salvarAtividade(atividade);

		return gsonSer.toJson(ConstantesUsuario.MSG_OK_CHECKIN);
	}

	private Object tratarAvaliacao(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonUtils.fromJson(receivedJsonString[2], AtividadeUsuario.class);

		final OperacaoAtividadeUsuario operacao = atividade.getTipoAtividade();
		final Estabelecimento estabelecimento = operacao.getEstabelecimento();

		if (operacao instanceof Avaliacao) {
			Avaliacao avaliacao = (Avaliacao) operacao;
			Ranking ranking = estabelecimento.getRanking();
			estabelecimento.aumentarQtdAvaliacoes();
			if (ranking != null) {
				final float novaMediaRanking = calculaMediaRanking(estabelecimento, avaliacao, ranking);
				ranking.setMediaAvaliacoes(novaMediaRanking);

				final float pontuacaoRankingAtual = estabelecimento.getRanking().getPontos();
				final float novaPontuacao = pontuacaoRankingAtual + avaliacao.getTotaldePontos();
				ranking.setPontos(novaPontuacao);

				// TODO fiz isso em EstabelecimentoDeserializer mas nao sei se foi a melhor escolha
				// ranking.setEstabelecimento(estabelecimento);
			} else {
				ranking = new Ranking(estabelecimento, avaliacao.getMediaAvaliacao(), avaliacao.getTotaldePontos());
				estabelecimento.setRanking(ranking);
			}

			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
			atividadesUsuarioBO.salvarAtividade(atividade);

			return gsonSer.toJson(ConstantesUsuario.MSG_OK_AVALIADO);
		}

		return null;
	}

	private float calculaMediaRanking(final Estabelecimento estabelecimento, Avaliacao avaliacao, Ranking ranking) {
		final float mediaRankingAtual = estabelecimento.getRanking().getMediaAvaliacoes();

		final float mediaAvaliacao = avaliacao.getMediaAvaliacao();
		float novaMediaRanking = mediaRankingAtual;
		if (mediaAvaliacao != mediaRankingAtual) {
			novaMediaRanking = ((mediaAvaliacao - mediaRankingAtual) / (estabelecimento.getQtdAvaliacoes() + 1)) + mediaRankingAtual;
		}
		return novaMediaRanking;
	}

	private static class TratarConsultaAvaliacaoCheckin {

		private static String verificarSeUsuarioJaAvaliouEstabelecimento(Gson gsonUtils, String[] receivedJsonString) {
			final ConsultaCheckinAvaliacao consulta = gsonUtils.fromJson(receivedJsonString[2], ConsultaCheckinAvaliacao.class);

			final String nomeEstabelecimentoConsulta = consulta.getNomeEstabelecimento();
			final Usuario usuario = consulta.getUsuarioTwitter();

			if (usuario != null) {
				AtividadesUsuarioBO ativUserBO = new AtividadesUsuarioBO();
				ArrayList<AtividadeUsuario> dezUltimasAtividadesUsuario = ativUserBO.consultarDezUltimasAtividadesUsuario(usuario);

				for (AtividadeUsuario ultimaAtividade : dezUltimasAtividadesUsuario) {
					String estabelecimentoAvaliado = ((OperacaoAtividadeUsuario) ultimaAtividade.getTipoAtividade()).getEstabelecimento().getNome();

					if (estabelecimentoAvaliado.equals(nomeEstabelecimentoConsulta)) {
						if (foiAvaliadoHoje(ultimaAtividade))
							return gsonSer.toJson(ConstantesUsuario.MSG_JA_AVALIADO);
					}
				}
				return gsonSer.toJson(ConstantesUsuario.MSG_NAO_AVALIADO);
			}
			return null;
		}

		/*
		 * Formato de entrada de data esperado 31/12/13 13:39
		 */
		private static boolean foiAvaliadoHoje(AtividadeUsuario atividade) {
			String dataAtividade = atividade.getDataAtividade();

			String[] infoAvaliado = dataAtividade.split(" ");
			String[] dataAvaliado = infoAvaliado[0].split("/");
			String[] horarioAvaliado = infoAvaliado[1].split(":");

			String[] infoAtual = Utils.formatarData(new Date()).split(" ");
			String[] dataAtual = infoAtual[0].split("/");
			String[] horarioAtual = infoAtual[1].split(":");

			int horaAvaliado = Integer.valueOf(horarioAvaliado[0]);
			int diaAvaliado = Integer.valueOf(dataAvaliado[0]);
			int mesAvaliado = Integer.valueOf(dataAvaliado[1]);
			int anoAvaliado = Integer.valueOf(dataAvaliado[2]);

			int horaAtual = Integer.valueOf(horarioAtual[0]);
			int diaAtual = Integer.valueOf(dataAtual[0]);
			int mesAtual = Integer.valueOf(dataAtual[1]);
			int anoAtual = Integer.valueOf(dataAtual[2]);

			int difHora = horaAvaliado - horaAtual;
			int difDia = diaAvaliado - diaAtual;
			int difMes = mesAvaliado - mesAtual;
			int difAno = anoAvaliado - anoAtual;

			// DIA: -3 MES: 0 ANO: 0 HORA: -6
			System.out.println("DIA: " + difDia + " MES: " + difMes + " ANO: " + difAno + " HORA: " + difHora);

			/*
			 * Se nao tiver pelo menos 12 horas de diferença
			 */
			if ((difDia == 0 && difMes == 0 && difAno == 0) || (difDia == 0 && (Math.abs(difHora) < 12)) || (difDia == 1 && difHora > 11)) {
				return true;
			}

			return false;
		}
	}
}
