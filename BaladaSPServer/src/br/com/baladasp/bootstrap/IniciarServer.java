package br.com.baladasp.bootstrap;

import br.com.baladasp.cgt.server.ServerTCP;

public class IniciarServer {
	public static void main(String[] args) {
		ServerTCP server = new ServerTCP(9009);
		Thread thread = new Thread(server);
		thread.start();
	}
}
