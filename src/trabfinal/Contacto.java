package trabfinal;

import java.util.ArrayList;

/**
 * Representa um contacto da agenda.
 * Um contacto tem uma identificação única para o utilizador e uma lista de entradas
 * associadas, como telefone, telemóvel ou mail. A classe também trata das pesquisas
 * e das comparações entre as entradas deste contacto.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class Contacto {
    // A identificação é final porque não deve mudar depois de o contacto ser criado.
    private final Identificacao identificacao;

    // Lista interna com todas as formas de contactar esta pessoa/empresa.
    private final ArrayList<EntradaContacto> entradas;

    // Cria um contacto com uma identificação e a primeira entrada obrigatória.
    // Garante que o objeto começa sempre num estado válido.
    public Contacto(Identificacao newIdentificacao, EntradaContacto newEntradaContacto){
        // Um contacto sem identificação ou sem qualquer entrada ficaria incompleto.
        // Por isso a validação é feita logo no construtor.
        if (newIdentificacao == null) {
            throw new IllegalArgumentException("A identificação é obrigatória.");
        }
        if (newEntradaContacto == null) {
            throw new IllegalArgumentException("A entrada de contacto é obrigatória.");
        }

        // Guarda a identificação recebida e inicializa a lista de entradas.
        identificacao = newIdentificacao;
        entradas = new ArrayList<>();
        entradas.add(newEntradaContacto);
    }

    // Converte o contacto num texto formatado para listagens e pesquisas.
    // Inclui a identificação e todas as entradas de contacto.
    @Override
    public String toString(){
        // StringBuilder é usado porque o texto é construído por partes.
        // Primeiro aparece a identificação e depois cada entrada numa linha própria.
        StringBuilder sb = new StringBuilder();
        sb.append(identificacao);
        for (int i = 0; i < entradas.size(); i++) {
            sb.append('\n').append(entradas.get(i));
        }

        return sb.toString();
    }

    // Acrescenta uma nova entrada à lista interna deste contacto.
    // Rejeita entradas nulas para manter os dados consistentes.
    public void addEntradaContacto(EntradaContacto newEntradaContacto){
        // Mantém a lista interna consistente: não deixa entrar uma referência nula.
        if (newEntradaContacto == null) {
            throw new IllegalArgumentException("A entrada de contacto é obrigatória.");
        }

        entradas.add(newEntradaContacto);
    }

    // Remove uma entrada específica deste contacto.
    // Devolve true quando a entrada existia e foi removida.
    public boolean removerEntradaContacto(EntradaContacto newEntradaContacto){
        // O remove procura a entrada usando o equals definido em EntradaContacto.
        // Assim, para remover, o tipo e o valor têm de coincidir.
        return entradas.remove(newEntradaContacto);
    }

    // Procura entradas exatamente iguais à entrada recebida.
    public ArrayList<EntradaContacto> procurarInformacao(EntradaContacto newEntradaContacto){
        // O resultado é uma lista para manter a estrutura igual à pesquisa parcial.
        ArrayList<EntradaContacto> informacao = new ArrayList<>();
        for(int i = 0; i < entradas.size(); i++){
            if(entradas.get(i).equals(newEntradaContacto)){
                informacao.add(entradas.get(i));
            }
        }
        return informacao;
    }

    // Procura entradas cujo valor contenha o texto indicado pelo utilizador.
    // Devolve todas as entradas deste contacto que satisfazem a pesquisa.
    public ArrayList<EntradaContacto> procurarInformacaoParcial(String newString){
        // Pesquisa parcial: basta o texto existir dentro do valor da entrada.
        // A comparação concreta fica em EntradaContacto, para não repetir lógica aqui.
        ArrayList<EntradaContacto> informacao = new ArrayList<>();
        for(int i = 0; i < entradas.size(); i++){
            if(entradas.get(i).procurarEntradaParcial(newString)){
                informacao.add(entradas.get(i));
            }
        }
        return informacao;
    }

    // Verifica se outro contacto partilha alguma entrada com este contacto.
    // Serve para detetar informação repetida antes de acrescentar contactos novos.
    public boolean temAlgumaInformacaoIgual(Contacto newContacto){
        // Se uma única entrada do outro contacto já existir neste, há informação repetida.
        for (EntradaContacto entradaOutro : newContacto.getEntradas()) {
            if (temInformacao(entradaOutro)) {
                return true;
            }
        }
        return false;
    }

    // Devolve as entradas do contacto sem expor diretamente a lista interna.
    public ArrayList<EntradaContacto> getEntradas(){
        // Devolve uma cópia da lista.
        return new ArrayList<>(entradas);
    }

    // Devolve a identificação associada a este contacto.
    public Identificacao getIdentificacao(){
        return identificacao;
    }

    // Indica se este contacto já tem uma entrada com o mesmo valor.
    // O tipo não é comparado; isso permite detetar o mesmo número/mail mesmo que venha com outro tipo.
    public boolean temInformacao(EntradaContacto entrada){
        // Verifica se já existe uma entrada com o mesmo valor.
        for (int i = 0; i < entradas.size(); i++){
            if (entradas.get(i).temMesmoValor(entrada)){
                return true;
            }
        }
        return false;
    }

    // Acrescenta a este contacto as entradas existentes noutro contacto.
    // Só copia entradas que ainda não existem, para evitar duplicações.
    public void acrescentarInformacao(Contacto outro){
        // Junta a informação de outro contacto a este contacto.
        // Só adiciona entradas novas, para evitar duplicados dentro do mesmo contacto.
        for (int i = 0; i < outro.getEntradas().size(); i++){
            EntradaContacto entrada = outro.getEntradas().get(i);
            if (!temInformacao(entrada)){
                entradas.add(entrada);
            }
        }
    }

}
