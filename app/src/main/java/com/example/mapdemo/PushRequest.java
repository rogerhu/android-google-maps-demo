package com.example.mapdemo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class PushRequest {

    String markerId;
    String title;
    String userId;
    String snippet = "";
    LatLng mapLocation;

    public PushRequest(JSONObject data) throws JSONException {
        JSONObject location = data.getJSONObject("location");
        markerId = data.getString("markerId");
        title = data.getString("title");
        snippet = data.optString("snippet", "");
        userId = data.getString("userId");

        mapLocation = new LatLng(location.getDouble("lat"),
                location.getDouble("long"));

    }
}