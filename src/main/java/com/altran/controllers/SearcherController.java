package com.altran.controllers;

import com.altran.services.PackageService;
import com.altran.services.domain.PackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearcherController {

    @Autowired
    private PackageService service;

    @RequestMapping(value = "/package", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PackageDTO> getAllPackage() {
        List<PackageDTO> response = service.getPackages("ca");
        return response;
    }
}
