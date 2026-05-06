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
        String s = "********************************\n*** Listar Contactos:";
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
    
    // Remover Contacto
    
    public void removerContacto(int idx){
        // verificar se entre 1 e tamanho do Array
        listaContactos.remove(idx-1);
    }
    
    // Metodo Encontrar
    public String encontrarContactos(String newString){
        String s = "********************************\n*** Encontrar Contactos:";
        String procura;
        for (int i = 0; i < listaContactos.size(); i++){
            procura = listaContactos.get(i).procurarInformacao(newString);
            System.out.println(listaContactos.get(i));
            if(!procura.equals("")){
                s += String.format("\n%d - %s", i+1, procura);
            }
        }
        return s;
    }
    
    // Metodo Estatisticas
}
