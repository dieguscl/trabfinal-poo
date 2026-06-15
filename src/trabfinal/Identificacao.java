package trabfinal;

/**
 * Guarda a identificação principal de um contacto.
 * O nome é obrigatório. A empresa é opcional e fica vazia quando não é indicada.
 * A classe também define como duas identificações são comparadas.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public class Identificacao {
        // A identificação é private; depois de criada, mantém o mesmo nome e empresa.
        private final String nome;
        private final String empresa;

        // Cria a identificação principal de um contacto.
        // O nome é obrigatório e a empresa pode ficar vazia.
        public Identificacao(String newNome, String newEmpresa){
            // A validação fica concentrada nestes métodos privados para o construtor ficar mais legível.
            nome = validarNome(newNome);
            empresa = validarEmpresa(newEmpresa);
        }

        // Valida e normaliza o nome recebido no construtor.
        // Lança erro se o nome estiver vazio, porque o contacto precisa de identificação.
        private String validarNome(String nome){
            // O nome é o único campo obrigatório da identificação.
            if (nome == null || nome.trim().equals("")){
                throw new IllegalArgumentException("O nome é obrigatório.");
            }
            return nome.trim();
        }

        // Valida e normaliza a empresa recebida no construtor.
        // Como é opcional, null é convertido numa String vazia.
        private String validarEmpresa(String empresa){
            // Empresa nula é tratada como texto vazio porque a empresa é opcional.
            if (empresa == null){
                return "";
            }
            return empresa.trim();
        }

        // Converte a identificação para texto, com nome e empresa quando existir.
        // Este formato é usado nas listagens da agenda.
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

        // Define quando duas identificações representam o mesmo contacto.
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
            // Mantém coerência com o equals: usa exatamente os campos comparados no equals.
            return java.util.Objects.hash(nome, empresa);
        }

        // Devolve o nome guardado na identificação.
        public String getNome(){
            return nome;
        }

        // Devolve a empresa guardada, ou String vazia se não existir.
        public String getEmpresa(){
            return empresa;
        }

        // Procura texto no nome ou na empresa desta identificação.
        // A pesquisa não distingue maiúsculas de minúsculas.
        public boolean procurarIdentificacaoParcial(String newIdentificacao){
            // Pesquisa o texto no nome e na empresa, sem distinguir maiúsculas de minúsculas.
            return nome.toLowerCase().contains(newIdentificacao.toLowerCase()) || empresa.toLowerCase().contains(newIdentificacao.toLowerCase());
        }

}
