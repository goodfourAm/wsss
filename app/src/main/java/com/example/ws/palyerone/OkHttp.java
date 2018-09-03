package com.example.ws.palyerone;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp {
    public static void sendOkHttp(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
