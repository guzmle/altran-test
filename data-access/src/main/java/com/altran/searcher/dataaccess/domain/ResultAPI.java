package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultAPI<T> {
    private int count;
    private String sort;
    private T results;
}
