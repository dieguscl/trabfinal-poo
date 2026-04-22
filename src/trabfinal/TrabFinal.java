/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabfinal;

/**
 *
 * @author luis
 */
public class TrabFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Identificacao pessoa1 = new Identificacao("João Silva", "ACME, Inc");
        
        System.out.println(pessoa1.toString());
        
        EntradaContacto cont1 = new EntradaContacto(TipoContacto.MAIL, "joao.silva@acme.com");
        
        System.out.println(cont1);
        
        EntradaContacto cont2 = new EntradaContacto(TipoContacto.TELEFONE, "913435679");
        
        System.out.println(cont2);
    }
    
}
