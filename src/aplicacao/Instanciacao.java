package aplicacao;

import dominio.Artista;
/*
import servico.BandaServico;
import servico.ServicoFactory;*/

public class Instanciacao {

	public static void main(String[] args) {

		Artista a1 = new Artista(null, "Muse", "Inglaterra");
		Artista a2 = new Artista(null, "Slipknot", "EUA");
		Artista a3 = new Artista(null, "Daft Punk", "Franca");
		Artista a4 = new Artista(null, "ACDC", "EUA");
		Artista a5 = new Artista(null, "Sepultura", "Brasil");
		
		//BandaServico bs = ServicoFactory.criarBandaServico();
		
	/*	bs.inserirAtualizar(b1);
		bs.inserirAtualizar(b2);
		bs.inserirAtualizar(b3);
		bs.inserirAtualizar(b4);
		bs.inserirAtualizar(b5);*/
		
		System.out.println("Pronto!");
	}

}
