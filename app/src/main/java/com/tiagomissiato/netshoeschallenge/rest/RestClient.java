package com.tiagomissiato.netshoeschallenge.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.tiagomissiato.netshoeschallenge.utils.GistFileDeserializer;
import com.tiagomissiato.netshoeschallenge.models.Gist;
import com.tiagomissiato.netshoeschallenge.models.GisFileObj;
import com.tiagomissiato.netshoeschallenge.rest.callbacks.RestCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by tiagomissiato on 6/28/15.
 */
public class RestClient {

    private static final String END_POINT = "https://api.github.com";

    private static ClientAPI REST_CLIENT;


    public static ClientAPI get() {
        return REST_CLIENT;
    }

    private static void setupRestClient(Context context, Gson gson) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new RestErrorHandler(context))
                .setClient(new OkClient(new OkHttpClient()));

        if(gson != null)
            builder.setConverter(new GsonConverter(gson));

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(ClientAPI.class);
    }

    public static void getGist(Context context, int page, RestCallback<List<Gist>> callback){
        Type listType = new TypeToken<ArrayList<Gist>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(listType, new DateTypeAdapter())
                .registerTypeAdapter(GisFileObj.class, new GistFileDeserializer())
                .create();

        setupRestClient(context, gson);
        RestClient.get().getGists(page, callback);
    }
}
