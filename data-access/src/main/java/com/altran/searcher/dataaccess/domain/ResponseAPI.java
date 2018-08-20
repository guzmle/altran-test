package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAPI<T> {
    private String help;
    private boolean success;
    private ResultAPI<T> result;
    private ErrorMessage error;
}
