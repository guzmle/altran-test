package com.altran.searcher.dataaccess.implementation;

import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.utilities.FilterParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Clase que realiza las pruebas de integracion con el api
 */
@RunWith(SpringRunner.class)
public class PackageServiceImplITTest {

    @InjectMocks
    private PackageDaoImpl dao;

    private Properties prop;
    private String apiUrl;
    private String defaultLang;

    @Before
    public void init() throws IOException {
        if (prop == null) {
            prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
            apiUrl = prop.getProperty("api.url");
            defaultLang = prop.getProperty("config.defaultLang");
        }


        ReflectionTestUtils.setField(dao, "apiUrl", apiUrl);
        ReflectionTestUtils.setField(dao, "restTemplate", new RestTemplate());
    }

    /**
     * Metodo que prueba que exitosamente se consulte el api
     */
    @Test
    public void testFindPackageSuccess() {

        FilterParams filter = new FilterParams();
        filter.setLimit(1);
        filter.setMaxItemsCache(1);

        ResultAPI<List<Package>> data = dao.getPackages(filter);
        assertNotNull(data);
    }

    /**
     * Metodo que prueba la consulta cuando el indice del ultimo registro dado los parametros de cantidad y de inicio
     * se encuentre dentro del maximo de registros que se guardan en cache
     */
    @Test
    public void testFindPackageSuccessParse() {

        FilterParams filter = new FilterParams();
        filter.setLimit(1);
        filter.setMaxItemsCache(1);

        ResultAPI<List<Package>> data = dao.getPackages(filter);

        Package firstObject = data.getResults().get(0);

        assertNotNull(firstObject.getCode());
        assertNotNull(firstObject.getOrganization());
        assertNotNull(firstObject.getOrganization().getDescriptionTranslated());
        assertTrue(firstObject.getOrganization().getDescriptionTranslated().containsKey(defaultLang));
        assertNotNull(firstObject.getUrlTornada());
        assertTrue(firstObject.getUrlTornada().containsKey(defaultLang));
    }
}
