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
    TipoContacto tipo;
    String valor;
    
    public EntradaContacto(TipoContacto newTipo, String newValor){
            // validate(newNome);
            tipo = newTipo;
            // validade(newEmpresa);
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
    
    // comparar entrada: recebe string, retorna bool
    // faz match parcial entre entrada e string
    // parametro para fazer completo
    
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
}
