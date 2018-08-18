package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

    @JsonProperty("description_translated")
    private Map<String, String> descriptionTranslated;

    public Map<String, String> getDescriptionTranslated() {
        return descriptionTranslated;
    }

    public void setDescriptionTranslated(Map<String, String> descriptionTranslated) {
        this.descriptionTranslated = descriptionTranslated;
    }
}
