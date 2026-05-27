/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class GestorContactos {
    private final ArrayList<Contacto> listaContactos;
    public GestorContactos(){
        listaContactos = new ArrayList<>();
    };
    
    // Metodo Listar
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
    
    // alternativa
//    public Contact encontrarContactosReturnContact(String newString){
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
        int nTelemovel = 0;
        int nTelefone = 0;
        int nEmail = 0;
        Contacto contacto;
        
        for (int i = 0; i < listaContactos.size(); i++){
            contacto = listaContactos.get(i);
            for (int j = 0; j < contacto.getEntradas().size(); j++){
                switch (contacto.getEntradas().get(j).getTipo()) {
                    case TipoContacto.TELEFONE -> nTelefone +=1;
                    case TipoContacto.TELEMOVEL -> nTelemovel +=1;
                    case TipoContacto.MAIL -> nEmail +=1;
                    
                //if (contacto.getEntradas().get(j).getTipo().equals(TipoContacto.TELEFONE)){
                    
                }               
            }
        }
        return s + String.format("\nTelemóvel: %d\nTelefone: %d\nMail: %d", nTelemovel, nTelefone, nEmail);
    }
}

