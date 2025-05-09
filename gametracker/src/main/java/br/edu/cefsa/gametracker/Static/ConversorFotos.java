package br.edu.cefsa.gametracker.Static;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

//Conversor de fotos
public class ConversorFotos {

    public static String ConverterBase64(byte[] foto){

        String base64 = java.util.Base64.getEncoder().encodeToString(foto);

        return base64;
    }

    public static byte[] ConversorByte(FileInputStream foto) throws IOException{
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesLidos;

        
        while((bytesLidos = foto.read(buffer)) != -1){
            baos.write(buffer, 0, bytesLidos);

        }
        return baos.toByteArray();
    }

      
}
