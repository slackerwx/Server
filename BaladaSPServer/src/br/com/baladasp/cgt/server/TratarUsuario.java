package br.com.baladasp.cgt.server;

import java.util.ArrayList;
import java.util.Date;

import br.com.baladasp.cdp.constantes.ConstantesUsuario;
import br.com.baladasp.cdp.estabelecimento.Avaliacao;
import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.AtividadesUsuario;
import br.com.baladasp.cdp.usuario.Checkin;
import br.com.baladasp.cdp.usuario.ConsultaAtividadeUsuario;
import br.com.baladasp.cdp.usuario.StatusUsuarios;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.AtividadesUsuarioBO;
import br.com.baladasp.cgt.bo.AvaliacaoBO;
import br.com.baladasp.cgt.bo.CheckinBO;
import br.com.baladasp.cgt.bo.RankingBO;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.cgt.util.AvaliacaoSerializer;
import br.com.baladasp.cgt.util.StatusUsuarioSerializer;
import br.com.baladasp.cgt.util.StatusUsuariosSerializer;
import br.com.baladasp.util.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Objeto
 */
public class TratarUsuario {

	private Gson gsonSer = new GsonBuilder().registerTypeAdapter(Avaliacao.class, new AvaliacaoSerializer())
			.registerTypeAdapter(StatusUsuarios.class, new StatusUsuarioSerializer())
			.registerTypeAdapter(ArrayList.class, new StatusUsuariosSerializer()).setPrettyPrinting().create();
	private Gson gsonUtils = new Gson();

