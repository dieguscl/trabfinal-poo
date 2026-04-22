/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

/**
 *
 * @author luis
 */
public class Identificacao {
        private String nome;
        private String empresa;
        
        public Identificacao(String newNome, String newEmpresa){
            // validate(newNome);
            nome = newNome;
            // validade(newEmpresa);
            empresa = newEmpresa;
        }
        
        @Override
        public String toString(){
            return String.format("nome: %s\nempresa: %s", nome, empresa);
        }
    
}
