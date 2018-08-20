package com.altran.searcher.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.altran.searcher.business.domain.PackageDTO;
import com.altran.searcher.utilities.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainRouterTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("It is Working!"));
    }

    @Test
    public void getFindAll() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "es");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String response = template.exchange(base.toString() + "package", HttpMethod.GET, entity, String.class)
                .getBody();
        assertThat(!response.contains("\"error\""), equalTo(true));
    }

    @Test
    public void getFindAllError() throws Exception {

        ResponseEntity<String> response = template.getForEntity(base.toString() + "package?offset=-50", String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void getFindWithParams() throws Exception {

        ResponseEntity<String> response = template.getForEntity(base.toString() + "package?offset=5&limit=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCorsFlter() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Origin", "http://localhost:9000");
        headers.set("Accept-Language", "ca");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String response = template.exchange(base.toString() + "package", HttpMethod.GET, entity, String.class)
                .getBody();
        assertThat(!response.contains("\"error\""), equalTo(true));
    }

    @Test
    public void testOptionRequest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Origin", "http://localhost:9000");
        headers.set("Accept-Language", "ca");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = template.exchange(base.toString() + "package", HttpMethod.OPTIONS, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
