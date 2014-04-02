package br.com.baladasp.cgt.service.usuario;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import br.com.baladasp.cgt.util.json.serializer.AtividadesUsuarioSerializer;
import br.com.baladasp.cgt.util.json.serializer.EstabelecimentosSerializer;
import br.com.baladasp.cgt.util.json.serializer.StatusUsuariosSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Objeto
 */
public class TratarUsuarioImpl implements TratarUsuario {

	private Gson gsonEstabelecimentosSerializer = new GsonBuilder()
			.registerTypeAdapter(ArrayList.class, new EstabelecimentosSerializer())
			.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	private static Gson gsonStatusUsuarioSerializer = new GsonBuilder()
			.registerTypeAdapter(ArrayList.class, new StatusUsuariosSerializer())
			.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	private static Gson gsonAtividadesUsuarioSerializer = new GsonBuilder()
			.registerTypeAdapter(ArrayList.class, new AtividadesUsuarioSerializer())
			.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	private Gson gsonUtils = new Gson();

	private ApplicationContext app;

	@Override
	public String operacoes(String[] receivedJsonString) {
		final UsuarioServiceEnum operacao = (UsuarioServiceEnum) gsonEstabelecimentosSerializer.fromJson(
				receivedJsonString[1], UsuarioServiceEnum.class);

		app = new ClassPathXmlApplicationContext("resource/applicationContext.xml");

		System.out.println(operacao);

		switch (operacao) {
		case VERIFICAR_CHECKIN_AVALIACAO:
			return TratarConsultaAvaliacaoCheckin.verificarSeUsuarioJaAvaliouEstabelecimento(
					gsonEstabelecimentosSerializer, receivedJsonString);
		case CHECKIN_ESTABELECIMENTO:
			return tratarCheckin(receivedJsonString);
		case AVALIAR_ESTABELECIMENTO:
			return tratarAvaliacao(receivedJsonString);
		case PERFIL_USUARIO:
			return tratarPerfil(receivedJsonString);
		case TIMELINE_TWITTER:
			return tratarListStatus(receivedJsonString);
		case ATIVIDADES_USUARIO:
			return tratarAtividadesUsuario(receivedJsonString);
		default:
			break;
		}

		return null;
	}

	@Override
	public String tratarListStatus(String[] receivedJsonString) {
		int pageNum = gsonUtils.fromJson(receivedJsonString[2], Integer.class);

		StatusUsuariosBO statusBO = (StatusUsuariosBO) app.getBean("statusUsuariosBO");

		System.out.println("PAGE " + pageNum);

		ArrayList<StatusUsuario> listStatus = statusBO.consultaStatusTimeline(pageNum);

		return gsonStatusUsuarioSerializer.toJson(listStatus);
	}

	@Override
	public String tratarPerfil(String[] receivedJsonString) {
		Usuario usuario = gsonStatusUsuarioSerializer.fromJson(receivedJsonString[2], Usuario.class);

		System.out.println("Perfil usuario antes: " + usuario);

		UsuarioBO usuarioBO = new UsuarioBO();
		Usuario user = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());

