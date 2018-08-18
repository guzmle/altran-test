package com.altran.searcher.business.domain;

/**
 * Created by guzmle on 17/8/18.
 */
public class PackageDTO {
    private String code;
    private OrganizationDTO organization;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
