package com.snjdigitalsolutions.cloudselfservicebackend.processgobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessStreamGobbler extends Thread{

    private final InputStream inputStream;
    private final String type;
    private final StringBuilder outputBuilder;

    public ProcessStreamGobbler(InputStream inputStream, String type) {
        this.inputStream = inputStream;
        this.type = type;
        this.outputBuilder = new StringBuilder();
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                outputBuilder.append("[").append(type).append("]").append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOutput() {
        return outputBuilder.toString();
    }


}
