package trabfinal;

/**
 * Representa uma entrada individual de contacto.
 * Cada entrada combina o tipo de contacto com o respetivo valor. Exemplos:
 * telefone + número fixo, telemóvel + número móvel, mail + endereço eletrónico.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class EntradaContacto {
    // O tipo e o valor são final porque a entrada é criada já com a informação definitiva.
    private final TipoContacto tipo;
    private final String valor;

    // Cria uma entrada de contacto com tipo e valor obrigatórios.
    public EntradaContacto(TipoContacto newTipo, String newValor){
            // Valida logo os dados de entrada para impedir objetos incompletos.
            // Isto simplifica o resto do programa: se o objeto existe, tem tipo e valor válidos.
            if (newTipo == null) {
                throw new IllegalArgumentException("O tipo de contacto é obrigatório.");
            }
            if (newValor == null || newValor.trim().isEmpty()) {
                throw new IllegalArgumentException("O valor do contacto é obrigatório.");
            }

            tipo = newTipo;
            valor = newValor.trim();
    }

    // Converte a entrada para o formato apresentado ao utilizador.
    @Override
    public String toString(){
        // Formato usado nas listagens e nas pesquisas apresentadas na consola.
        return String.format("%s: %s", tipo.toString(), valor);
    }

    // Devolve o valor guardado nesta entrada, como número ou endereço de mail.
    public String getValor(){
        return valor;
    }

    // Devolve o tipo associado a esta entrada.
    public TipoContacto getTipo(){
        return tipo;
    }

    // Verifica se o valor desta entrada contém o texto pesquisado.
    // A pesquisa não distingue maiúsculas de minúsculas.
    public boolean procurarEntradaParcial(String newValor){
        return valor.toLowerCase().contains(newValor.toLowerCase());
    }

    // Compara esta entrada com outra usando apenas o valor.
    // Permite detetar informação repetida mesmo quando o tipo é diferente.
    public boolean temMesmoValor(EntradaContacto outraEntrada) {
        // Compara apenas o valor, não o tipo.
        // Serve para detetar informação repetida mesmo que tenha sido classificada de forma diferente.
        return valor.equals(outraEntrada.getValor());
    }

    // Define a igualdade completa entre duas entradas de contacto.
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
    // Isto é importante se a classe for usada em coleções como HashSet ou HashMap.
    @Override
    public int hashCode() {
        // Como equals compara tipo e valor, o hashCode tem de usar os mesmos atributos.
        return java.util.Objects.hash(tipo, valor);
    }
}
