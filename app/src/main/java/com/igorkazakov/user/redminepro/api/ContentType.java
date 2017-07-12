package com.igorkazakov.user.redminepro.api;

/**
 * Created by user on 11.07.17.
 */

public enum ContentType {

    JSON("application/json");

    private String value;

    private ContentType(String string){
        this.value = string;
    }

    public String getValue(){
        return  this.value;
    }
}
