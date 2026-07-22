package trabfinal;

/**
 * Representa uma entrada individual de contacto.
 * Cada entrada combina o tipo de contacto com o respetivo valor. Exemplos:
 * telefone + número fixo, telemóvel + número móvel, mail + endereço eletrónico.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class EntradaContacto {
    private final TipoContacto tipo;
    private final String valor;

    public EntradaContacto(TipoContacto newTipo, String newValor){
            if (newTipo == null) {
                throw new IllegalArgumentException("O tipo de contacto é obrigatório.");
            }
            if (newValor == null || newValor.trim().isEmpty()) {
                throw new IllegalArgumentException("O valor do contacto é obrigatório.");
            }

            tipo = newTipo;
            valor = newValor.trim();
    }

    @Override
    public String toString(){
        return String.format("%s: %s", tipo.toString(), valor);
    }

    public String getValor(){
        return valor;
    }

    public TipoContacto getTipo(){
        return tipo;
    }

    public boolean procurarEntradaParcial(String newValor){
        return valor.toLowerCase().contains(newValor.toLowerCase());
    }

    // Compara esta entrada com outra usando apenas o valor.
    // Permite detetar informação repetida mesmo quando o tipo é diferente.
    public boolean temMesmoValor(EntradaContacto outraEntrada) {
        return valor.equals(outraEntrada.getValor());
    }

    // Duas entradas são iguais quando têm o mesmo tipo e o mesmo valor.
    @Override
    public boolean equals(Object obj){
        // Primeiro trata os casos simples: mesma referência ou objeto incompatível.
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        // Depois de confirmar que é uma EntradaContacto, compara os atributos relevantes.
        EntradaContacto other = (EntradaContacto) obj;
        return tipo.equals(other.tipo) && valor.equals(other.valor);
    }

    // Calcula o código hash de forma coerente com o método equals.
    @Override
    public int hashCode() {
        return java.util.Objects.hash(tipo, valor);
    }
}
