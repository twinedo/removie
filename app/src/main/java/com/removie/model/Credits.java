package com.removie.model;

import com.google.gson.annotations.SerializedName;

/*
Created by twin on May 11, 2019
*/

public class Credits {

    @SerializedName("id")
    public String mId;

    @SerializedName("profile_path")
    public final String profilePath;

    @SerializedName("name")
    public final String name;

    @SerializedName("character")
    public final String character;

    public String getProfilePath() {
        return profilePath;
    }
    public String getName() {
        return name;
    }
    public String getCharacter() {
            return character;
        }

    public Credits(String mId, String profilePath, String name, String character) {
        this.mId = mId;
        this.profilePath = profilePath;
        this.name = name;
        this.character = character;
    }
}
