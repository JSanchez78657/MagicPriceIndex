package com.mpi.ws.endpoints.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MPIEndpoint {

    @RequestMapping("/")
    public String hello() {
        return "Hello World";
    }
}
