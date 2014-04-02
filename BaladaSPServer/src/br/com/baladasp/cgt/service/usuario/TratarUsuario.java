package br.com.baladasp.cgt.service.usuario;

public interface TratarUsuario {
	String operacoes(String[] receivedJsonString);
	String tratarListStatus(String[] receivedJsonString);
	String tratarPerfil(String[] receivedJsonString);
	String tratarCheckin(String[] receivedJsonString);
	String tratarAvaliacao(String[] receivedJsonString);
	String tratarAtividadesUsuario(String[] receivedJsonString);
}