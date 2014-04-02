package br.com.baladasp.cgt.server.test;

public interface Tratamento {
	String criarJson(Object object);
	Object operacao(String[] sendJson);
}
