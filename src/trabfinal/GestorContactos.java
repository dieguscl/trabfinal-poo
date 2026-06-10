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
    public String listarContactos(){
        String s = "";
        for (int i = 0; i < listaContactos.size(); i++){
            s += String.format("\n%d - %s", i+1, listaContactos.get(i).toString()); 
        }
        return s;
    };
    
    // Metodo Acrescentar
    public void acrescentarContacto(Contacto contacto){
        // ver validações
        listaContactos.add(contacto);
    }
    
    //public void alterarContacto()
    
    // Remover Contacto
    
    public void removerContacto(int idx){
        // verificar se entre 1 e tamanho do Array
        listaContactos.remove(idx-1);
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
    
    public HashMap<Integer, Contacto> encontrarInformacao(String newString){
        HashMap<Integer, Contacto> contactosEncontrados = new HashMap<>();
        for (int i = 0; i < listaContactos.size(); i++){
            if (listaContactos.get(i).getIdentificacao().procurarIdentificacaoParcial(newString)){
                contactosEncontrados.put(i+1, listaContactos.get(i));
                continue;
            }
            //EntradaContacto entrada = new EntradaContacto(TipoContacto.TELEFONE, newString);
            ArrayList <EntradaContacto> entradasEncontradas = listaContactos.get(i).procurarInformacaoParcial(newString);
            if(!entradasEncontradas.isEmpty()){
                Contacto newContacto = new Contacto(listaContactos.get(i).getIdentificacao(), entradasEncontradas.get(0));
                for (int j = 1; j < entradasEncontradas.size(); j++) {
                    newContacto.addEntradaContacto(entradasEncontradas.get(j));
                }
                contactosEncontrados.put(i+1, newContacto);
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
    
    
    // next step
    public ArrayList<Contacto> encontrarInformacaoRepetidaContactos(Contacto newContacto){
        ArrayList<Contacto> listaContactosInfoRepetida = new ArrayList<>();
        
        for (int i = 0; i < listaContactos.size(); i++){
            if (newContacto.compararInformacao(listaContactos.get(i))){
                listaContactosInfoRepetida.add(listaContactos.get(i));
            }
        }
        return listaContactosInfoRepetida;
    }
    
    // Metodo Estatisticas
    public String estatisticas(){
        String s = "********************************\n *** Estatísticas:";
        HashMap<TipoContacto, Integer> contagem = new HashMap<>();

        for (int i = 0; i < listaContactos.size(); i++){
            Contacto contacto = listaContactos.get(i);
            for (int j = 0; j < contacto.getEntradas().size(); j++){
                TipoContacto tipo = contacto.getEntradas().get(j).getTipo();
                contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
            }
        }

        return s + String.format("\nTelemóvel: %d\nTelefone: %d\nMail: %d",
                contagem.getOrDefault(TipoContacto.TELEMOVEL, 0),
                contagem.getOrDefault(TipoContacto.TELEFONE, 0),
                contagem.getOrDefault(TipoContacto.MAIL, 0));
    }
}

