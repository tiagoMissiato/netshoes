package com.tiagomissiato.netshoeschallenge.rest.callbacks;

import com.tiagomissiato.netshoeschallenge.rest.models.RestError;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by tiagomissiato on 6/29/15.
 */
public abstract class RestCallback<T> implements Callback<T> {
    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError != null) {
            failure(restError);
        } else {
            failure(new RestError(error.getMessage()));
        }
    }
}
