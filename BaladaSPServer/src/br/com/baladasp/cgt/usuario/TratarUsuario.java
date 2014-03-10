package br.com.baladasp.cgt.usuario;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.AtividadeUsuario;
import br.com.baladasp.cdp.usuario.Avaliacao;
import br.com.baladasp.cdp.usuario.ConstantesUsuario;
import br.com.baladasp.cdp.usuario.ConsultaCheckinAvaliacao;
import br.com.baladasp.cdp.usuario.OperacaoAtividadeUsuario;
import br.com.baladasp.cdp.usuario.StatusUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.AtividadesUsuarioBO;
import br.com.baladasp.cgt.bo.RankingBO;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.cgt.util.json.serializer.EstabelecimentosSerializer;
import br.com.baladasp.cgt.util.json.serializer.StatusUsuariosSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import br.com.baladasp.cgt.util.UsuarioSerializer;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Objeto
 */
public class TratarUsuario{

	private Gson gsonEstabelecimentosSerializer = new GsonBuilder()
													.registerTypeAdapter(ArrayList.class, new EstabelecimentosSerializer())
													.excludeFieldsWithoutExposeAnnotation()
													.setPrettyPrinting()
													.create();
	
	private static Gson gsonStatusUsuarioSerializer = new GsonBuilder()
													.registerTypeAdapter(ArrayList.class, new StatusUsuariosSerializer())
													.excludeFieldsWithoutExposeAnnotation()
													.setPrettyPrinting()
													.create();

	public Object operacoes(String[] receivedJsonString) {
		final String operacao = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[1], String.class);

		if (operacao.equalsIgnoreCase(ConstantesUsuario.VERIFICACAO)) {
			System.out.println(ConstantesUsuario.VERIFICACAO);
			return TratarConsultaAvaliacaoCheckin.verificarSeUsuarioJaAvaliouEstabelecimento(gsonEstabelecimentosSerializer, receivedJsonString);
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

		return gsonStatusUsuarioSerializer.toJson(listStatus);
	}

	private Object tratarPerfil(String[] receivedJsonString) {
		Usuario usuario = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[2], Usuario.class);

		UsuarioBO usuarioBO = new UsuarioBO();
		Usuario user = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());

		if (user == null) {
			usuarioBO.inserirUsuario(usuario);
			usuario = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());
		} else {
			usuario = user;
		}

		return usuario == null ? null : gsonStatusUsuarioSerializer.toJson(usuario);
	}

	private String tratarCheckin(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[2], AtividadeUsuario.class);

		AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
		atividadesUsuarioBO.salvarAtividade(atividade);

		return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_OK_CHECKIN);
	}

	//TODO Urgente, preciso dar um jeito de cascatear alteracoes em Ranking
	private Object tratarAvaliacao(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[2], AtividadeUsuario.class);

		final OperacaoAtividadeUsuario operacao = atividade.getTipoAtividade();
		final Estabelecimento estabelecimento = operacao.getEstabelecimento();

		if (operacao instanceof Avaliacao) {
			Avaliacao avaliacao = (Avaliacao) operacao;
		
			RankingBO rankingBO = new RankingBO();
			Ranking ranking = rankingBO.consultarRanking(estabelecimento);
			
			estabelecimento.aumentarQtdAvaliacoes();
			if (ranking != null) {
				final float novaMediaRanking = calculaMediaRanking(avaliacao, ranking);
				ranking.setMediaAvaliacoes(novaMediaRanking);

				final float pontuacaoRankingAtual = ranking.getPontos();
				final float novaPontuacao = pontuacaoRankingAtual + avaliacao.getTotaldePontos();
				ranking.setPontos(novaPontuacao);

				 ranking.setEstabelecimento(estabelecimento);
			} else {
				ranking = new Ranking(estabelecimento, avaliacao.getMediaAvaliacao(), avaliacao.getTotaldePontos());
			}
			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
			atividadesUsuarioBO.salvarAtividade(atividade);

			return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_OK_AVALIADO);
		}

		return null;
	}

	private float calculaMediaRanking(Avaliacao avaliacao, Ranking ranking) {
		final Estabelecimento estabelecimento = ranking.getEstabelecimento();
		final float mediaRankingAtual = ranking.getMediaAvaliacoes();

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
							return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_JA_AVALIADO);
					}
				}
				return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_NAO_AVALIADO);
			}
			return null;
		}

		/*
		 * Formato de entrada de data esperado 31/12/13 13:39
		 */
		private static boolean foiAvaliadoHoje(AtividadeUsuario atividade) {
			
			Calendar dataAtividade = atividade.getDataAtividade();
			int horaAvaliado = dataAtividade.get(Calendar.HOUR_OF_DAY);
			int diaAvaliado = dataAtividade.get(Calendar.DAY_OF_MONTH);
			int mesAvaliado = dataAtividade.get(Calendar.MONTH)+1;
			int anoAvaliado = dataAtividade.get(Calendar.YEAR);
			
			Calendar dataAtual = Calendar.getInstance();
			int horaAtual = dataAtual.get(Calendar.HOUR_OF_DAY);
			int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);
			int mesAtual = dataAtual.get(Calendar.MONTH)+1;
			int anoAtual = dataAtual.get(Calendar.YEAR);

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
