package com.tiagomissiato.netshoeschallenge.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiagomissiato.netshoeschallenge.models.GistFile;
import com.tiagomissiato.netshoeschallenge.models.GisFileObj;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class GistFileDeserializer implements JsonDeserializer<GisFileObj> {

    @Override
    public GisFileObj deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = (JsonObject) json;

        GisFileObj obj = new GisFileObj();
        obj.gists = new ArrayList<>();

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry: entries) {
            JsonObject gistFileObject = (JsonObject) entry.getValue();
            GistFile file = new GistFile();
            file.filename = getStrValue(gistFileObject, "filename");
            file.type = getStrValue(gistFileObject, "type");
            file.language = getStrValue(gistFileObject, "language");
            file.raw_url = getStrValue(gistFileObject, "raw_url");
            file.size = gistFileObject.get("size").getAsInt();
            obj.gists.add(file);
        }

        return obj;
    }

    public String getStrValue(JsonObject obj, String key){
        //Some times the value is null so, with this function an empty string is returned
        try{
            return obj.get(key).getAsString();
        }catch (Exception e) {
            return "";
        }
    }
}
