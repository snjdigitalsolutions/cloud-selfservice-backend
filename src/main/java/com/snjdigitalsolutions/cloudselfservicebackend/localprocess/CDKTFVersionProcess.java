package com.snjdigitalsolutions.cloudselfservicebackend.localprocess;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CDKTFVersionProcess extends AbstractLocalProcess {

    public CDKTFVersionProcess(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public void defineAndStartProcess() {
        ProcessBuilder builder = new ProcessBuilder("cdktf", "--version");
        this.startProcess(builder);
    }

    @Override
    public void generateResponse() {
        if (exitCode == 0) {
            Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+");
            Matcher matcher = pattern.matcher(gobblerUtility.getStdOut());
            if (matcher.find()) {
                response = matcher.group();
            }
        } else {
            response = "";
        }
    }
}
