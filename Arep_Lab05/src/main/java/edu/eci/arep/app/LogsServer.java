package edu.eci.arep.app;

import edu.eci.arep.app.services.MongoDBService;
import edu.eci.arep.app.services.impl.MongoDBServiceImpl;

import static spark.Spark.*;

public class LogsServer {

    private static MongoDBService mongoDBService = MongoDBServiceImpl.getInstance();

    public static void main(String... args){
        port(getPort());

        get("logs/:cadena",(req,res) -> {
            res.header("Content-Type","application/json");
            String cadena = req.params("cadena");
            mongoDBService.createString(cadena);
            return mongoDBService.getStrings();
        });

        get("hello",(req,res) -> {
            return "Funciona";
        });

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
