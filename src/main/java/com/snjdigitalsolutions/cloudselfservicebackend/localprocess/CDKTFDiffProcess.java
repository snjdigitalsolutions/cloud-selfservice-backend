package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

import com.snjdigitalsolutions.cloudselfservicebackend.utility.AddChangeDestroyUtility;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CDKTFDiffProcess extends AbstractLocalProcess {

    private final AddChangeDestroyUtility addChangeDestroyUtility;

    public CDKTFDiffProcess(ApplicationContext applicationContext, AddChangeDestroyUtility addChangeDestroyUtility) {
        super(applicationContext);
        this.addChangeDestroyUtility = addChangeDestroyUtility;
    }

    @Override
    public void defineAndStartProcess() {
        ProcessBuilder builder = new ProcessBuilder("cdktf", "diff");
        this.startProcess(builder);
    }

    @Override
    public void generateResponse() {
        if (exitCode == 0) {
            Optional<String> changes = addChangeDestroyUtility.matchLine(gobblerUtility.getStdOut());
            changes.ifPresent(s -> response = s);
        } else {
            response = "";
        }
    }
}
