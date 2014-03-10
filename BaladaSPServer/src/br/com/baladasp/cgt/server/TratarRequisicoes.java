package br.com.baladasp.cgt.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Calendar;

import br.com.baladasp.cgt.usuario.TratarUsuario;
import br.com.baladasp.util.Console;

import com.google.gson.Gson;

public class TratarRequisicoes implements Runnable {

	private Socket clientSocket;
	private String clientID;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public TratarRequisicoes(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.clientSocket.setSoTimeout(10000);
			this.clientID = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * jsonString[0] = Tipo de objeto jsonString[1] = Tipo de operacao jsonString[2] = Argumento
	 */
	@Override
	public void run() {
		Object obj = null;
		boolean connected = true;
		try {
			notify("CLIENTE CONECTADO");
			while (connected) {
				obj = in.readObject();
				if (obj.getClass().equals(String[].class)) {
					String[] jsonStringRecebida = (String[]) obj;
					final Gson gson = new Gson();
					final String tipoObjetoRecebido = gson.fromJson(jsonStringRecebida[0], String.class);

					if (tipoObjetoRecebido.equalsIgnoreCase("usuario")) {
						TratarUsuario tratarCliente = new TratarUsuario();
						Object objt = tratarCliente.operacoes(jsonStringRecebida);

						resposta(objt);
					}
					if (tipoObjetoRecebido.equalsIgnoreCase("estabelecimento")) {
						TratarEstabelecimento tratarEstabelecimento = new TratarEstabelecimento();
						Object objt = tratarEstabelecimento.operacoes(jsonStringRecebida);

						resposta(objt);
					}
				}

				int i = in.read();
				if (i == -1) {
					connected = false;
					try {
						if (clientSocket != null) {
							clientSocket.close();
							notify("CLIENTE DESCONECTADO");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void resposta(Object sendObject) throws IOException {
		out.writeObject(sendObject);
	}

	private String getStandardLogHeader() {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return "[" + dateFormat.format(Calendar.getInstance().getTime()) + "] " + clientID + " : ";
	}

	private void notify(String msg) {
		Console.println(getStandardLogHeader() + msg);
	}

}