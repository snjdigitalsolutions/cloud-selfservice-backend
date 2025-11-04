package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

import com.snjdigitalsolutions.cloudselfservicebackend.processgobbler.GobblerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class TerraformVersionProcess implements TerrformProcess {

    private final ApplicationContext applicationContext;
    private GobblerUtility gobblerUtility;
    private String response;

    private static final Logger LOGGER = LoggerFactory.getLogger(TerraformVersionProcess.class);
    private int exitCode;

    public TerraformVersionProcess(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void executeProcess() {
        ProcessBuilder builder = new ProcessBuilder("terraform", "-version");
        try {
            Process process = builder.start();
            gobblerUtility = applicationContext.getBean(GobblerUtility.class);
            gobblerUtility.startGobbler(process);
            exitCode = process.waitFor();
            gobblerUtility.joinGobbler();
        } catch (Exception e) {
            LOGGER.error("Error executing terraform version process.");
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void generateResponse() {
        if (exitCode == 0) {
            Pattern pattern = Pattern.compile("Terraform v([0-9]+\\.[0-9]+\\.[0-9]+)");
            Matcher matcher = pattern.matcher(gobblerUtility.getStdOut());
            if (matcher.find()) {
                String version = matcher.group();
                String[] splitVersion = version.split(" ");
                response = splitVersion[1];
            }
        } else {
            response = "";
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
