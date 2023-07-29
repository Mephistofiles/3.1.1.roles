package com.mcdadork.springbootcrud.model;

import java.util.Map;

public class RequestWrapper {
    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
