/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

import java.util.Scanner;

/**
 *
 * @author luis
 */
public class Menu {

	private final GestorContactos gestor;
	private int runNumber = 0;

	// load ficheiro
	// iniciar menu base
	public Menu(GestorContactos newGestor) {
		// gestor.load
		gestor = newGestor;
	}

	public void menuInicial() {
		if (runNumber != 0) {
			System.out.println("\n-----------------");
			runNumber++;
		}
		String s = "1 - Listar Contactos\n";
		s += "2 - Acrescentar Contacto\n";
		s += "3 - Remover Contacto\n";
		s += "4 - Encontrar Contactos\n";
		s += "5 - Estatísticas\n";
		s += "6 - Sair";
		System.out.println(s);

		try (Scanner scanner = new Scanner(System.in)) {
			switch (scanner.nextInt()) {
				case 1 ->
					mostrarListaContactos();
				case 2 ->
					acrescentarContacto();
				// case 3 -> "Mail";
				// case 4 -> "Mail";
				//case 5 -> "Mail";
				case 5 -> mostrarEstatisticas();
			}
		}

	}

	private void mostrarListaContactos() {

		String listaContactos = gestor.listarContactos();
		System.out.println("********************************\n*** Listar Contactos:");
		Scanner scanner = new Scanner(System.in);

		if (listaContactos.equals("")) {
			System.out.println("\nNão há contactos!\nPrima entrer para continuar!");

			scanner.nextLine();

		} else {
			System.out.println(gestor.listarContactos());
			System.out.println("Escrever em Ficheiro (S/N)?");

			switch (scanner.next("^[sSnN]$").toLowerCase()) {
				case "s" ->
					System.out.println("Importar");
				case "n" ->
					System.out.println("Não importar");
			}
		}

		menuInicial();

	}

	private void acrescentarContacto() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Indique o nome do contacto:");
		String name = scanner.nextLine();
		System.out.println("Indique o nome da companhia, se for um contacto profissional, ou então deixe vazio:");
		String empresa = scanner.nextLine();
		System.out.println("Indique os dados do contacto ou vazio para terminar:");
		String dados = scanner.nextLine();
		System.out.println("Indique o tipo do contacto:");
		System.out.println("1 - Telefone");
		System.out.println("2 - Telemovel");
		System.out.println("3 - Mail");

		String tipo = scanner.nextLine();

		Identificacao newId = new Identificacao(name, empresa);
		EntradaContacto newEntrada = new EntradaContacto(TipoContacto.fromInt(Integer.parseInt(tipo)), dados);
		Contacto newContacto = new Contacto(newId, newEntrada);
		gestor.acrescentarContacto(newContacto);

		menuInicial();
	}
	 
	//private void removerContacto() {
//	}

	private void mostrarEstatisticas() {
		System.out.println(gestor.estatisticas());

		menuInicial();
	}
}
