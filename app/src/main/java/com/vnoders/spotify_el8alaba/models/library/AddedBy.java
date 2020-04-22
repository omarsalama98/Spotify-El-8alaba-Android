
package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class AddedBy {

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("uri")
    private String uri;

    @SerializedName("id")
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AddedBy addedBy = (AddedBy) obj;
        return Objects.equals(type, addedBy.type) &&
                Objects.equals(name, addedBy.name) &&
                Objects.equals(uri, addedBy.uri) &&
                Objects.equals(id, addedBy.id);
    }

}
