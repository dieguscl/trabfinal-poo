package trabfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Centraliza todas as operações com ficheiros.
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

    // Separador usado entre campos. O TAB reduz o risco de conflito com espaços nos nomes.
    private static final String SEP = "\t";

    // Impede a criação de objetos desta classe.
    // Todos os comportamentos são fornecidos através de métodos estáticos.
    private GestorFicheiros() {
        // Construtor privado porque esta classe só tem métodos estáticos.
    }

    // Lê o ficheiro de dados e reconstrói a lista de contactos guardada.
    // Se o ficheiro não existir ou não puder ser lido, devolve uma lista vazia.
    public static ArrayList<Contacto> carregar(String ficheiro) {
        // Começa sempre com uma lista vazia. Se houver erro ou ficheiro inexistente, devolve esta lista.
        ArrayList<Contacto> contactos = new ArrayList<>();
        Path caminho = Path.of(ficheiro);
        if (!Files.exists(caminho)) {
            return contactos;
        }

        try (BufferedReader leitor = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            // Guardam temporariamente o contacto que está a ser lido.
            // Quando surge uma nova linha C, o contacto anterior é fechado e adicionado à lista.
            Identificacao idAtual = null;
            ArrayList<EntradaContacto> entradasAtual = new ArrayList<>();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Ignora linhas vazias para tolerar pequenas quebras no ficheiro.
                if (linha.isBlank()) {
                    continue;
                }

                // split(..., -1) mantém campos vazios, importante para contactos sem empresa.
                String[] campos = linha.split(SEP, -1);
                if (campos[0].equals("C") && campos.length >= 2) {
                    // Encontrou um novo contacto: antes de começar este, guarda o anterior.
                    adicionarContacto(contactos, idAtual, entradasAtual);

                    // A empresa é opcional. Se não existir no ficheiro, fica como String vazia.
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

            // No fim do ficheiro ainda falta fechar o último contacto lido.
            adicionarContacto(contactos, idAtual, entradasAtual);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível ler o ficheiro de contactos (" + e.getMessage() + ").");
        }

        return contactos;
    }

    // Guarda todos os contactos no ficheiro de dados da aplicação.
    // Substitui o conteúdo anterior pelo estado atual da agenda.
    public static boolean guardar(ArrayList<Contacto> contactos, String ficheiro) {
        // Converte todos os contactos para o formato simples de texto usado pela aplicação.
        // O ficheiro anterior é substituído quando escrever(...) é chamado.
        StringBuilder sb = new StringBuilder();
        for (Contacto c : contactos) {
            Identificacao id = c.getIdentificacao();
            sb.append("C").append(SEP)
              .append(limpar(id.getNome())).append(SEP)
              .append(limpar(id.getEmpresa())).append("\n");

            // Cada entrada fica numa linha própria imediatamente a seguir ao contacto.
            for (EntradaContacto e : c.getEntradas()) {
                sb.append("E").append(SEP)
                  .append(e.getTipo().name()).append(SEP)
                  .append(limpar(e.getValor())).append("\n");
            }
        }
        return escrever(ficheiro, sb.toString());
    }

    // Escreve num ficheiro um texto já formatado por outra classe.
    // É usado para exportar listagens e resultados de pesquisa.
    public static boolean exportarTexto(String conteudo, String ficheiro) {
        // Usado para listagens e pesquisas, que já chegam prontas a escrever.
        return escrever(ficheiro, conteudo);
    }

    // Gera automaticamente um nome disponível para guardar resultados de pesquisa.
    // Evita substituir ficheiros de pesquisas anteriores.
    public static String proximoNomePesquisa() {
        // Gera nomes como pesquisa-2026-06-15.txt.
        // Se o nome já existir, acrescenta -2, -3, ... para não substituir ficheiros anteriores.
        String base = "pesquisa-" + LocalDate.now();
        String nome = base + ".txt";
        int n = 2;
        while (Files.exists(Path.of(nome))) {
            nome = base + "-" + n + ".txt";
            n++;
        }
        return nome;
    }

    // Constrói um Contacto a partir da identificação e das entradas lidas do ficheiro.
    // Só adiciona à lista contactos com identificação e pelo menos uma entrada.
    private static void adicionarContacto(ArrayList<Contacto> contactos,
            Identificacao id, ArrayList<EntradaContacto> entradas) {
        // Só cria um Contacto se houver identificação e pelo menos uma entrada.
        // Isto evita objetos inválidos quando o ficheiro tem linhas incompletas.
        if (id == null || entradas.isEmpty()) {
            return;
        }

        // O construtor de Contacto obriga a primeira entrada. As restantes são acrescentadas a seguir.
        Contacto contacto = new Contacto(id, entradas.get(0));
        for (int i = 1; i < entradas.size(); i++) {
            contacto.addEntradaContacto(entradas.get(i));
        }
        contactos.add(contacto);
    }

    // Tenta criar uma identificação a partir dos campos lidos no ficheiro.
    // Se os dados forem inválidos, devolve null para ignorar esse registo.
    private static Identificacao criarIdentificacao(String nome, String empresa) {
        // Se o ficheiro tiver uma identificação inválida, o registo é ignorado sem terminar o programa.
        try {
            return new Identificacao(nome, empresa);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Tenta criar uma entrada de contacto a partir dos campos lidos no ficheiro.
    // Se o tipo ou o valor forem inválidos, devolve null e a entrada é ignorada.
    private static EntradaContacto criarEntrada(String tipo, String valor) {
        // valueOf converte o texto do ficheiro no enum correspondente.
        // Se o tipo não existir ou o valor for inválido, a entrada é ignorada.
        try {
            return new EntradaContacto(TipoContacto.valueOf(tipo), valor);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Escreve o conteúdo recebido no ficheiro indicado.
    // Devolve true se a escrita terminou com sucesso e false se ocorreu erro.
    private static boolean escrever(String ficheiro, String conteudo) {
        // try-with-resources fecha automaticamente o escritor no fim da escrita.
        try (BufferedWriter escritor = Files.newBufferedWriter(
                Path.of(ficheiro), StandardCharsets.UTF_8)) {
            escritor.write(conteudo);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao gravar o ficheiro: " + e.getMessage());
            return false;
        }
    }

    // Remove caracteres que poderiam quebrar o formato do ficheiro de dados.
    // Mantém o texto numa única linha e sem separadores internos.
    private static String limpar(String texto) {
        // Remove caracteres que estragariam o formato por linhas e campos separados por TAB.
        return texto.replace(SEP, " ").replace("\n", " ").replace("\r", " ");
    }
}
