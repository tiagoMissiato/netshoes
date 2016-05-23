package com.tiagomissiato.netshoeschallenge.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiagomissiato on 6/29/15.
 */
public class RestError implements Serializable {

    public enum ERROR_CODE {
        NO_CONNECTION(-1);

        public int code;

        ERROR_CODE(int code){this.code = code;}
    }

    @SerializedName("code")
    private Integer code;

    @SerializedName("error_message")
    private String strMessage;

    public RestError(String strMessage)
    {
        this.strMessage = strMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }
}
