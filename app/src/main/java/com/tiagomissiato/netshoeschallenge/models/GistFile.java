package com.tiagomissiato.netshoeschallenge.models;

import java.io.Serializable;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class GistFile implements Serializable {

    public String filename;
    public String type;

    public String language;
    public String raw_url;
    public int size;
}
