package com.snjdigitalsolutions.cloudselfservicebackend.controller;

import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.CDKTFDiffProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.CDKTFVersionProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.localprocess.TerraformVersionProcess;
import com.snjdigitalsolutions.cloudselfservicebackend.response.AddChangeDestroyResponse;
import com.snjdigitalsolutions.cloudselfservicebackend.response.ProcessResponse;
import com.snjdigitalsolutions.cloudselfservicebackend.utility.AddChangeDestroyUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TerraformController {

    private final TerraformVersionProcess terraformVersionProcess;
    private final CDKTFVersionProcess cdktfVersionProcess;
    private final CDKTFDiffProcess cdktfDiffProcess;
    private final AddChangeDestroyUtility addChangeDestroyUtility;

    public TerraformController(TerraformVersionProcess terraformVersionProcess, CDKTFVersionProcess cdktfVersionProcess, CDKTFDiffProcess cdktfDiffProcess, AddChangeDestroyUtility addChangeDestroyUtility) {
        this.terraformVersionProcess = terraformVersionProcess;
        this.cdktfVersionProcess = cdktfVersionProcess;
        this.cdktfDiffProcess = cdktfDiffProcess;
        this.addChangeDestroyUtility = addChangeDestroyUtility;
    }

    @GetMapping("/terraformversion")
    public Mono<ProcessResponse> getTerraformVersion() {
        terraformVersionProcess.performProcess();
        ProcessResponse response = new ProcessResponse();
        response.setExitCode(terraformVersionProcess.getExitCode());
        response.setStdOut(terraformVersionProcess.getStdOut());
        response.setStdErr(terraformVersionProcess.getStdErr());
        response.setResponse(terraformVersionProcess.getResponse());
        return Mono.just(response);
    }

    @GetMapping("/terraformcdktfversion")
    public Mono<ProcessResponse> getCDKTFVersion() {
        cdktfVersionProcess.performProcess();
        ProcessResponse response = new ProcessResponse();
        response.setExitCode(cdktfVersionProcess.getExitCode());
        response.setStdOut(cdktfVersionProcess.getStdOut());
        response.setStdErr(cdktfVersionProcess.getStdErr());
        response.setResponse(cdktfVersionProcess.getResponse());
        return Mono.just(response);
    }

    @GetMapping("/terraformcdktfdiff")
    public ResponseEntity<Flux<AddChangeDestroyResponse>> getCDKTFDiff() {
        cdktfDiffProcess.performProcess();
        AddChangeDestroyResponse response = new AddChangeDestroyResponse();
        response.setExitCode(cdktfDiffProcess.getExitCode());
        String processResponse = cdktfDiffProcess.getResponse();
        if (!processResponse.isEmpty()) {
            response.setAdd(addChangeDestroyUtility.numberToAdd(processResponse).toString());
            response.setChange(addChangeDestroyUtility.numberToChange(processResponse).toString());
            response.setDestroy(addChangeDestroyUtility.numberToDestroy(processResponse).toString());
        }
        Flux<AddChangeDestroyResponse> flux = Flux.just(response);
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:8080")
                .body(flux);
    }

}
