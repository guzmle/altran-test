package com.altran.searcher.business.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageDTO {
    private String code;
    private OrganizationDTO organization;
    private String url;

    public PackageDTO(String code, OrganizationDTO organization, String url) {
        this.code = code;
        this.organization = organization;
        this.url = url;
    }
}
