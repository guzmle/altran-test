package com.altran.searcher.business.domain;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class OrganizationDTOTest {


    @Test
    public void testCreation() {
        OrganizationDTO obj = new OrganizationDTO("description");
        assertNotNull(obj);
    }

    @Test
    public void testGetDescription() {
        OrganizationDTO obj = new OrganizationDTO("description");
        obj.setDescription("nuevo");
        assertEquals("nuevo", obj.getDescription());
    }
}
