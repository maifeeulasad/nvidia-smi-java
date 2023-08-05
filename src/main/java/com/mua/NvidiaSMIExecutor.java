package com.mua;

import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NvidiaSMIExecutor {

    public static void executeNvidiaSMI(Callback callback, int delaySecond) {
        new Thread(() -> {
            try {
                while (true) {
                    executeNvidiaSMI(new Callback() {
                        @Override
                        public void onSuccess(JSONObject data) {
                            callback.onSuccess(data);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            callback.onError(errorMessage);
                        }
                    });

                    Thread.sleep(delaySecond * 1000L);
                }
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public static void executeNvidiaSMI(Callback callback) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("nvidia-smi", "-q", "-x");
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                callback.onError("nvidia-smi command failed with exit code: " + exitCode);
            } else {
                JSONObject jsonData = XML.toJSONObject(output.toString());

                callback.onSuccess(jsonData);
            }
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    interface Callback {
        void onSuccess(JSONObject data);

        void onError(String errorMessage);
    }
}
