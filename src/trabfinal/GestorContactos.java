/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author luis
 */
public class GestorContactos {
    private final ArrayList<Contacto> listaContactos;
    public GestorContactos(){
        listaContactos = new ArrayList<>();
    };
    
    // adicionar função de importação
    
    // adicionar função exportação, se array de contactos no arg: parcial; se nada: base
    
    // Metodo Listar - Alterar para retornar Contactos
    // Obsoleto
//    public ArrayList<Contacto> listarContactos(){
//        String s = "";
//        for (int i = 0; i < listaContactos.size(); i++){
//            s += String.format("\n%d - %s", i+1, listaContactos.get(i).toString()); 
//        }
//        return s;
//    };
    
    // Metodo Acrescentar
    public void acrescentarContacto(Contacto contacto){
        // ver validações
        listaContactos.add(contacto);
    }
    
    //public void alterarContacto()
    
    // Remover Contacto
    
    public void removerContacto(int idx){
        // verificar se entre 1 e tamanho do Array
        listaContactos.remove(idx);
    }
    
    public ArrayList<Contacto> getListaContactos(){
        return new ArrayList<>(listaContactos);
    }
    
    public Contacto getContacto(int indice){
        if (indice < 0 || indice >= listaContactos.size()){
            throw new IllegalArgumentException("Índice inválido.");
        }
        return listaContactos.get(indice);
    }
    
    // Metodo Encontrar
    // alterar para retornar os objetos encontrados
//    public String encontrarContactos(String newString){
//        String s = "********************************\n*** Encontrar Contactos:";
//        String procura;
//        for (int i = 0; i < listaContactos.size(); i++){
//            procura = listaContactos.get(i).procurarInformacao(newString);
//            System.out.println(listaContactos.get(i));
//            if(!procura.equals("")){
//                s += String.format("\n%d - %s", i+1, procura);
//            }
//        }
//        return s;
//    }
    
    public ArrayList<Integer> encontrarInformacao(String newString){
        ArrayList<Integer> contactosEncontrados = new ArrayList<>();
        for (int i = 0; i < listaContactos.size(); i++){
            if (listaContactos.get(i).getIdentificacao().procurarIdentificacaoParcial(newString)){
                contactosEncontrados.add(i);
                continue;
            }
            ArrayList<EntradaContacto> entradasEncontradas = listaContactos.get(i).procurarInformacaoParcial(newString);
            if (!entradasEncontradas.isEmpty()) {
                contactosEncontrados.add(i);
            }
        }
        return contactosEncontrados;
    }
    
    public Contacto encontrarContactosRepetidos(Contacto newContacto){
        //Contacto contactoRepetido = new Contacto;
        //int idx = -1;
        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            if (contacto.getIdentificacao().equals(newContacto.getIdentificacao())){
                return contacto;
            }
        }
        return null;
    }
    
    
    public ArrayList<Contacto> encontrarInformacaoRepetidaContactos(Contacto newContacto){
        ArrayList<Contacto> listaContactosInfoRepetida = new ArrayList<>();
        
        for (int i = 0; i < listaContactos.size(); i++){
            if (newContacto.temAlgumaInformacaoIgual(listaContactos.get(i))){
                listaContactosInfoRepetida.add(listaContactos.get(i));
            }
        }
        return listaContactosInfoRepetida;
    }
    
    // Metodo Estatisticas
    public String estatisticas(){
        HashMap<TipoContacto, Integer> contagem = new HashMap<>();

        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            ArrayList<EntradaContacto> entradas = contacto.getEntradas();
            for (int j = 0; j < entradas.size(); j++) {
                TipoContacto tipo = entradas.get(j).getTipo();
                contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("********************************\n");
        sb.append("*** Estatísticas:");
        sb.append(String.format(
                "\nTelemóvel: %d\nTelefone: %d\nMail: %d",
                contagem.getOrDefault(TipoContacto.TELEMOVEL, 0),
                contagem.getOrDefault(TipoContacto.TELEFONE, 0),
                contagem.getOrDefault(TipoContacto.MAIL, 0)
        ));

        return sb.toString();
    }
}

