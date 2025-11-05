package com.snjdigitalsolutions.cloudselfservicebackend.controller;

import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.CDKTFVersionProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.TerraformVersionProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.response.ProcessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TerraformController {

    private final TerraformVersionProcess terraformVersionProcess;
    private final CDKTFVersionProcess cdktfVersionProcess;

    public TerraformController(TerraformVersionProcess terraformVersionProcess, CDKTFVersionProcess cdktfVersionProcess) {
        this.terraformVersionProcess = terraformVersionProcess;
        this.cdktfVersionProcess = cdktfVersionProcess;
    }

    @GetMapping("/terraformversion")
    public ProcessResponse getTerraformVersion() {
        terraformVersionProcess.performProcess();
        ProcessResponse response = new ProcessResponse();
        response.setExitCode(terraformVersionProcess.getExitCode());
        response.setStdOut(terraformVersionProcess.getStdOut());
        response.setStdErr(terraformVersionProcess.getStdErr());
        response.setResponse(terraformVersionProcess.getResponse());
        return response;
    }

    @GetMapping("/terraformcdktfversion")
    public ProcessResponse getCDKTFVersion() {
        cdktfVersionProcess.performProcess();
        ProcessResponse response = new ProcessResponse();
        response.setExitCode(cdktfVersionProcess.getExitCode());
        response.setStdOut(cdktfVersionProcess.getStdOut());
        response.setStdErr(cdktfVersionProcess.getStdErr());
        response.setResponse(cdktfVersionProcess.getResponse());
        return response;
    }

}
