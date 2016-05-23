package com.tiagomissiato.netshoeschallenge.rest;

/**
 * Created by trigoleto on 7/3/15.
 * t.m.rigoleto@gmail.com
 */
public class RestException extends Exception {

    public RestException() {
    }

    public RestException(String detailMessage) {
        super(detailMessage);
    }
}
