package com.tiagomissiato.netshoeschallenge.rest;

import com.tiagomissiato.netshoeschallenge.models.Gist;
import com.tiagomissiato.netshoeschallenge.rest.callbacks.RestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by tiagomissiato on 6/28/15.
 */
public interface ClientAPI {

    @GET("/gists/public")
    void getGists(@Query("page") int page, RestCallback<List<Gist>> bills);

}
