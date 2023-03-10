package edu.eci.arep.app.services;

import org.bson.json.JsonObject;
import org.json.JSONObject;

public interface MongoDBService {

    void createString(String string);

    JSONObject getStrings();

}
