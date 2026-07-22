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
        };
    }
}
