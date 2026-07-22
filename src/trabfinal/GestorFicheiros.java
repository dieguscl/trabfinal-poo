package trabfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Guarda e carrega a agenda no ficheiro de dados e também exporta listagens
 * já formatadas para ficheiros de texto escolhidos pelo utilizador.
 *
 * Formato usado no ficheiro de dados:
 * C<TAB>nome<TAB>empresa -> início de um contacto
 * E<TAB>TIPO<TAB>valor   -> entrada associada ao último contacto lido
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class GestorFicheiros {

    private static final String SEP = "\t";

    private GestorFicheiros() {
    }

    // Lê o ficheiro de dados e reconstrói a lista de contactos guardada.
    // Se o ficheiro não existir ou não puder ser lido, devolve uma lista vazia.
    public static ArrayList<Contacto> carregar(String ficheiro) {
        
        ArrayList<Contacto> contactos = new ArrayList<>();
        File caminho = new File(ficheiro);
        if (!caminho.exists()) {
            return contactos;
        }

        // Charset por omissão nos dois lados (leitura e escrita), para serem sempre coerentes.
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            
            Identificacao idAtual = null;
            ArrayList<EntradaContacto> entradasAtual = new ArrayList<>();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                
                if (linha.isBlank()) {
                    continue;
                }

                String[] campos = linha.split(SEP, -1);
                if (campos[0].equals("C") && campos.length >= 2) {

                    adicionarContacto(contactos, idAtual, entradasAtual);

                    String empresa = campos.length >= 3 ? campos[2] : "";
                    idAtual = criarIdentificacao(campos[1], empresa);
                    entradasAtual = new ArrayList<>();
                } else if (campos[0].equals("E") && campos.length >= 3 && idAtual != null) {
                    // Linhas E só são aceites depois de já ter sido lido um contacto válido.
                    EntradaContacto entrada = criarEntrada(campos[1], campos[2]);
                    if (entrada != null) {
                        entradasAtual.add(entrada);
                    }
                }
            }

            adicionarContacto(contactos, idAtual, entradasAtual);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível ler o ficheiro de contactos (" + e.getMessage() + ").");
        }

        return contactos;
    }

    public static boolean guardar(ArrayList<Contacto> contactos, String ficheiro) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(ficheiro))))) {
            for (Contacto contacto : contactos) {
                Identificacao id = contacto.getIdentificacao();
                printWriter.println("C" + SEP + limpar(id.getNome()) + SEP + limpar(id.getEmpresa()));

                for (EntradaContacto e : contacto.getEntradas()) {
                    printWriter.println("E" + SEP + e.getTipo().name() + SEP + limpar(e.getValor()));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean exportarTexto(String conteudo, String ficheiro) {
        return escrever(ficheiro, conteudo);
    }

    public static String proximoNomePesquisa() {
        // Gera nomes como pesquisa-2026-06-15.txt.
        String base = "pesquisa-" + LocalDate.now();
        String nome = base + ".txt";
        int n = 2;
        while (Files.exists(Path.of(nome))) {
            nome = base + "-" + n + ".txt";
            n++;
        }
        return nome;
    }

    private static void adicionarContacto(ArrayList<Contacto> contactos,
            Identificacao id, ArrayList<EntradaContacto> entradas) {
        
        if (id == null || entradas.isEmpty()) {
            return;
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
            return null;
        }
    }

    private static EntradaContacto criarEntrada(String tipo, String valor) {
        try {
            return new EntradaContacto(TipoContacto.valueOf(tipo), valor);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static boolean escrever(String ficheiro, String conteudo) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter(new File(ficheiro))))) {
            printWriter.print(conteudo);
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Remove caracteres que poderiam quebrar o formato do ficheiro de dados.
    private static String limpar(String texto) {
        return texto.replace(SEP, " ").replace("\n", " ").replace("\r", " ");
    }
}
