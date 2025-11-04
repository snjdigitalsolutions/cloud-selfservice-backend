package com.snjdigitalsolutions.cloudselfservicebackend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessResponse {

    private Integer exitCode;
    private String response;
    private String stdOut;
    private String stdErr;

}
