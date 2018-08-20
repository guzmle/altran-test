package com.altran.searcher.business.domain;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class PackageDTOTest {


    @Test
    public void testCreation() {
        PackageDTO obj = new PackageDTO("0", new OrganizationDTO("as"), "");
        assertNotNull(obj);
        assertEquals("0", obj.getCode());
        assertEquals("", obj.getUrl());
    }
}
