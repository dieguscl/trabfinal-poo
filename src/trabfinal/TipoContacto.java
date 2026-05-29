/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package trabfinal;

/**
 *
 * @author diegus
 */
public enum TipoContacto{ TELEFONE, TELEMOVEL, MAIL;

    public static TipoContacto fromInt(int i) {
    	return switch (i) {
	     case 1 -> TELEFONE;
	     case 2 -> TELEMOVEL;
	     case 3 -> MAIL;
	     default -> null;
	};
    }
    
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
