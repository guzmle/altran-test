package com.altran.searcher.dataaccess.implementation;

import com.altran.searcher.dataaccess.domain.ErrorMessage;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResponseAPI;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.dataaccess.exceptions.APIException;
import com.altran.searcher.utilities.FilterParams;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
public class PackageServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<ResponseAPI<List<Package>>> response;

    @Mock
    private ResponseAPI<List<Package>> result;
    @Mock
    private ErrorMessage error;

    @InjectMocks
    private PackageDaoImpl dao;

    private Properties prop;
    private int maxItems;
    private int minLimit;
    private String apiUrl;

    @Before
    public void init() throws IOException {
        if (prop == null) {
            prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
            maxItems = Integer.parseInt(prop.getProperty("config.maxItemsCache"));
            minLimit = Integer.parseInt(prop.getProperty("config.minLimit"));
            apiUrl = prop.getProperty("api.url");
        }


        ReflectionTestUtils.setField(dao, "apiUrl", apiUrl);
    }

    /**
     * Metodo que prueba la consulta cuando el indice del ultimo registro dado los parametros de cantidad y de inicio
     * se encuentre dentro del maximo de registros que se guardan en cache
     */
    @Test
    public void testFindPackageBetweenMaxLimits() {

        StringBuilder url = new StringBuilder();
        Formatter fmt = new Formatter(url);
        fmt.format(apiUrl, maxItems, 0);

        doReturn(new ResultAPI<>()).when(result).getResult();
        doReturn(result).when(response).getBody();
        doReturn(true).when(result).isSuccess();
        doReturn(response).when(restTemplate).exchange(eq(url.toString()), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems - minLimit - 1);


        ResultAPI<List<Package>> data = dao.getPackages(filter);
        assertNotNull(data);
    }

    /**
     * Metodo que prueba la consulta cuando el indice del ultimo registro dado los parametros de cantidad y de inicio
     * se encuentre dentro del maximo de registros que se guardan en cache
     */
    @Test
    public void testFindPackageEqualMaxLimitsCache() {

        StringBuilder url = new StringBuilder();
        Formatter fmt = new Formatter(url);
        fmt.format(apiUrl, maxItems, 0);

        doReturn(new ResultAPI<>()).when(result).getResult();
        doReturn(result).when(response).getBody();
        doReturn(true).when(result).isSuccess();
        doReturn(response).when(restTemplate).exchange(eq(url.toString()), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems - minLimit);


        ResultAPI<List<Package>> data = dao.getPackages(filter);
        assertNotNull(data);
    }

    /**
     * Metodo que prueba la consulta cuando el indice del ultimo registro dado los parametros de cantidad y de inicio
     * sobre pasa al maximo de registros que se guardan en cache
     */
    @Test
    public void testFindPackageGreaterThanMaxLimitsCache() {

        StringBuilder url = new StringBuilder();
        Formatter fmt = new Formatter(url);
        fmt.format(apiUrl, minLimit, maxItems + minLimit);

        doReturn(new ResultAPI<>()).when(result).getResult();
        doReturn(result).when(response).getBody();
        doReturn(true).when(result).isSuccess();
        doReturn(response).when(restTemplate).exchange(eq(url.toString()), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems + minLimit);


        ResultAPI<List<Package>> data = dao.getPackages(filter);
        assertNotNull(data);
    }


    /**
     * Metodo que prueba la consulta cuando el resultado del api es null
     */
    @Test
    public void testFindPackageNullResult() {
        thrown.expect(APIException.class);

        doReturn(null).when(response).getBody();
        doReturn(response).when(restTemplate).exchange(anyString(), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems + minLimit);

        dao.getPackages(filter);
    }


    /**
     * Metodo que prueba la consulta cuando el resultado del api es null
     */
    @Test
    public void testFindPackageUnsuccessfulResult() {

        thrown.expect(APIException.class);

        doReturn(result).when(response).getBody();
        doReturn(false).when(result).isSuccess();
        doReturn(error).when(result).getError();
        doReturn(null).when(error).getMessage();
        doReturn(response).when(restTemplate).exchange(anyString(), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems + minLimit);

        dao.getPackages(filter);
    }


    /**
     * Metodo que prueba la consulta cuando el resultado del api es null
     */
    @Test
    public void testFindPackageUnexpectedException() {

        thrown.expect(APIException.class);

        doThrow(new RuntimeException()).when(restTemplate).exchange(anyString(), any(HttpMethod.class), isNull(),
                Mockito.<ParameterizedTypeReference<ResponseAPI<List<Package>>>>any());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit);
        filter.setMaxItemsCache(maxItems);
        filter.setOffset(maxItems + minLimit);

        dao.getPackages(filter);
    }
}
