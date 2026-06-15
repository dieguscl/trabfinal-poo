package trabfinal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gere a coleção principal de contactos da aplicação.
 * Esta classe não pergunta nada ao utilizador: apenas acrescenta, remove, procura
 * e devolve informação sobre os contactos guardados em memória.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class GestorContactos {
    // ArrayList porque a agenda precisa de guardar vários contactos e aceder a eles por índice.
    private final ArrayList<Contacto> listaContactos;

    // Cria um gestor de contactos com a lista principal vazia.
    // Os contactos são acrescentados depois pelo menu ou pelo carregamento do ficheiro.
    public GestorContactos(){
        // A lista começa vazia e é preenchida pelo Menu ou pelo carregamento do ficheiro.
        listaContactos = new ArrayList<>();
    }

    // Acrescenta um contacto à agenda em memória.
    public void acrescentarContacto(Contacto contacto){
        // Acrescenta no fim da lista. As verificações de duplicados são feitas antes, no Menu.
        listaContactos.add(contacto);
    }

    // Remove da agenda o contacto que está no índice indicado.
    // O índice recebido é o índice interno da lista, começando em zero.
    public void removerContacto(int idx){
        // Remove pelo índice interno da lista, que começa em zero.
        listaContactos.remove(idx);
    }

    // Devolve uma cópia da lista de contactos guardada no gestor.
    // Permite consultar a agenda sem alterar diretamente a lista interna.
    public ArrayList<Contacto> getListaContactos(){
        return new ArrayList<>(listaContactos);
    }

    // Devolve o contacto que está no índice indicado.
    // Antes de aceder à lista, valida se o índice está dentro dos limites.
    public Contacto getContacto(int indice){
        // Valida o índice antes de consultar a lista, para apresentar um erro mais controlado.
        if (indice < 0 || indice >= listaContactos.size()){
            throw new IllegalArgumentException("Índice inválido.");
        }
        return listaContactos.get(indice);
    }

    // Procura texto na identificação e nas entradas de todos os contactos.
    // Devolve os índices dos contactos encontrados para o menu poder usá-los.
    public ArrayList<Integer> encontrarInformacao(String newString){
        // Devolve índices, não os próprios contactos, porque o Menu precisa de saber
        // que posição remover ou apresentar depois da pesquisa.
        ArrayList<Integer> contactosEncontrados = new ArrayList<>();
        for (int i = 0; i < listaContactos.size(); i++){
            // Primeiro pesquisa no nome/empresa. Se encontrar, passa logo ao próximo contacto
            // para não adicionar o mesmo índice duas vezes.
            if (listaContactos.get(i).getIdentificacao().procurarIdentificacaoParcial(newString)){
                contactosEncontrados.add(i);
                continue;
            }

            // Se não encontrou na identificação, procura nas entradas de contacto.
            ArrayList<EntradaContacto> entradasEncontradas = listaContactos.get(i).procurarInformacaoParcial(newString);
            if (!entradasEncontradas.isEmpty()) {
                contactosEncontrados.add(i);
            }
        }
        return contactosEncontrados;
    }

    // Procura na agenda um contacto com a mesma identificação do contacto recebido.
    // Devolve o contacto existente ou null se não houver repetição.
    public Contacto encontrarContactosRepetidos(Contacto newContacto){
        // Considera repetido um contacto com a mesma identificação.
        // Se encontrar, devolve o contacto já existente para permitir juntar informação.
        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            if (contacto.getIdentificacao().equals(newContacto.getIdentificacao())){
                return contacto;
            }
        }
        return null;
    }

    // Procura contactos já existentes que partilhem alguma entrada com o novo contacto.
    // Devolve todos os contactos com informação repetida.
    public ArrayList<Contacto> encontrarInformacaoRepetidaContactos(Contacto newContacto){
        // Procura contactos que tenham pelo menos uma entrada igual à do novo contacto.
        // Isto deteta casos como o mesmo número associado a nomes diferentes.
        ArrayList<Contacto> listaContactosInfoRepetida = new ArrayList<>();

        for (int i = 0; i < listaContactos.size(); i++){
            if (newContacto.temAlgumaInformacaoIgual(listaContactos.get(i))){
                listaContactosInfoRepetida.add(listaContactos.get(i));
            }
        }
        return listaContactosInfoRepetida;
    }

    // Conta quantas entradas existem de cada tipo de contacto e devolve resultados.
    public HashMap<TipoContacto, Integer> estatisticas(){
        // HashMap guarda a contagem por tipo de contacto.
        // A chave é o TipoContacto e o valor é o número de entradas desse tipo.
        HashMap<TipoContacto, Integer> contagem = new HashMap<>();

        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            ArrayList<EntradaContacto> entradas = contacto.getEntradas();
            for (int j = 0; j < entradas.size(); j++) {
                TipoContacto tipo = entradas.get(j).getTipo();

                // getOrDefault evita testar se o tipo já existe no mapa antes de somar.
                contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
            }
        }
        return contagem;
    }
}
