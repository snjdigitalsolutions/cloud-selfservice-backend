package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

public interface LocalProcess {

    void performProcess();

    void startProcess(ProcessBuilder builder);

    void defineAndStartProcess();

    String getResponse();

    int getExitCode();

    String getStdOut();

    String getStdErr();

    void generateResponse();

}
