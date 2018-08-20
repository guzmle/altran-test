package com.altran.searcher.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainRouterTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    private UriComponentsBuilder packagesURI;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1");
        this.packagesURI = UriComponentsBuilder.fromUriString(this.base + "/packages");
    }

    @Test
    public void getFindAll() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "es");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = template.exchange(packagesURI.build().toString(), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getFindAllError() throws Exception {
        String uri = packagesURI.queryParam("offset", -50).build().toString();
        ResponseEntity<String> response = template.getForEntity(uri, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void getFindWithParams() throws Exception {

        String uri = packagesURI.queryParam("offset", 5).queryParam("limit", 10).build().toString();
        ResponseEntity<String> response = template.getForEntity(uri, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCorsFlter() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Origin", "http://localhost:9000");
        headers.set("Accept-Language", "ca");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = template.exchange(packagesURI.build().toString(), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testOptionRequest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Origin", "http://localhost:9000");
        headers.set("Accept-Language", "ca");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = template.exchange(packagesURI.build().toString(), HttpMethod.OPTIONS, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
