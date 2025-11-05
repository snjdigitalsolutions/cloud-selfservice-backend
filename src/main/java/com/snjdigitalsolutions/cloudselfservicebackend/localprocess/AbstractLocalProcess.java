package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

import com.snjdigitalsolutions.cloudselfservicebackend.processgobbler.GobblerUtility;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public abstract class AbstractLocalProcess implements LocalProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLocalProcess.class);
    private ApplicationContext applicationContext = null;
    protected GobblerUtility gobblerUtility;
    protected int exitCode;
    protected String response;

    protected AbstractLocalProcess(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void performProcess() {
        defineAndStartProcess();
        generateResponse();
    }

    @Override
    public void startProcess(ProcessBuilder builder){
        try {
            Process process = builder.start();
            gobblerUtility = applicationContext.getBean(GobblerUtility.class);
            gobblerUtility.startGobbler(process);
            exitCode = process.waitFor();
            gobblerUtility.joinGobbler();
        } catch (Exception e) {
            LOGGER.error("Error executing process.");
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    @Override
    public String getStdOut() {
        return gobblerUtility.getStdOut();
    }

    @Override
    public String getStdErr() {
        return gobblerUtility.getStdErr();
    }

    @Override
    public String getResponse() {
        return response;
    }
}
