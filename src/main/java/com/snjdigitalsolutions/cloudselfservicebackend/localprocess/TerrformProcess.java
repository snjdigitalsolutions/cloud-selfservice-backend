package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

public interface TerrformProcess {

    void executeProcess();

    String getResponse();

    int getExitCode();

    String getStdOut();

    String getStdErr();

    void generateResponse();

}
