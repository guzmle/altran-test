package com.altran.searcher.utilities;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que permite representar los datos para filtrar la consulta en el sistema
 */
@Getter
@Setter
public class FilterParams {
    private int offset;
    private int limit;
    private String lang;
    private int maxItemsCache;

    public FilterParams() {
    }

    public FilterParams(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "FilterParams{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", lang='" + lang + '\'' +
                '}';
    }
}
