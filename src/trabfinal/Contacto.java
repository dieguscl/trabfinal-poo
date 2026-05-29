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
public class Contacto {
    private final Identificacao identificacao;
    private final ArrayList<EntradaContacto> entradas;
    
    public Contacto(Identificacao newIdentificacao, EntradaContacto newEntradaContacto){
        identificacao = newIdentificacao;
        entradas = new ArrayList<>();
        entradas.add(newEntradaContacto);
    };
    
    @Override
    public String toString(){
        String s = identificacao.toString();
        for (int i = 0; i < entradas.size(); i++){
            s += String.format("\n%s", entradas.get(i).toString()); 
        }
        return s;
    }
    
    public void addEntradaContacto(EntradaContacto newEntradaContacto){
        // adicionar metodo de validacao de contactos
        entradas.add(newEntradaContacto);
    }
    
    // alterar para retornar objetos entrada
//    public String procurarInformacao(String newString){
//        String s = identificacao.toString();
//        
//        boolean encontrado = false;
//        
//        for(int i = 0; i < entradas.size(); i++){
//            if(entradas.get(i).getValor().contains(newString)){
//                s += "\n" + entradas.get(i).toString();
//                encontrado = true;
//            }
//        }
//        
//        if(encontrado){
//            return s;
//        }else{
//            return "";
//        }
//        
//    }
    
    public ArrayList<EntradaContacto> procurarInformacao(EntradaContacto newEntradaContacto){
        ArrayList<EntradaContacto> informacao = new ArrayList<>();
        for(int i = 0; i < entradas.size(); i++){
            if(entradas.get(i).equals(newEntradaContacto)){
                informacao.add(entradas.get(i));
            }
        }
        return informacao;
    }
    
    public boolean compararInformacao(Contacto newContacto){
        boolean temMesmaInformacao = false;
        
        for (int i = 0; i < newContacto.getEntradas().size(); i++){
            for (int j = 0; j < entradas.size(); j++){
                if (newContacto.getEntradas().get(i).getValor().equals(entradas.get(j).getValor())){
                    temMesmaInformacao = true;
                }
                
            }    
        }
        return temMesmaInformacao;
    }
    
    public ArrayList<EntradaContacto> getEntradas(){
        return entradas;
    }
    
    public Identificacao getIdentificacao(){
        return identificacao;
    }

    public boolean temInformacao(EntradaContacto entrada){
        for (int i = 0; i < entradas.size(); i++){
            if (entradas.get(i).getValor().equals(entrada.getValor())){
                return true;
            }
        }
        return false;
    }

    public void acrescentarInformacao(Contacto outro){
        for (int i = 0; i < outro.getEntradas().size(); i++){
            EntradaContacto entrada = outro.getEntradas().get(i);
            if (!temInformacao(entrada)){
                entradas.add(entrada);
            }
        }
    }

}
