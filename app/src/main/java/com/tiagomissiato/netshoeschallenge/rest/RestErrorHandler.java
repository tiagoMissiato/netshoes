package com.tiagomissiato.netshoeschallenge.rest;

import android.content.Context;

import com.tiagomissiato.netshoeschallenge.R;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by trigoleto on 7/3/15.
 * t.m.rigoleto@gmail.com
 */
public class RestErrorHandler implements ErrorHandler {

    Context mContext;

    public RestErrorHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        if(cause.getKind() == RetrofitError.Kind.NETWORK){
            return new RestException(mContext.getString(R.string.no_connection_error));
        } else if((int) Math.floor(cause.getResponse().getStatus() / 100.00) == 4){
            return new RestException(mContext.getString(R.string.server_error));
        } else if((int) Math.floor(cause.getResponse().getStatus() / 100.00) == 5){
            return new RestException(mContext.getString(R.string.internal_error));
        } else {
            return cause;
        }
    }
}
