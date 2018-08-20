package com.altran.searcher.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private int code;
    private boolean success;
    private T result;
}
