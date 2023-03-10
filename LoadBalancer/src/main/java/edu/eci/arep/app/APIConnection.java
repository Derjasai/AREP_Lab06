package edu.eci.arep.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.net.HttpURLConnection;

public class APIConnection {
    public static String get(String url) throws Exception {

        // Crear objeto URL y HttpURLConnection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Establecer método GET
        con.setRequestMethod("GET");

        // Leer respuesta
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Imprimir respuesta
        return response.toString();
    }
}