		if (user == null) {
			usuarioBO.inserirUsuario(usuario);
			usuario = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());
		} else {
			usuario = user;
		}

		System.out.println("Perfil usuario: " + usuario);

		return usuario == null ? null : gsonStatusUsuarioSerializer.toJson(usuario);
	}

	@Override
	public String tratarCheckin(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[2],
				AtividadeUsuario.class);

		System.out.println("tratarCheckin: " + atividade);
		AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
		atividadesUsuarioBO.salvarAtividade(atividade);

		return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_OK_CHECKIN);
	}

	@Override
	public String tratarAvaliacao(String[] receivedJsonString) {
		AtividadeUsuario atividade = gsonEstabelecimentosSerializer.fromJson(receivedJsonString[2],
				AtividadeUsuario.class);

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

				estabelecimento.setMediaAvaliacoes(novaMediaRanking);
				ranking.setEstabelecimento(estabelecimento);
			} else {
				estabelecimento.setMediaAvaliacoes(avaliacao.getMediaAvaliacao());
				ranking = new Ranking(estabelecimento, avaliacao.getMediaAvaliacao(), avaliacao.getTotaldePontos());
			}
			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();

			// Usar AspectJ pra esse tipo de coisa.
			System.out.println(atividade);
			atividadesUsuarioBO.salvarAtividade(atividade);

			rankingBO.salvarRanking(ranking);

			return gsonStatusUsuarioSerializer.toJson(ConstantesUsuario.MSG_OK_AVALIADO);
		}

		return null;
	}

	@Override
	public String tratarAtividadesUsuario(String[] receivedJsonString) {
		Usuario usuario = gsonStatusUsuarioSerializer.fromJson(receivedJsonString[2], Usuario.class);
		int pageNum = gsonUtils.fromJson(receivedJsonString[3], Integer.class);

		AtividadesUsuarioBO atividadesUsuarioBO = (AtividadesUsuarioBO) app.getBean("atividadesUsuarioBO");

		System.out.println("PAGE " + pageNum);

		ArrayList<AtividadeUsuario> atividadesUsuario = atividadesUsuarioBO.consultarAtividadesPaginacao(usuario,
				pageNum);

		return gsonAtividadesUsuarioSerializer.toJson(atividadesUsuario);
	}

	private float calculaMediaRanking(Avaliacao avaliacao, Ranking ranking) {
		final Estabelecimento estabelecimento = ranking.getEstabelecimento();
		final float mediaRankingAtual = ranking.getMediaAvaliacoes();

		final float mediaAvaliacao = avaliacao.getMediaAvaliacao();
		float novaMediaRanking = mediaRankingAtual;
		if (mediaAvaliacao != mediaRankingAtual) {
			novaMediaRanking = ((mediaAvaliacao - mediaRankingAtual) / (estabelecimento.getQtdAvaliacoes() + 1))
					+ mediaRankingAtual;
		}
		return novaMediaRanking;
	}

	private static class TratarConsultaAvaliacaoCheckin {

		private static String verificarSeUsuarioJaAvaliouEstabelecimento(Gson gsonUtils, String[] receivedJsonString) {
			final ConsultaCheckinAvaliacao consulta = gsonUtils.fromJson(receivedJsonString[2],
					ConsultaCheckinAvaliacao.class);

			final String nomeEstabelecimentoConsulta = consulta.getNomeEstabelecimento();
			final Usuario usuario = consulta.getUsuarioTwitter();

			if (usuario != null) {
				AtividadesUsuarioBO ativUserBO = new AtividadesUsuarioBO();
				ArrayList<AtividadeUsuario> dezUltimasAtividadesUsuario = ativUserBO
						.consultarDezUltimasAtividadesUsuario(usuario);

				for (AtividadeUsuario ultimaAtividade : dezUltimasAtividadesUsuario) {
					String estabelecimentoAvaliado = ((OperacaoAtividadeUsuario) ultimaAtividade.getTipoAtividade())
							.getEstabelecimento().getNome();

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
			int mesAvaliado = dataAtividade.get(Calendar.MONTH) + 1;
			int anoAvaliado = dataAtividade.get(Calendar.YEAR);

			Calendar dataAtual = Calendar.getInstance();
			int horaAtual = dataAtual.get(Calendar.HOUR_OF_DAY);
			int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);
			int mesAtual = dataAtual.get(Calendar.MONTH) + 1;
			int anoAtual = dataAtual.get(Calendar.YEAR);

			int difHora = horaAvaliado - horaAtual;
			int difDia = diaAvaliado - diaAtual;
			int difMes = mesAvaliado - mesAtual;
			int difAno = anoAvaliado - anoAtual;

			// DIA: -3 MES: 0 ANO: 0 HORA: -6
			System.out.println("DIA: " + difDia + " MES: " + difMes + " ANO: " + difAno + " HORA: " + difHora);

			/*
			 * Se nao tiver pelo menos 12 horas de diferen�a
			 */
			if ((difDia == 0 && difMes == 0 && difAno == 0) || (difDia == 0 && (Math.abs(difHora) < 12))
					|| (difDia == 1 && difHora > 11)) {
				return true;
			}

			return false;
		}
	}

}
