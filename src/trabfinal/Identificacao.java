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
            if (!empresa.equals("")){
               return String.format("%s\n%s", nome, empresa); 
            }
            else{
                return nome;
            }
        }
        
        @Override
        public boolean equals(Object obj){
            if (this == obj){
                return true;
            }
            if (obj == null || getClass() != obj.getClass()){
                return false;
            }
            Identificacao other = (Identificacao) obj;
            return nome.equals(other.nome) && empresa.equals(other.empresa);
        }

        @Override
        public int hashCode(){
            return java.util.Objects.hash(nome, empresa);
        }

}
