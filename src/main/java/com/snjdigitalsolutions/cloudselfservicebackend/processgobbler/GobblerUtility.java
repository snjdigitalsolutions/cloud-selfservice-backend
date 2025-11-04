package com.snjdigitalsolutions.cloudselfservicebackend.processgobbler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GobblerUtility {

    private ProcessStreamGobbler outputGobbler;
    private ProcessStreamGobbler errorGobbler;

    public void startGobbler(Process process) {
        outputGobbler = new ProcessStreamGobbler(process.getInputStream(), "OUTPUT");
        errorGobbler = new ProcessStreamGobbler(process.getErrorStream(), "ERROR");
        outputGobbler.start();
        errorGobbler.start();
    }

    public void joinGobbler() throws InterruptedException {
        outputGobbler.join();
        errorGobbler.join();
    }

    public String getStdOut() {
        return outputGobbler.getOutput();
    }

    public String getStdErr() {
        return errorGobbler.getOutput();
    }
}
