package com.tiagomissiato.netshoeschallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class Owner {

    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatar;

}
