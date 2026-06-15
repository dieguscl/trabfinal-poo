package trabfinal;

/**
 * Ponto de entrada da aplicação de gestão de contactos.
 * Cria os objetos principais e inicia o menu.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class TrabFinal {

    // Nome do ficheiro usado para guardar e recuperar a agenda entre execuções.
    private static final String FICHEIRO_DADOS = "contactos.txt";

    // Inicia a aplicação: cria o gestor, cria o menu e entrega o controlo ao menu.
    // O programa fica a correr até o utilizador escolher sair.
    public static void main(String[] args) {
        // O gestor guarda os dados em memória; o menu trata da interação com o utilizador.
        // O nome do ficheiro é passado ao menu para este carregar e guardar os contactos.
        GestorContactos gestor = new GestorContactos();
        Menu menu = new Menu(gestor, FICHEIRO_DADOS);
        menu.menuInicial();
    }
}
