package com.altran.searcher.utilities;

/**
 * Created by guzmle on 17/8/18.
 */
public class FilterParams {
    private int offset;
    private int limit;
    private String lang;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
