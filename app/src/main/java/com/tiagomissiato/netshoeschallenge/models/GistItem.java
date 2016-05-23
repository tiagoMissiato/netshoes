package com.tiagomissiato.netshoeschallenge.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class GistItem implements Serializable{

    public Owner owner;
    public GistFile gist;
}
