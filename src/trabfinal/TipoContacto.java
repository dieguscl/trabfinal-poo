package trabfinal;

/**
 * Define os tipos de entrada aceites pela agenda.
 * Usar um enum evita representar o tipo com números ou textos soltos ao longo do código.
 *
 * @author Diego Laya (2025154378), Luis Junqueira (2025168125)
 */
public enum TipoContacto{ TELEFONE, TELEMOVEL, MAIL;

    // Converte a opção numérica introduzida no menu para um TipoContacto.
    // Devolve null quando a opção não corresponde a nenhum tipo válido.
    public static TipoContacto fromInt(int i) {
        // Converte a opção numérica do menu no valor do enum usado pelo resto do programa.
        // Se a opção não existir, devolve null para o menu poder pedir novamente.
    	return switch (i) {
	     case 1 -> TELEFONE;
	     case 2 -> TELEMOVEL;
	     case 3 -> MAIL;
	     default -> null;
	};
    }

    // Converte o valor do enum para o texto apresentado ao utilizador.
    // Evita mostrar diretamente TELEFONE, TELEMOVEL e MAIL.
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
