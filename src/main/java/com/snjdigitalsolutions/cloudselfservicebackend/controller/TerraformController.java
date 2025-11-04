package com.snjdigitalsolutions.cloudselfservicebackend.controller;

import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.TerraformVersionProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.response.ProcessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TerraformController {

    private final TerraformVersionProcess terraformVersionProcess;

    public TerraformController(TerraformVersionProcess terraformVersionProcess) {
        this.terraformVersionProcess = terraformVersionProcess;
    }

    @GetMapping("/terraformconnect")
    public ProcessResponse testConnection() {
        terraformVersionProcess.executeProcess();
        terraformVersionProcess.generateResponse();
        ProcessResponse response = new ProcessResponse();
        response.setExitCode(terraformVersionProcess.getExitCode());
        response.setStdOut(terraformVersionProcess.getStdOut());
        response.setStdErr(terraformVersionProcess.getStdErr());
        response.setResponse(terraformVersionProcess.getResponse());
        return response;
    }

}
