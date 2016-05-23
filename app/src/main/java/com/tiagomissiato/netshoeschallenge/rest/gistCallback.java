package com.tiagomissiato.netshoeschallenge.rest;

import android.content.Context;

import com.tiagomissiato.netshoeschallenge.models.Gist;
import com.tiagomissiato.netshoeschallenge.rest.callbacks.RestCallback;

import java.util.List;

public class gistCallback {
    private final Context context;
    private final int page;
    private final RestCallback<List<Gist>> callback;

    public gistCallback(Context context, int page, RestCallback<List<Gist>> callback) {
        this.context = context;
        this.page = page;
        this.callback = callback;
    }

    public Context getContext() {
        return context;
    }

    public int getPage() {
        return page;
    }

    public RestCallback<List<Gist>> getCallback() {
        return callback;
    }
}
