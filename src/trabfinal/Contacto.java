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
    private final Identificacao identificacao;

    private final ArrayList<EntradaContacto> entradas;

    public Contacto(Identificacao newIdentificacao, EntradaContacto newEntradaContacto){
        if (newIdentificacao == null) {
            throw new IllegalArgumentException("A identificação é obrigatória.");
        }
        if (newEntradaContacto == null) {
            throw new IllegalArgumentException("A entrada de contacto é obrigatória.");
        }

        identificacao = newIdentificacao;
        entradas = new ArrayList<>();
        entradas.add(newEntradaContacto);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(identificacao);
        for (int i = 0; i < entradas.size(); i++) {
            sb.append('\n').append(entradas.get(i));
        }

        return sb.toString();
    }

    public void addEntradaContacto(EntradaContacto newEntradaContacto){
        if (newEntradaContacto == null) {
            throw new IllegalArgumentException("A entrada de contacto é obrigatória.");
        }

        entradas.add(newEntradaContacto);
    }

    public boolean removerEntradaContacto(EntradaContacto newEntradaContacto){
        return entradas.remove(newEntradaContacto);
    }

    // Procura entradas exatamente iguais à entrada recebida.
    public ArrayList<EntradaContacto> procurarInformacao(EntradaContacto newEntradaContacto){
        ArrayList<EntradaContacto> informacao = new ArrayList<>();
        for(int i = 0; i < entradas.size(); i++){
            if(entradas.get(i).equals(newEntradaContacto)){
                informacao.add(entradas.get(i));
            }
        }
        return informacao;
    }

    // Procura entradas cujo valor contenha o texto indicado pelo utilizador.
    public ArrayList<EntradaContacto> procurarInformacaoParcial(String newString){
        ArrayList<EntradaContacto> informacao = new ArrayList<>();
        for(int i = 0; i < entradas.size(); i++){
            if(entradas.get(i).procurarEntradaParcial(newString)){
                informacao.add(entradas.get(i));
            }
        }
        return informacao;
    }

    // Verifica se outro contacto partilha alguma entrada com este contacto.
    public boolean temAlgumaInformacaoIgual(Contacto newContacto){
        for (EntradaContacto entradaOutro : newContacto.getEntradas()) {
            if (temInformacao(entradaOutro)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<EntradaContacto> getEntradas(){
        return new ArrayList<>(entradas);
    }

    public Identificacao getIdentificacao(){
        return identificacao;
    }

    public boolean temInformacao(EntradaContacto entrada){
        for (int i = 0; i < entradas.size(); i++){
            if (entradas.get(i).temMesmoValor(entrada)){
                return true;
            }
        }
        return false;
    }

    public void acrescentarInformacao(Contacto outro){
        for (int i = 0; i < outro.getEntradas().size(); i++){
            EntradaContacto entrada = outro.getEntradas().get(i);
            if (!temInformacao(entrada)){
                entradas.add(entrada);
            }
        }
    }

}
