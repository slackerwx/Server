package br.com.baladasp.cgt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP implements Runnable {

	private int port;
	private ServerSocket socket;
	private TratarRequisicoes requisicao;

	public ServerTCP(int port) {
		this.port = port;
		init();
	}

	private void init() {
		try {
			socket = new ServerSocket(port);
			System.out.println("Servidor aguardando conexao na porta: " + port);
		} catch (IOException e) {
			System.out.println("Erro ao criar o socket na porta: " + port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Socket clientSocket = null;

		boolean connected = true;
		while (connected) {
			if (socket != null && socket.isBound()) {
				try {
					clientSocket = socket.accept();
					if (clientSocket != null) {
						requisicao = new TratarRequisicoes(clientSocket);
						Thread thread = new Thread(requisicao);
						thread.start();
					}
				} catch (IOException e) {
					e.printStackTrace();
					close();
					connected = false;
				}
			}
		}
	}

	public void close() {
		try {
			System.out.println("Fechando servidor...");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
