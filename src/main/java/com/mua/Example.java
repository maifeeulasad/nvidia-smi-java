package com.mua;

import org.json.JSONObject;

public class Example {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // single shot
        NvidiaSMIWrapper.executeNvidiaSMI(new NvidiaSMIWrapper.Callback() {
            @Override
            public void onSuccess(JSONObject data) {
                System.out.println(data);
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println(errorMessage);
            }
        });

        // periodic
        NvidiaSMIWrapper.executeNvidiaSMI(new NvidiaSMIWrapper.Callback() {
            @Override
            public void onSuccess(JSONObject data) {
                System.out.println(data);
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println(errorMessage);
            }
        }, 1);
    }
}