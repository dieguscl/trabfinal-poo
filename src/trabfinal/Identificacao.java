/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

/**
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class Identificacao {
        private final String nome;
        private final String empresa;
        
        public Identificacao(String newNome, String newEmpresa){
            nome = validarNome(newNome);
            empresa = validarEmpresa(newEmpresa);
        }

        private String validarNome(String nome){
            if (nome == null || nome.trim().equals("")){
                throw new IllegalArgumentException("O nome é obrigatório.");
            }
            return nome.trim();
        }

        private String validarEmpresa(String empresa){
            if (empresa == null){
                return "";
            }
            return empresa.trim();
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
        
        public String getNome(){
            return nome;
        }
        
        public String getEmpresa(){
            return empresa;
        }
        
        
        public boolean procurarIdentificacaoParcial(String newIdentificacao){
            return nome.toLowerCase().contains(newIdentificacao.toLowerCase()) || empresa.toLowerCase().contains(newIdentificacao.toLowerCase());
        }

}
