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
        
        Identificacao pessoa2 = new Identificacao("Joana Fontes","");
        
        // System.out.println(pessoa1.toString());
        
        EntradaContacto cont1 = new EntradaContacto(TipoContacto.MAIL, "joao.silva@acme.com");
        
        // System.out.println(cont1);
        
        EntradaContacto cont2 = new EntradaContacto(TipoContacto.TELEFONE, "913435679");
        
        // System.out.println(cont2);
        
        Contacto contTest1 = new Contacto(pessoa1, cont1);
        System.out.println(contTest1);
        
        Contacto contTest2 = new Contacto(pessoa2, cont2);
        EntradaContacto cont3 = new EntradaContacto(TipoContacto.MAIL, "fontesjoana@personal.com");
        EntradaContacto cont4 = new EntradaContacto(TipoContacto.MAIL, "joanasfontes@newmail.pt");
        contTest2.addEntradaContacto(cont3);
        contTest2.addEntradaContacto(cont4);
        
        Contacto contTest3 = new Contacto(new Identificacao("Jonas Carneiro", ""), new EntradaContacto(TipoContacto.TELEMOVEL, "912334455"));

        System.out.println(contTest2);
        
        
        
        GestorContactos newGestorContactos = new GestorContactos();
        newGestorContactos.acrescentarContacto(contTest2);
        newGestorContactos.acrescentarContacto(contTest1);
        newGestorContactos.acrescentarContacto(contTest3);
        //newGestorContactos.removerContacto(1);
        //System.out.println(contTest2.procurarInformacao("joana"));
        
        
        //System.out.println(newGestorContactos.encontrarContactos("joana"));
        
        System.out.println(contTest1.getEntradas().toString());
        System.out.println(contTest2.getEntradas().toString());
        
        System.out.println(newGestorContactos.estatisticas());
        
        Menu menu = new Menu(newGestorContactos);
        menu.menuInicial();
    }
    
    
            
    
}
