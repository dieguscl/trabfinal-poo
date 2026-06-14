/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


/**
 *
 * @author luis
 */
public class Menu {

    private final GestorContactos gestor;
    private final Scanner scanner = new Scanner(System.in);
    private int runNumber = 0;

    // load ficheiro
    // iniciar menu base
    public Menu(GestorContactos newGestor) {
        // gestor.load
        gestor = newGestor;
    }

    
//    public void menuInicial() {
//        if (runNumber != 0) {
//            System.out.println("\n-----------------");
//            runNumber++;
//        }
//        String s = "1 - Listar Contactos\n";
//        s += "2 - Acrescentar Contacto\n";
//        s += "3 - Remover Contacto\n";
//        s += "4 - Encontrar Contactos\n";
//        s += "5 - Estatísticas\n";
//        s += "6 - Sair";
//        System.out.println(s);
//
//        switch (scanner.nextLine().trim()) {
//            case "1" ->
//                mostrarListaContactos();
//            case "2" ->
//                acrescentarContacto();
//            case "3" ->
//                removerContacto();
//            case "4" ->
//                encontrarContactos();
//            case "5" ->
//                mostrarEstatisticas();
//            case "6" -> {
//            }
//            default ->
//                menuInicial();
//        }
//    }
    
    public void menuInicial() {
    boolean emExecucao = true;

        while (emExecucao) {
            mostrarOpcoes();
            switch (scanner.nextLine().trim()) {
                case "1" -> mostrarListaContactos();
                case "2" -> acrescentarContacto();
                case "3" -> removerContacto();
                case "4" -> encontrarContactos();
                case "5" -> mostrarEstatisticas();
                case "6" -> {
                    //funcao de save;
                    emExecucao = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    
    private void mostrarOpcoes() {
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
    
    

    private void mostrarListaContactos() {
        System.out.println("********************************\n*** Listar Contactos:");
        ArrayList<Contacto> listaContactos = gestor.getListaContactos();
        if (listaContactos.isEmpty()) {
            System.out.println("\nNão há contactos!\nPrima entrer para continuar!");
            scanner.nextLine();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < listaContactos.size(); i++) {
                sb.append('\n')
                  .append(i + 1)
                  .append(" - ")
                  .append(listaContactos.get(i));
            }
            System.out.println(sb);
            System.out.println("Escrever em Ficheiro (S/N)?");

            switch (scanner.nextLine().trim().toLowerCase()) {
                case "s" ->
                    System.out.println("Importar");
                case "n" ->
                    System.out.println("Não importar");
            }
        }

        //menuInicial();
    }

    private void acrescentarContacto() {
        System.out.println("********************************\n*** Acrescentar contacto:");
        System.out.println("Indique o nome do contacto:");
        String nome = scanner.nextLine();
        if (nome.equals("")) {
            //menuInicial();
            return;
        }

        System.out.println("Indique o nome da companhia, se for um contacto profissional, ou então deixe vazio:");
        String empresa = scanner.nextLine();

        Identificacao novaId = new Identificacao(nome, empresa);
        Contacto novoContacto = null;

        while (true) {
            System.out.println("Indique os dados do contacto ou vazio para terminar:");
            String dados = scanner.nextLine();
            if (dados.equals("")) {
                break;
            }

            TipoContacto tipo = lerTipoContacto();
            EntradaContacto novaEntrada = new EntradaContacto(tipo, dados);

            if (novoContacto == null) {
                novoContacto = new Contacto(novaId, novaEntrada);
            } else if (novoContacto.temInformacao(novaEntrada)) {
                System.out.println("Essa informação já foi introduzida neste contacto.");
            } else {
                novoContacto.addEntradaContacto(novaEntrada);
            }
        }

        if (novoContacto == null) {
            //menuInicial();
            return;
        }

        adicionarComVerificacao(novoContacto);
        //menuInicial();
    }

    private TipoContacto lerTipoContacto() {
        while (true) {
            System.out.println("Indique o tipo do contacto:");
            System.out.println("1 - Telefone");
            System.out.println("2 - Telemovel");
            System.out.println("3 - Mail");
            try {
                TipoContacto tipo = TipoContacto.fromInt(Integer.parseInt(scanner.nextLine().trim()));
                if (tipo != null) {
                    return tipo;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Tipo inválido.");
        }
    }

    private void adicionarComVerificacao(Contacto novo) {
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

        ArrayList<Contacto> comInfoRepetida = gestor.encontrarInformacaoRepetidaContactos(novo);
        if (!comInfoRepetida.isEmpty()) {
            System.out.println("A informação introduzida já existe nos seguintes contactos:");
            for (int i = 0; i < comInfoRepetida.size(); i++) {
                System.out.println(comInfoRepetida.get(i));
            }
            System.out.println("1 - Acrescentar a informação ao contacto já existente");
            System.out.println("2 - Acrescentar o novo contacto");
            System.out.println("0 - Desistir de introduzir este contacto");

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

        gestor.acrescentarContacto(novo);
        System.out.println("Contacto acrescentado.");
    }
    
    private void removerContactoIndividual(int indiceEncontrado){
        Contacto contactoEncontrado = gestor.getListaContactos().get(indiceEncontrado);
        System.out.println("O contacto encontrado foi:");
        System.out.println((indiceEncontrado+1) + " - " + contactoEncontrado.toString() + "\n");
        System.out.println("1 - Remover o contacto completo");
        System.out.println("2 - Eliminar um tipo de contacto");
        System.out.println("3 - Voltar");
        System.out.println("Escolhar uma opção entre [1,3]:");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                gestor.removerContacto(indiceEncontrado);
                System.out.println("Contacto removido.");
            }
            case "2" -> {
                System.out.println("Indique o pretende remover:");
                System.out.println("Indique os dados do contacto ou vazio para terminar:");
                String valor = scanner.nextLine().trim();

                if(!valor.isEmpty()){
                    TipoContacto tipo = lerTipoContacto();
                    EntradaContacto entradaCheck = new EntradaContacto(tipo, valor); 
                    if(contactoEncontrado.removerEntradaContacto(entradaCheck)){
                        System.out.println("Entrada removida com sucesso.");
                    }
                    else{
                        System.out.println("Entrada não encontrada.");
                    }
                }
            }
            default ->
                System.out.println("Contacto não introduzido.");
        }
    }

    private void removerContacto() {
        System.out.println("********************************\n*** Remover contacto:\nIndique qual a informação a pesquisar nos contactos:");
        String informacao = scanner.nextLine().trim();
        ArrayList<Integer> contactosEncontrados = gestor.encontrarInformacao(informacao);
        String s = "";
        if(contactosEncontrados.isEmpty() || informacao.isBlank()){
            System.out.println("Informação não encontrada nos contactos");
        }
        else if(contactosEncontrados.size() == 1){
            
            //String s = "";
            int indiceEncontrado = contactosEncontrados.get(0);
            
            removerContactoIndividual(indiceEncontrado);
                    
//            Contacto contactoEncontrado = gestor.getListaContactos().get(indiceEncontrado);
//            
//            s += (indiceEncontrado+1) + " - " + contactoEncontrado.toString() + "\n";    
//            System.out.println(s);
//            
//            System.out.println("1 - Remover o contacto completo");
//            System.out.println("2 - Eliminar um tipo de contacto");
//            System.out.println("3 - Voltar");
//            System.out.println("Escolhar uma opção entre [1,3]:");
//
//            switch (scanner.nextLine().trim()) {
//                case "1" -> {
//                    gestor.removerContacto(indiceEncontrado);
//                    System.out.println("Contacto removido.");
//                }
//                case "2" -> {
//                    System.out.println("Indique o pretende remover:");
//                    System.out.println("Indique os dados do contacto ou vazio para terminar:");
//                    String valor = scanner.nextLine();
//                    
//                    if(!valor.isEmpty()){
//                        TipoContacto tipo = lerTipoContacto();
//                        EntradaContacto entradaCheck = new EntradaContacto(tipo, valor); 
//                        if(contactoEncontrado.getEntradas().contains(entradaCheck)){
//                            contactoEncontrado.getEntradas().remove(entradaCheck);
//                            System.out.println("Entrada removida com sucesso.");
//                        }
//                        else{
//                            System.out.println("Entrada não encontrada.");
//                        }
//                    }
//                }
//                default ->
//                    System.out.println("Contacto não introduzido.");
//            }
        }
        else{
            System.out.println("Contactos com essa informação:");
            StringBuilder sb = new StringBuilder();
            for (int indice : contactosEncontrados){
                Contacto contacto = gestor.getListaContactos().get(indice);
                
                sb.append(indice + 1)
                  .append(" - ")
                  .append(contacto.getIdentificacao().getNome())
                  .append('\n');

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
                //menuInicial();
                return;
            }
            int indiceSelecionado;
            try {
                indiceSelecionado = Integer.parseInt(idx) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
                //menuInicial();
                return;
            }
            if (indiceSelecionado == -1 || !contactosEncontrados.contains(indiceSelecionado)) {
                //menuInicial();
                return;
            }
            
            removerContactoIndividual(indiceSelecionado);
            
//            Contacto contactoSelecionado = gestor.getListaContactos().get(indice);
//            System.out.println("O contacto encontrado foi:");
//                
//            
//            System.out.println((indice+1) + " - " + contactoSelecionado.toString() + "\n");
//            
//            System.out.println("1 - Remover o contacto completo");
//            System.out.println("2 - Eliminar um tipo de contacto");
//            System.out.println("3 - Voltar");
//            System.out.println("Escolhar uma opção entre [1,3]:");
//            
//            switch (scanner.nextLine().trim()) {
//                case "1" -> {
//                    gestor.removerContacto(indice);
//                    System.out.println("Contacto removido.");
//                }
//                case "2" -> {
//                    System.out.println("Indique o pretende remover:");
//                    System.out.println("Indique os dados do contacto ou vazio para terminar:");
//                    String valor = scanner.nextLine();
//                    
//                    if(!valor.isEmpty()){
//                        TipoContacto tipo = lerTipoContacto();
//                        EntradaContacto entradaCheck = new EntradaContacto(tipo, valor); // criar um metodo recebe entrada Check e remove a entrada
//                        if(contactoSelecionado.getEntradas().contains(entradaCheck)){ // mudar para pesquisa parcial (criar metodo para pesquisa parcial)
//                            contactoSelecionado.getEntradas().remove(entradaCheck);
//                            System.out.println("Entrada removida com sucesso.");
//                        }
//                        else{
//                            System.out.println("Entrada não encontrada.");
//                        }
//                    }
//                }
//                default ->
//                    System.out.println("Contacto não introduzido.");
//            }
        }
        
        //menuInicial();
    }

    private void encontrarContactos(){
        System.out.println("********************************\n*** Encontrar Contactos:\nIndique qual a informação a pesquisar nos contactos:");
        String informacao = scanner.nextLine().trim();
        ArrayList<Integer> contactosEncontrados = gestor.encontrarInformacao(informacao);
        if (contactosEncontrados.isEmpty()){
            System.out.println("Informação não encontrada nos contactos");
            //menuInicial();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int indice : contactosEncontrados) {
            sb.append(indice + 1)
              .append(" - ")
              .append(gestor.getListaContactos().get(indice))
              .append('\n');
        }

        System.out.println("Contactos com essa informação:");
        System.out.println(sb);
        
        //menuInicial();
    }
    
    
    private void mostrarEstatisticas() {
        System.out.println(gestor.estatisticas());

        //menuInicial();
    }
}
