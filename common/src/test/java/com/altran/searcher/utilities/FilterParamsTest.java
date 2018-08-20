package com.altran.searcher.utilities;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class FilterParamsTest {


    @Test
    public void testCreation() {
        FilterParams obj = new FilterParams();
        assertNotNull(obj);
    }

    @Test
    public void testCreationLang() {
        String lang = "ca";
        FilterParams obj = new FilterParams(lang);
        assertNotNull(obj);
        assertEquals(lang, obj.getLang());
    }

    @Test
    public void testToString() {
        FilterParams obj = new FilterParams();
        obj.setLang("ca");
        obj.setOffset(1);
        obj.setLimit(1);
        assertEquals("FilterParams{offset=1, limit=1, lang='ca'}", obj.toString());
    }
}
