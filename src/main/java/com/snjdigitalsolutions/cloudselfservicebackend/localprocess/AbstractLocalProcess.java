package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

import com.snjdigitalsolutions.cloudselfservicebackend.processgobbler.GobblerUtility;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;

@NoArgsConstructor
public abstract class AbstractLocalProcess implements LocalProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLocalProcess.class);
    @Value("${cdktf.project.directory}")
    private String projectDirectory;
    @Value("${cdktf.use.specified.directory}")
    private boolean useSpecifiedDirectory;
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
            if (useSpecifiedDirectory) {
                builder.directory(new File(projectDirectory));
            }
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
