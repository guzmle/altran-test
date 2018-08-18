package com.altran.searcher.dataaccess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by guzmle on 17/8/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultAPI<T> {
    private int count;
    private String sort;
    private T results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
