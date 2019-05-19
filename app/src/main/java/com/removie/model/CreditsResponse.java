package com.removie.model;

import java.util.List;

/*
Created by twin on May 11, 2019
*/

public class CreditsResponse {

    public List<Credits> cast;

    public CreditsResponse(List<Credits> cast) {
        this.cast = cast;
    }


    public List<Credits> getCast() {
        return cast;
    }
}
