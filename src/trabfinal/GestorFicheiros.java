/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Responsavel por toda a manipulacao de ficheiros:
 *  - importar/exportar a agenda no ficheiro de dados (persistencia entre execucoes);
 *  - exportar listagens ja formatadas para ficheiros de texto a pedido do utilizador.
 *
 * Formato do ficheiro de dados (uma linha por registo, campos separados por TAB):
 *   C{TAB}nome{TAB}empresa   -> inicio de um contacto
 *   E{TAB}TIPO{TAB}valor     -> entrada do ultimo contacto lido (TIPO = TELEFONE|TELEMOVEL|MAIL)
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class GestorFicheiros {

    private static final String SEP = "\t";

    /** Classe utilitaria: so disponibiliza metodos estaticos. */
    private GestorFicheiros() {
    }

    // -------------------------------------------------------------- Importar

    /**
     * Le os contactos a partir do ficheiro de dados. Se o ficheiro ainda nao
     * existir (primeira utilizacao) devolve uma lista vazia.
     *
     * @param ficheiro caminho do ficheiro de dados
     * @return lista de contactos lida (nunca null)
     */
    public static ArrayList<Contacto> carregar(String ficheiro) {
        ArrayList<Contacto> contactos = new ArrayList<>();
        Path caminho = Path.of(ficheiro);
        if (!Files.exists(caminho)) {
            return contactos;
        }

        try (BufferedReader leitor = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            Identificacao idAtual = null;
            ArrayList<EntradaContacto> entradasAtual = new ArrayList<>();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                String[] campos = linha.split(SEP, -1);
                if (campos[0].equals("C") && campos.length >= 2) {
                    // Fecha o contacto anterior antes de comecar um novo.
                    adicionarContacto(contactos, idAtual, entradasAtual);
                    // A empresa e opcional: contacto pessoal pode vir so "C{TAB}nome".
                    String empresa = campos.length >= 3 ? campos[2] : "";
                    idAtual = criarIdentificacao(campos[1], empresa);
                    entradasAtual = new ArrayList<>();
                } else if (campos[0].equals("E") && campos.length >= 3 && idAtual != null) {
                    EntradaContacto entrada = criarEntrada(campos[1], campos[2]);
                    if (entrada != null) {
                        entradasAtual.add(entrada);
                    }
                }
            }
            adicionarContacto(contactos, idAtual, entradasAtual); // ultimo contacto
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível ler o ficheiro de contactos (" + e.getMessage() + ").");
        }

        return contactos;
    }

    // --------------------------------------------------------------- Exportar

    /**
     * Grava toda a agenda no ficheiro de dados, substituindo o conteudo
     * anterior. Chamado antes de o programa terminar (opcao Sair).
     *
     * @return true se gravou com sucesso
     */
    public static boolean guardar(ArrayList<Contacto> contactos, String ficheiro) {
        StringBuilder sb = new StringBuilder();
        for (Contacto c : contactos) {
            Identificacao id = c.getIdentificacao();
            sb.append("C").append(SEP)
              .append(limpar(id.getNome())).append(SEP)
              .append(limpar(id.getEmpresa())).append("\n");
            for (EntradaContacto e : c.getEntradas()) {
                sb.append("E").append(SEP)
                  .append(e.getTipo().name()).append(SEP)
                  .append(limpar(e.getValor())).append("\n");
            }
        }
        return escrever(ficheiro, sb.toString());
    }

    /**
     * Escreve um texto (uma listagem ja formatada) num ficheiro de texto
     * indicado pelo utilizador. Usado pelas opcoes "Listar" e "Encontrar".
     *
     * @return true se gravou com sucesso
     */
    public static boolean exportarTexto(String conteudo, String ficheiro) {
        return escrever(ficheiro, conteudo);
    }

    // ---------------------------------------------------------------- Apoio

    private static void adicionarContacto(ArrayList<Contacto> contactos,
            Identificacao id, ArrayList<EntradaContacto> entradas) {
        if (id == null || entradas.isEmpty()) {
            return; // sem identificacao valida ou sem entradas: nada a guardar
        }
        Contacto contacto = new Contacto(id, entradas.get(0));
        for (int i = 1; i < entradas.size(); i++) {
            contacto.addEntradaContacto(entradas.get(i));
        }
        contactos.add(contacto);
    }

    private static Identificacao criarIdentificacao(String nome, String empresa) {
        try {
            return new Identificacao(nome, empresa);
        } catch (IllegalArgumentException e) {
            return null; // nome invalido no ficheiro: ignora o registo
        }
    }

    private static EntradaContacto criarEntrada(String tipo, String valor) {
        try {
            return new EntradaContacto(TipoContacto.valueOf(tipo), valor);
        } catch (IllegalArgumentException e) {
            return null; // tipo desconhecido ou valor invalido: ignora a entrada
        }
    }

    private static boolean escrever(String ficheiro, String conteudo) {
        try (BufferedWriter escritor = Files.newBufferedWriter(
                Path.of(ficheiro), StandardCharsets.UTF_8)) {
            escritor.write(conteudo);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao gravar o ficheiro: " + e.getMessage());
            return false;
        }
    }

    /** Remove separadores/quebras de linha que corromperiam o formato. */
    private static String limpar(String texto) {
        return texto.replace(SEP, " ").replace("\n", " ").replace("\r", " ");
    }
}
