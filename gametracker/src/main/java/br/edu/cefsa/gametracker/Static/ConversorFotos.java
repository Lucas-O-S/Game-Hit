package br.edu.cefsa.gametracker.Static;


//Conversor de fotos
public class ConversorFotos {

    public static String ConverterBase64(byte[] foto){

        String base64 = java.util.Base64.getEncoder().encodeToString(foto);

        return base64;
    }

      
}
