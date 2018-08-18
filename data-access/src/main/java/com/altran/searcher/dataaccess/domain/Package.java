package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package {

    private String code;

    @JsonProperty("url_tornada")
    private Map<String, String> urlTornada;

    private Organization organization;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getUrlTornada() {
        return urlTornada;
    }

    public void setUrlTornada(Map<String, String> urlTornada) {
        this.urlTornada = urlTornada;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
