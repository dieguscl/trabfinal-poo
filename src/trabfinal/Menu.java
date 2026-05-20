/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;
import java.util.Scanner;

/**
 *
 * @author luis
 */
public class Menu {
    private GestorContactos gestor;
    
    // load ficheiro
    
    // iniciar menu base
    
    public Menu(GestorContactos newGestor){
        // gestor.load
        gestor = newGestor;
    }
    
    public void menuInicial(){
        String s = "1 - Listar Contactos\n";
        s += "2 - Acrescentar Contacto\n";
        s += "3 - Remover Contacto\n";
        s += "4 - Encontrar Contactos\n";
        s += "5 - Estatísticas\n";
        s += "6 - Sair";
        System.out.println(s);
        
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt() ){
            case 1 -> mostrarListaContactos();
            // case 2 -> "Telemóvel";
            // case 3 -> "Mail";
            // case 4 -> "Mail";
            //case 5 -> "Mail";
            // case 6 -> "Mail";
        }
        scanner.close();
        
    }
    
    
    private void mostrarListaContactos(){
        
        String listaContactos = gestor.listarContactos();
        System.out.println("********************************\n*** Listar Contactos:");
        Scanner scanner = new Scanner(System.in);
        
        if (listaContactos.equals("")){
            System.out.println("\nNão há contactos!\nPrima entrer para continuar!");
            
            scanner.nextLine();
                
        
        } else {
            System.out.println(gestor.listarContactos());
            System.out.println("Escrever em Ficheiro (S/N)?");
            
            switch(scanner.next("^[sSnN]$").toLowerCase()){
                case "s" -> System.out.println("Importar");
                case "n" -> System.out.println("Não importar");
            }
        }
        
        menuInicial();
    
    }
    
    // Acrescentar Contacto
//    private void acrescentarContacto(){
//        String nome;
//        
//        System.out.println("********************************\n*** Acrescentar Contactos\nIndique o nome do contacto:");
//        
//    }    
    // Encontrar Contacto
    
    // Remover Contacto
    
    // Estatistica
    
    // Sair
    
    
}
