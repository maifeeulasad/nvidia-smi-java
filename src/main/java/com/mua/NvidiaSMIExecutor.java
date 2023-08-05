package com.mua;

import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A utility class to execute the 'nvidia-smi' command and retrieve NVIDIA System Management Interface (nvidia-smi) data.
 * The data is parsed from XML format to JSONObject format using the 'org.json' library.
 * This class provides two methods for execution:
 * - executeNvidiaSMI(Callback callback): Executes the 'nvidia-smi' command and provides the result via the provided callback.
 * - executeNvidiaSMI(Callback callback, int delaySecond): Continuously executes the 'nvidia-smi' command at a specified interval (*second*)
 * and provides the result via the provided callback.
 */
public class NvidiaSMIExecutor {

    /**
     * Continuously executes the 'nvidia-smi' command at a specified interval and provides the result via the provided callback.
     *
     * @param callback    The callback interface to handle the results of the 'nvidia-smi' command execution.
     * @param delaySecond The interval in seconds between each 'nvidia-smi' command execution.
     */
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

    /**
     * Executes the 'nvidia-smi' command and provides the result via the provided callback.
     *
     * @param callback The callback interface to handle the results of the 'nvidia-smi' command execution.
     */
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

    /**
     * The callback interface to handle the results of the 'nvidia-smi' command execution.
     * Implement this interface to receive the results of 'nvidia-smi' command execution.
     */
    interface Callback {

        /**
         * Called when the 'nvidia-smi' command is successfully executed and data is retrieved.
         *
         * @param data The JSON data representing the result of the 'nvidia-smi' command execution.
         */
        void onSuccess(JSONObject data);

        /**
         * Called when an error occurs during the execution of the 'nvidia-smi' command.
         *
         * @param errorMessage The error message describing the reason for the failure.
         */
        void onError(String errorMessage);
    }
}
