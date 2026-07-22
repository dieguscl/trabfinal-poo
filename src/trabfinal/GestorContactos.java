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
    private final ArrayList<Contacto> listaContactos;

    public GestorContactos(){
        // A lista começa vazia e é preenchida pelo Menu ou pelo carregamento do ficheiro.
        listaContactos = new ArrayList<>();
    }

    public void acrescentarContacto(Contacto contacto){
        listaContactos.add(contacto);
    }

    public void removerContacto(int idx){
        listaContactos.remove(idx);
    }

    public ArrayList<Contacto> getListaContactos(){
        return new ArrayList<>(listaContactos);
    }

    public Contacto getContacto(int indice){
        if (indice < 0 || indice >= listaContactos.size()){
            throw new IllegalArgumentException("Índice inválido.");
        }
        return listaContactos.get(indice);
    }

    // Devolve os índices dos contactos encontrados para o menu poder usá-los.
    public ArrayList<Integer> encontrarInformacao(String newString){
        ArrayList<Integer> contactosEncontrados = new ArrayList<>();
        for (int i = 0; i < listaContactos.size(); i++){
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
        HashMap<TipoContacto, Integer> contagem = new HashMap<>();

        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            ArrayList<EntradaContacto> entradas = contacto.getEntradas();
            for (int j = 0; j < entradas.size(); j++) {
                TipoContacto tipo = entradas.get(j).getTipo();

                contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
            }
        }
        return contagem;
    }
}
