/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

/**
 *
 * @author luis
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
            valor = newValor;
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
    
    // parametro para fazer completo
    public boolean procurarEntradaParcial(String newValor){
        return valor.toLowerCase().contains(newValor.toLowerCase());
    }
    
    public boolean temMesmoValor(EntradaContacto outraEntrada) {
        return valor.equals(outraEntrada.getValor());
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        EntradaContacto other = (EntradaContacto) obj;
        return tipo.equals(other.tipo) && valor.equals(other.valor);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(tipo, valor);
    }
}
