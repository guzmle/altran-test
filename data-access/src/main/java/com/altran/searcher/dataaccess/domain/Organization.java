package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

    @JsonProperty("description_translated")
    private Map<String, String> descriptionTranslated;
}
