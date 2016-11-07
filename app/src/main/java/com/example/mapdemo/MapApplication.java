package com.example.mapdemo;

import com.parse.Parse;
import com.parse.interceptors.ParseLogInterceptor;

import android.app.Application;

public class MapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // set applicationId and server based on the values in the Heroku settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("codepath-android") // should correspond to APP_ID env variable
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://codepath-maps-push-lab.herokuapp.com/parse/").build());
    }


}
