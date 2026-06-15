package trabfinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controla a interação com o utilizador através da consola.
 * Esta classe não guarda os dados diretamente: delega a gestão da agenda no GestorContactos
 * e delega a leitura/escrita de ficheiros no GestorFicheiros.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class Menu {

    // Gestor que mantém a lista de contactos em memória.
    private final GestorContactos gestor;

    // Scanner único para ler todas as opções e dados introduzidos pelo utilizador.
    private final Scanner scanner = new Scanner(System.in);

    // Nome do ficheiro de persistência usado no arranque e na saída do programa.
    private final String ficheiroDados;

    // Cria o menu principal e associa-o ao gestor e ao ficheiro de dados.
    // Também carrega os contactos guardados para preparar o arranque da aplicação.
    public Menu(GestorContactos newGestor, String ficheiroDados) {
        // Recebe o gestor já criado no main e associa o ficheiro de dados a este menu.
        gestor = newGestor;
        this.ficheiroDados = ficheiroDados;

        // O carregamento é feito aqui para o utilizador começar logo com a agenda guardada.
        carregarContactos();
    }

    // Carrega os contactos em ficheiro para a agenda em memória.
    // Cada contacto lido é entregue ao GestorContactos.
    private void carregarContactos() {
        // GestorFicheiros devolve uma lista; o menu passa cada contacto para o gestor principal.
        // Assim, a origem dos dados fica separada da classe que gere a coleção em memória.
        for (Contacto contacto : GestorFicheiros.carregar(ficheiroDados)) {
            gestor.acrescentarContacto(contacto);
        }
    }

    // Mostra o menu principal e executa a opção escolhida pelo utilizador.
    // O ciclo termina apenas quando é escolhida a opção de sair.
    public void menuInicial() {
        // O ciclo mantém o programa ativo até o utilizador escolher a opção 6.
        boolean emExecucao = true;

        while (emExecucao) {
            mostrarOpcoes();

            // Lê a opção como String para evitar problemas com quebras de linha do Scanner.
            switch (scanner.nextLine().trim()) {
                case "1" -> mostrarListaContactos();
                case "2" -> acrescentarContacto();
                case "3" -> removerContacto();
                case "4" -> encontrarContactos();
                case "5" -> mostrarEstatisticas();
                case "6" -> {
                    // Antes de sair, grava a lista atual para manter os dados entre execuções.
                    GestorFicheiros.guardar(gestor.getListaContactos(), ficheiroDados);
                    System.out.println("Contactos gravados. Até à próxima!");
                    emExecucao = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // Apresenta no ecrã as opções principais da aplicação.
    private void mostrarOpcoes() {
        // StringBuilder junta as linhas do menu antes de imprimir tudo de uma vez.
        StringBuilder sb = new StringBuilder();

        sb.append("\n-----------------\n");
        sb.append("1 - Listar Contactos\n");
        sb.append("2 - Acrescentar Contacto\n");
        sb.append("3 - Remover Contacto\n");
        sb.append("4 - Encontrar Contactos\n");
        sb.append("5 - Estatísticas\n");
        sb.append("6 - Sair");

        System.out.println(sb);
    }

    // Lista todos os contactos existentes na agenda.
    // Depois de mostrar a lista, permite exportar essa informação para ficheiro.
    private void mostrarListaContactos() {
        // Vai buscar uma cópia da lista ao gestor e apresenta cada contacto.
        System.out.println("********************************\n*** Listar Contactos:");
        ArrayList<Contacto> listaContactos = gestor.getListaContactos();
        if (listaContactos.isEmpty()) {
            System.out.println("\nNão há contactos!\nPrima enter para continuar!");
            scanner.nextLine();
        } else {
            // O mesmo texto é usado para mostrar no ecrã e para exportar para ficheiro.
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < listaContactos.size(); i++) {
                sb.append('\n')
                  .append(i + 1)
                  .append(" - ")
                  .append(listaContactos.get(i));
            }
            System.out.println(sb);
            perguntarEscreverFicheiro("********************************\n*** Listar Contactos:" + sb);
        }
    }

    // Recolhe os dados necessários para criar um novo contacto.
    // No fim, chama a verificação de duplicados antes de guardar na agenda.
    private void acrescentarContacto() {
        // Primeiro lê a identificação do contacto; depois lê uma ou mais entradas.
        System.out.println("********************************\n*** Acrescentar contacto:");
        System.out.println("Indique o nome do contacto:");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            // Nome vazio cancela a criação do contacto.
            return;
        }

        System.out.println("Indique o nome da empresa, se for um contacto profissional, ou então deixe vazio:");
        String empresa = scanner.nextLine().trim();

        // Cria a identificação antes das entradas, porque o Contacto precisa dela no construtor.
        Identificacao novaId = new Identificacao(nome, empresa);
        Contacto novoContacto = null;

        while (true) {
            System.out.println("Indique os dados do contacto ou vazio para terminar:");
            String dados = scanner.nextLine().trim();
            if (dados.isBlank()) {
                break;
            }

            // Para cada valor introduzido, pergunta também que tipo de contacto é.
            TipoContacto tipo = lerTipoContacto();
            EntradaContacto novaEntrada = new EntradaContacto(tipo, dados);

            // O objeto Contacto só é criado quando existe a primeira entrada válida.
            // As entradas seguintes são acrescentadas ao mesmo objeto.
            if (novoContacto == null) {
                novoContacto = new Contacto(novaId, novaEntrada);
            } else if (novoContacto.temInformacao(novaEntrada)) {
                // Evita repetir dentro do contacto que está a ser construído.
                System.out.println("Essa informação já foi introduzida neste contacto.");
            } else {
                novoContacto.addEntradaContacto(novaEntrada);
            }
        }

        if (novoContacto == null) {
            // Se o utilizador não introduziu nenhuma entrada, não há contacto para guardar.
            return;
        }

        // Só depois de construir o contacto é que verifica conflitos com a agenda existente.
        adicionarComVerificacao(novoContacto);
    }

    // Lê do utilizador o tipo de entrada de contacto.
    // Repete a pergunta até receber uma opção válida.
    private TipoContacto lerTipoContacto() {
        // Repete a pergunta até o utilizador indicar uma opção existente.
        while (true) {
            System.out.println("Indique o tipo do contacto:");
            System.out.println("1 - Telefone");
            System.out.println("2 - Telemóvel");
            System.out.println("3 - Mail");
            try {
                // Converte a opção numérica para o enum usado no resto do programa.
                TipoContacto tipo = TipoContacto.fromInt(Integer.parseInt(scanner.nextLine().trim()));
                if (tipo != null) {
                    return tipo;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Tipo inválido.");
        }
    }

    // Decide como acrescentar um contacto depois de verificar duplicados.
    // Pode juntar informação a um contacto existente, criar um novo ou cancelar a operação.
    private void adicionarComVerificacao(Contacto novo) {
        // Primeira regra: se a identificação já existe, o utilizador decide se junta a nova informação.
        Contacto existente = gestor.encontrarContactosRepetidos(novo);
        if (existente != null) {
            System.out.println("Já existe um contacto com a mesma identificação:");
            System.out.println(existente);
            System.out.println("1 - Acrescentar a nova informação ao contacto já existente");
            System.out.println("0 - Desistir de introduzir este contacto");

            if (scanner.nextLine().trim().equals("1")) {
                existente.acrescentarInformacao(novo);
                System.out.println("Informação acrescentada ao contacto já existente.");
            } else {
                System.out.println("Contacto não introduzido.");
            }
            return;
        }

        // Segunda regra: se a identificação é nova, ainda pode haver telefone/mail repetido noutro contacto.
        ArrayList<Contacto> comInfoRepetida = gestor.encontrarInformacaoRepetidaContactos(novo);
        if (!comInfoRepetida.isEmpty()) {
            System.out.println("A informação introduzida já existe nos seguintes contactos:");
            for (int i = 0; i < comInfoRepetida.size(); i++) {
                System.out.println(comInfoRepetida.get(i));
            }
            System.out.println("1 - Acrescentar a informação ao contacto já existente");
            System.out.println("2 - Acrescentar o novo contacto");
            System.out.println("0 - Desistir de introduzir este contacto");

            // O programa dá ao utilizador a escolha entre juntar informação ou criar novo contacto.
            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    comInfoRepetida.get(0).acrescentarInformacao(novo);
                    System.out.println("Informação acrescentada ao contacto já existente.");
                }
                case "2" -> {
                    gestor.acrescentarContacto(novo);
                    System.out.println("Novo contacto acrescentado.");
                }
                default ->
                    System.out.println("Contacto não introduzido.");
            }
            return;
        }

        // Se não houver conflitos, o contacto é acrescentado diretamente.
        gestor.acrescentarContacto(novo);
        System.out.println("Contacto acrescentado.");
    }

    // Apresenta as opções de remoção para um contacto já identificado.
    // Permite remover o contacto completo ou apenas uma entrada específica.
    private void removerContactoIndividual(int indiceEncontrado){
        // Recebe um índice já validado pela pesquisa e mostra o contacto completo antes de remover.
        Contacto contactoEncontrado = gestor.getContacto(indiceEncontrado);
        System.out.println("O contacto encontrado foi:");
        System.out.println((indiceEncontrado+1) + " - " + contactoEncontrado.toString() + "\n");
        System.out.println("1 - Remover o contacto completo");
        System.out.println("2 - Eliminar um tipo de contacto");
        System.out.println("3 - Voltar");
        System.out.println("Escolhar uma opção entre [1,3]:");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                // Remove o contacto inteiro da lista principal.
                gestor.removerContacto(indiceEncontrado);
                System.out.println("Contacto removido.");
            }
            case "2" -> {
                // Para remover apenas uma entrada, é necessário indicar o valor e o tipo.
                System.out.println("Indique o pretende remover:");
                System.out.println("Indique os dados do contacto ou vazio para terminar:");
                String valor = scanner.nextLine().trim();

                if(!valor.isEmpty()){
                    TipoContacto tipo = lerTipoContacto();
                    EntradaContacto entradaCheck = new EntradaContacto(tipo, valor);

                    // A remoção depende do equals de EntradaContacto, ou seja, tipo e valor têm de coincidir.
                    if(contactoEncontrado.removerEntradaContacto(entradaCheck)){
                        System.out.println("Entrada removida com sucesso.");
                    }
                    else{
                        System.out.println("Entrada não encontrada.");
                    }
                }
            }
            case "3" -> {
            }
            default ->
                System.out.println("Opção inválida.");
        }
    }

    // Pesquisa contactos a partir de texto introduzido e conduz o processo de remoção.
    // Se houver vários resultados, pede ao utilizador que escolha qual pretende remover.
    private void removerContacto() {
        // A remoção começa por uma pesquisa por contactos e entradas.
        System.out.println("********************************\n*** Remover contacto:\nIndique qual a informação a pesquisar nos contactos:");
        String informacao = scanner.nextLine().trim();
        if(informacao.isBlank()){
            System.out.println("Informação não encontrada nos contactos");
            return;
        }

        ArrayList<Integer> contactosEncontrados = gestor.encontrarInformacao(informacao);
        if (contactosEncontrados.isEmpty()) {
            System.out.println("Informação não encontrada nos contactos");
            return;
        }

        if(contactosEncontrados.size() == 1){
            // Com um único resultado, avança diretamente para o submenu de remoção.
            int indiceEncontrado = contactosEncontrados.get(0);

            removerContactoIndividual(indiceEncontrado);
        }
        else{
            // Com vários resultados, mostra uma lista curta para o utilizador escolher um índice.
            System.out.println("Contactos com essa informação:");
            StringBuilder sb = new StringBuilder();
            for (int indice : contactosEncontrados){
                Contacto contacto = gestor.getContacto(indice);

                // Mostra o número real do contacto na agenda e o nome para facilitar a seleção.
                sb.append(indice + 1)
                  .append(" - ")
                  .append(contacto.getIdentificacao().getNome())
                  .append('\n');

                // Mostra apenas as entradas que coincidem com a pesquisa, para facilitar leitura da listagem.
                for (EntradaContacto entrada : contacto.getEntradas()){
                    if (entrada.procurarEntradaParcial(informacao)){
                        sb.append(entrada).append('\n');
                    }
                }
            }

            System.out.println(sb);
            System.out.println("Indique o número do contacto ou 0 para esquecer:");
            String idx = scanner.nextLine().trim();
            if (idx.isBlank()) {
                return;
            }
            int indiceSelecionado;
            try {
                // Converte o valor introduzido para índice interno da lista.
                indiceSelecionado = Integer.parseInt(idx) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
                return;
            }

            // Só aceita contactos que estavam nos resultados da pesquisa.
            // Sai em caso de introdução de 0 pelo utilizador: indice = -1
            if (indiceSelecionado == -1 || !contactosEncontrados.contains(indiceSelecionado)) {
                return;
            }

            removerContactoIndividual(indiceSelecionado);
        }
    }

    // Procura contactos pela informação introduzida pelo utilizador.
    // Mostra os resultados e permite guardá-los num ficheiro de pesquisa.
    private void encontrarContactos(){
        // Pesquisa a palavra introduzida na identificação e nas entradas dos contactos.
        System.out.println("********************************\n*** Encontrar Contactos:\nIndique qual a informação a pesquisar nos contactos:");
        String informacao = scanner.nextLine().trim();
        if (informacao.isBlank()) {
            System.out.println("Informação não encontrada nos contactos");
            return;
        }

        ArrayList<Integer> contactosEncontrados = gestor.encontrarInformacao(informacao);
        if (contactosEncontrados.isEmpty()){
            System.out.println("Informação não encontrada nos contactos");
            return;
        }

        // Constrói a listagem completa dos contactos encontrados.
        // Esta listagem é usada tanto para imprimir como para gravar pesquisa em ficheiro.
        StringBuilder sb = new StringBuilder();
        for (int indice : contactosEncontrados) {
            sb.append(indice + 1)
              .append(" - ")
              .append(gestor.getContacto(indice))
              .append('\n');
        }

        System.out.println("Contactos com essa informação:");
        System.out.println(sb);
        perguntarEscreverPesquisa("********************************\n*** Encontrar Contactos:\nContactos com essa informação:\n" + sb);
    }

    // Mostra as estatísticas calculadas pelo gestor de contactos.
    private void mostrarEstatisticas() {
        // Recebe valor das estatística do gestor de contactos.
        HashMap<TipoContacto, Integer> contagem = gestor.estatisticas();
        // Formata e imprime a estatísticas.
        StringBuilder sb = new StringBuilder();
        sb.append("********************************\n");
        sb.append("*** Estatísticas:");
        sb.append(String.format(
                "\nTelemóvel: %d\nTelefone: %d\nMail: %d",
                contagem.getOrDefault(TipoContacto.TELEMOVEL, 0),
                contagem.getOrDefault(TipoContacto.TELEFONE, 0),
                contagem.getOrDefault(TipoContacto.MAIL, 0)
        ));
        System.out.println(sb);
    }

    // Pergunta ao utilizador se pretende guardar uma listagem em ficheiro.
    // O nome do ficheiro é escolhido manualmente pelo utilizador.
    private void perguntarEscreverFicheiro(String conteudo) {
        // Usado na opção Listar Contactos: o utilizador escolhe o nome do ficheiro.
        System.out.println("Escrever em Ficheiro (S/N)?");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Indique o nome do ficheiro:");
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty() && GestorFicheiros.exportarTexto(conteudo, nome)) {
                System.out.println("Ficheiro gravado com sucesso!");
            }
        }
    }

    // Pergunta ao utilizador se pretende guardar os resultados de uma pesquisa.
    // O nome do ficheiro é gerado automaticamente para evitar substituições.
    private void perguntarEscreverPesquisa(String conteudo) {
        // Usado na opção Encontrar Contactos: o nome é automático para evitar substituir pesquisas anteriores.
        System.out.println("Escrever em Ficheiro (S/N)?");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            String nome = GestorFicheiros.proximoNomePesquisa();
            if (GestorFicheiros.exportarTexto(conteudo, nome)) {
                System.out.println("Ficheiro gravado com sucesso: " + nome);
            }
        }
    }
}
