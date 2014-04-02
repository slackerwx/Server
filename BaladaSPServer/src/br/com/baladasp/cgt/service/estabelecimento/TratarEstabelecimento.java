package br.com.baladasp.cgt.service.estabelecimento;

import java.io.UnsupportedEncodingException;

public interface TratarEstabelecimento {
	Object operacoes(String[] receivedJsonString) throws UnsupportedEncodingException;
	String buscaPorNome(String argumento, int pageNum);
	String buscarTopDez(String argumento);
}
