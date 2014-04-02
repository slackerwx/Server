package br.com.baladasp.bootstrap;

public class IniciarStatusListener {
	public static void main(String[] args){
		TwitterStatusListener twitterStatusListener = new TwitterStatusListener();
		twitterStatusListener.listenerStatus();
	}
}
