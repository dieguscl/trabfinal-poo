/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabfinal;

enum TipoContacto{ TELEFONE, TELEMOVEL, MAIL;
    @Override
    public String toString(){
        return switch (this) {
            case TELEFONE -> "Telefone";
            case TELEMOVEL -> "Telemóvel";
            case MAIL -> "Mail";
            default -> "";
        };
    }
}

/**
 *
 * @author luis
 */
public class EntradaContacto {
    TipoContacto tipo;
    String valor;
    
    public EntradaContacto(TipoContacto newTipo, String newValor){
            // validate(newNome);
            tipo = newTipo;
            // validade(newEmpresa);
            valor = newValor;
    }
    
    @Override
    public String toString(){
        return String.format("%s: %s", tipo.toString(), valor);
    }
}
