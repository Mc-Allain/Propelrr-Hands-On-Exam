package com.example.propelrrhandsonexam;

import java.util.ArrayList;
import java.util.List;

public class Responses {

    private List<Response> responseList = new ArrayList<>();

    public Responses() {
    }

    public Responses(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<Response> getResponseList() {
        return responseList;
    }
}
