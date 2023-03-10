package edu.eci.arep.app.services.impl;

import com.mongodb.client.*;
import org.bson.Document;
import edu.eci.arep.app.services.MongoDBService;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MongoDBServiceImpl implements MongoDBService {

    private static MongoDBServiceImpl instance;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private MongoDBServiceImpl(){
        this.mongoClient = MongoClients.create("mongodb://ec2-52-91-102-79.compute-1.amazonaws.com:27017/test");
        this.database = mongoClient.getDatabase("test");
        this.collection = database.getCollection("logs");
    }

    public static MongoDBServiceImpl getInstance() {
        if(instance == null){
            instance = new MongoDBServiceImpl();
        }
        return instance;
    }

    @Override
    public void createString(String string) {
        TimeZone zonaHoraria = TimeZone.getTimeZone("America/Bogota");
        Document document = new Document("Cadena", string).append("Fecha", LocalDateTime.now(zonaHoraria.toZoneId()));
        //collection.deleteMany(new Document());
        collection.insertOne(document);
    }

    @Override
    public JSONObject getStrings() {
        StringBuilder json = new StringBuilder();
        JSONObject jsonObject = new JSONObject();
        List<Document> allDocuments = collection.find()
                 .sort(new Document("Fecha", -1))
                 .limit(10)
                 .into(new ArrayList<>());

        for(Document document:allDocuments){
            Document newDocument = new Document();
            String id = document.remove("_id").toString();
            newDocument.append("Cadena", document.get("Cadena")).append("Fecha", document.get("Fecha").toString());
            jsonObject.put(id,newDocument.toJson());
        }
        return jsonObject;
    }
}
