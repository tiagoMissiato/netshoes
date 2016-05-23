package com.tiagomissiato.netshoeschallenge.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class Owner implements Serializable {

    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatar;

}
