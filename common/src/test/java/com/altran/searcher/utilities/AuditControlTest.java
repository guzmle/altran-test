package com.altran.searcher.utilities;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AuditControlTest {


    @Test
    public void testCreation() {
        AuditControl obj = new AuditControl();
        assertNotNull(obj);
    }

    @Test
    public void testToString() {
        AuditControl obj = new AuditControl();
        obj.setIp("1");
        obj.setUserAgent("1");
        assertEquals("AuditControl{ip='1', UserAgent='1'}", obj.toString());
    }
}