	public Object operacoes(String[] receivedJsonString) {
		final String operacao = gsonUtils.fromJson(receivedJsonString[1], String.class);

		if (operacao.equalsIgnoreCase(ConstantesUsuario.VERIFICACAO)) {
			System.out.println(ConstantesUsuario.VERIFICACAO);
			return tratarConsulta(receivedJsonString);
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

		ArrayList<StatusUsuarios> listStatus = statusBO.consultaStatusTimeline();

		return gsonSer.toJson(listStatus);
	}

	private Object tratarPerfil(String[] receivedJsonString) {
		final Usuario usuario = gsonUtils.fromJson(receivedJsonString[2], Usuario.class);
		Usuario user = consultarUsuario(usuario);

		System.out.println(user);
		return user == null ? null : gsonSer.toJson(user);
	}

	private String tratarConsulta(String[] receivedJsonString) {
		
		ConsultaAtividadeUsuario consulta = gsonUtils.fromJson(receivedJsonString[2], ConsultaAtividadeUsuario.class);
		
		String nomeEstabelecimentoConsulta = consulta.getNomeEstabelecimento();
		
		Usuario usuarioTwitter = consultarUsuario(consulta.getUsuarioTwitter());
		
		String nomeEstabelecimentoAtividade = " ";

		if (usuarioTwitter != null) {
			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();
			
			ArrayList<AtividadesUsuario> dezUltimasAtividadesUsuario = atividadesUsuarioBO.consultarDezUltimasAtividadesUsuario(usuarioTwitter);
			
			
			for (AtividadesUsuario atividade : dezUltimasAtividadesUsuario) {

				String tipoAtividade = atividade.getTipoAtividade();
				String dataAtividade = atividade.getDataAtividade();
				
				System.out.println("Tipo atividade "  + tipoAtividade);
				/*
				 * verificacao nao esta funcionando
				 */
				
				if (tipoAtividade.equalsIgnoreCase(ConstantesUsuario.CHECKIN)) {
					Checkin checkin = atividade.getCheckin();
					nomeEstabelecimentoAtividade = checkin.getEstabelecimento().getNome();
				}
				if (tipoAtividade.equalsIgnoreCase(ConstantesUsuario.AVALIACAO)) {
					Avaliacao av = atividade.getAvaliacao();
					nomeEstabelecimentoAtividade = av.getEstabelecimento().getNome();
				}

				System.out.println(nomeEstabelecimentoAtividade + " " + dataAtividade + " " + nomeEstabelecimentoConsulta);
				
				if (nomeEstabelecimentoAtividade.equals(nomeEstabelecimentoConsulta)) {
					/*
					 * Formato de entrada de data 31/12/13 13:39
					 */
					String[] infoAvaliado = dataAtividade.split(" ");
					String[] dataAvaliado = infoAvaliado[0].split("/");
					String[] horarioAvaliado = infoAvaliado[1].split(":");

					String[] infoAtual = Utils.formatarData(new Date()).split(" ");
					String[] dataAtual = infoAtual[0].split("/");
					String[] horarioAtual = infoAtual[1].split(":");

					int diaAvaliado = Integer.valueOf(dataAvaliado[0]);
					int diaAtual = Integer.valueOf(dataAtual[0]);
					int difDia = diaAvaliado - diaAtual;

					int mesAvaliado = Integer.valueOf(dataAvaliado[1]);
					int mesAtual = Integer.valueOf(dataAtual[1]);
					int difMes = mesAvaliado - mesAtual;

					int anoAvaliado = Integer.valueOf(dataAvaliado[2]);
					int anoAtual = Integer.valueOf(dataAtual[2]);
					int difAno = anoAvaliado - anoAtual;

					int horaAvaliado = Integer.valueOf(horarioAvaliado[0]);
					int horaAtual = Integer.valueOf(horarioAtual[0]);
					int difHora = horaAvaliado - horaAtual;

					System.out.println("DIA: " + difDia + " MES: " + difMes + " ANO: " + difAno + " HORA: " + difHora);

					/*
					 * DIA: -3 MES: 0 ANO: 0 HORA: -6
					 */
					if ((difDia == 0 && difMes == 0 && difAno == 0) || (difDia == 0 && (difHora < 12 || difHora < -12))
							|| (difDia == 1 && difHora > 11)) {
						System.out.println(ConstantesUsuario.MSG_ERRO_CHECKIN);
						return gsonSer.toJson(ConstantesUsuario.MSG_ERRO_CHECKIN);
					}
				}
			}
			System.out.println(ConstantesUsuario.MSG_NAO_FEZ_CHECKIN);
			return gsonSer.toJson(ConstantesUsuario.MSG_NAO_FEZ_CHECKIN);
		}
		return null;
	}

	private String tratarCheckin(String[] receivedJsonString) {
		Checkin checkin = gsonUtils.fromJson(receivedJsonString[2], Checkin.class);
		Estabelecimento estabelecimento = checkin.getEstabelecimento();
		Categoria categoria = estabelecimento.getCategoria();
		Usuario usuarioTwitter = consultarUsuario(checkin.getUsuario());

		if (usuarioTwitter != null) {
			UsuarioBO usuarioBO = new UsuarioBO();
			CheckinBO checkinBO = new CheckinBO();
			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();

			usuarioTwitter.checkinUsuario(categoria);
			checkin.setUsuario(usuarioTwitter);
			usuarioTwitter.checkinUsuario(categoria);
			usuarioBO.atualizarUsuario(usuarioTwitter);

			AtividadesUsuario atividade = new AtividadesUsuario();
			atividade.setUsuario(usuarioTwitter);
			atividade.setCheckin(checkin);

			checkinBO.salvarCheckin(checkin);
			atividadesUsuarioBO.salvarAtividade(atividade);

			return gsonSer.toJson(ConstantesUsuario.MSG_OK_CHECKIN);
		}
		return null;
	}

	private Object tratarAvaliacao(String[] receivedJsonString) {

		Avaliacao avaliacao = gsonUtils.fromJson(receivedJsonString[2], Avaliacao.class);

		int total = mediaAvaliacao(avaliacao.getAmbiente(), avaliacao.getAtendimento(), avaliacao.getAtracao(),
				avaliacao.getCardapio(), avaliacao.getEstacionamento(), avaliacao.getGeral(), avaliacao.getLocalizacao(),
				avaliacao.getPreco());
		avaliacao.setMedia(total);

		Usuario usuarioTwitter = consultarUsuario(avaliacao.getUsuario());
		Estabelecimento estabelecimento = avaliacao.getEstabelecimento();
		Categoria categoria = estabelecimento.getCategoria();

		Ranking ranking = new Ranking(estabelecimento, categoria, total);

		if (usuarioTwitter != null) {
			UsuarioBO usuarioBO = new UsuarioBO();
			RankingBO rankingBO = new RankingBO();
			AvaliacaoBO avaliacaoBO = new AvaliacaoBO();
			AtividadesUsuarioBO atividadesUsuarioBO = new AtividadesUsuarioBO();

			usuarioTwitter.checkinUsuario(categoria);
			usuarioBO.atualizarUsuario(usuarioTwitter);
			avaliacao.setUsuario(usuarioTwitter);
			avaliacaoBO.salvarAvaliacao(avaliacao);

			ArrayList<Avaliacao> listAvaliacao = avaliacaoBO.consultarAvaliacoesEstabelecimento(estabelecimento);
			if (listAvaliacao != null) {
				float t = 0f;
				for (Avaliacao av : listAvaliacao) {
					t += av.getMedia();
				}
				float mediaRanking = t / listAvaliacao.size();
				ranking.setMediaAvaliacoes(mediaRanking);

				System.out.println(mediaRanking);
			}
			rankingBO.salvarRanking(ranking);

			AtividadesUsuario atividade = new AtividadesUsuario();
			atividade.setAvaliacao(avaliacao);
			atividade.setUsuario(usuarioTwitter);

			atividadesUsuarioBO.salvarAtividade(atividade);

			System.out.println(usuarioTwitter);
			return gsonSer.toJson(ConstantesUsuario.MSG_OK_AVALIADO);
		}

		return null;
	}

	private int mediaAvaliacao(int ambiente, int atendimento, int atracao, int cardapio, int estacionamento, int geral,
			int localizacao, int preco) {
		return (ambiente + atendimento + atracao + cardapio + estacionamento + geral + localizacao + preco) / 8;
	}

	private Usuario consultarUsuario(Usuario usuario) {
		UsuarioBO usuarioBO = new UsuarioBO();
		Usuario user = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());

		if (user == null) {
			usuarioBO.inserirUsuario(usuario);
			user = usuarioBO.consultarUsuarioIDTwitter(usuario.getIDTwitter());
		}

		return user;
	}
}