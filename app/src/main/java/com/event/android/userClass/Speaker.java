package com.event.android.userClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Speaker {

    @SerializedName("id")
    @Expose
    private Integer id;

    public Speaker(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
