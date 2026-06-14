/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabfinal;

/**
 * Ponto de entrada da aplicacao de Gestao de Contactos.
 * O Menu trata de ler os contactos do ficheiro no arranque e de os gravar
 * antes de terminar, garantindo que cada utilizacao usa a informacao mais atual.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class TrabFinal {

    /** Nome do ficheiro onde a agenda e guardada entre execucoes. */
    private static final String FICHEIRO_DADOS = "contactos.txt";

    public static void main(String[] args) {
        GestorContactos gestor = new GestorContactos();
        Menu menu = new Menu(gestor, FICHEIRO_DADOS);
        menu.menuInicial();
    }
}
