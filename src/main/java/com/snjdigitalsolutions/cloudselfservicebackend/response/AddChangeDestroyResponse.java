package com.snjdigitalsolutions.cloudselfservicebackend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddChangeDestroyResponse {

    private Integer exitCode;
    private String add;
    private String change;
    private String destroy;

}
