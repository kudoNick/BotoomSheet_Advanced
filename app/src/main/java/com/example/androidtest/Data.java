package com.example.androidtest;

import org.json.JSONException;
import org.json.JSONObject;

class Data {

    String name,image,comment;

    public Data() {
    }

    public Data(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("name")) {
            name = jsonObject.getString("name");
        }
        if (jsonObject.has("image")) {
            image = jsonObject.getString("image");
        }
        if (jsonObject.has("comment")) {
            comment = jsonObject.getString("comment");
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
