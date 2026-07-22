package trabfinal;

/**
 * Guarda a identificação principal de um contacto.
 * O nome é obrigatório. A empresa é opcional e fica vazia quando não é indicada.
 * A classe também define como duas identificações são comparadas.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class Identificacao {
        private final String nome;
        private final String empresa;

        public Identificacao(String newNome, String newEmpresa){
            // A validação fica concentrada nestes métodos privados para o construtor ficar mais legível.
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
            // Se houver empresa, a identificação ocupa duas linhas; caso contrário, mostra só o nome.
            if (!empresa.equals("")){
               return String.format("%s\n%s", nome, empresa);
            }
            else{
                return nome;
            }
        }

        // Nome e empresa têm de coincidir para serem consideradas iguais.
        @Override
        public boolean equals(Object obj){
            // Duas identificações são iguais apenas quando nome e empresa coincidem.
            if (this == obj){
                return true;
            }
            if (obj == null || getClass() != obj.getClass()){
                return false;
            }
            Identificacao other = (Identificacao) obj;
            return nome.equals(other.nome) && empresa.equals(other.empresa);
        }

        // Calcula o código hash usando os mesmos campos usados no equals.
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

        // Procura texto no nome ou na empresa desta identificação.
        // A pesquisa não distingue maiúsculas de minúsculas.
        public boolean procurarIdentificacaoParcial(String newIdentificacao){
            return nome.toLowerCase().contains(newIdentificacao.toLowerCase()) || empresa.toLowerCase().contains(newIdentificacao.toLowerCase());
        }

}
