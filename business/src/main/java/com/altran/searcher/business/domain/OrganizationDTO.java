package com.altran.searcher.business.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO {
    private String description;

    public OrganizationDTO(String description) {
        this.description = description;
    }
}
