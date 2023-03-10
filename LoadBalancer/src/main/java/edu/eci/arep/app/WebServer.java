package edu.eci.arep.app;

import edu.eci.arep.app.loadBalancers.LoadBalancer;
import edu.eci.arep.app.loadBalancers.impl.RoundRobin;

import java.net.MalformedURLException;
import java.net.URL;

import static spark.Spark.get;
import static spark.Spark.port;

public class WebServer {

    private static LoadBalancer loadBalancer = RoundRobin.getInstance();

    public static void main(String... args){
        port(getPort());

        get("logs/:cadena",(req,res) -> {
            res.header("Content-Type","application/json");
            String cadena = req.params("cadena");
            return APIConnection.get(loadBalancer.getServer() + "/logs/" + cadena);
        });

        get("/", (res,req)-> {
            return index();
        });
    }

    private static String index(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Form Example</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Form with GET</h1>\n" +
                "    <form action=\"/hello\">\n" +
                "        <label for=\"newLog\">Name:</label><br>\n" +
                "        <input type=\"text\" id=\"newLog\" name=\"newLog\" value=\"John\"><br><br>\n" +
                "        <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "    </form>\n" +
                "    <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "    <script>\n" +
                "        function loadGetMsg() {\n" +
                "            let nameVar = document.getElementById(\"newLog\").value;\n" +
                "            const xhttp = new XMLHttpRequest();\n" +
                "            xhttp.onload = function() {\n" +
                "                document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                this.responseText;\n" +
                "            }\n" +
                "            xhttp.open(\"GET\", \"/logs/\"+nameVar);\n" +
                "            xhttp.send();\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

}
