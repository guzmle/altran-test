package com.altran.searcher.business.implementation;

import com.altran.searcher.business.domain.PackageDTO;
import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.dataaccess.PackageDao;
import com.altran.searcher.dataaccess.domain.Organization;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.utilities.FilterParams;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doReturn;

/**
 * Created by guzmle on 18/8/18.
 */
@RunWith(SpringRunner.class)
public class PackageServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private PackageDao dao;

    @InjectMocks
    private PackageServiceImpl service;

    private Properties prop;
    private int minLimit;
    private String defaultLang;

    private List<Package> listMock;

    @Mock
    ResultAPI<List<Package>> apiResult;


    @Before
    public void init() throws IOException {
        if (prop == null) {
            prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
            minLimit = Integer.parseInt(prop.getProperty("config.minLimit"));
            defaultLang = prop.getProperty("config.defaultLang");
        }

        ReflectionTestUtils.setField(service, "minLimit", String.valueOf(minLimit));

        listMock = new ArrayList<>();

        HashMap<String, String> langMap = new HashMap<>();
        langMap.put("ca", "idioma ca");
        langMap.put("es", "idioma es");

        for (int i = 0; i < minLimit; i++) {
            Package obj = new Package();
            obj.setCode("code" + i);
            Organization org = new Organization();
            org.setDescriptionTranslated(langMap);
            obj.setOrganization(org);

            obj.setUrlTornada(langMap);
            listMock.add(obj);
        }
    }

    /**
     * Mtodo que prueba que cuando no se asina el limit del objeto FilterParam se tome el configurado por default
     */
    @Test
    public void testFindPackageWitoutLimit() {

        doReturn(listMock).when(apiResult).getResults();
        doReturn(apiResult).when(dao).getPackages(anyObject());

        FilterParams filter = new FilterParams();
        filter.setLang(defaultLang);

        ResultDTO data = service.getPackages(filter);
        assertEquals(minLimit, data.getCount());
        assertEquals(minLimit, data.getPackages().size());
    }


    /**
     * Metodo que prueba que cuando el limite sobre pasa la cantidad de registros obtenido de la fuente de datos
     * se obtenga la cantidad adecuada
     */
    @Test
    public void testFindPackageLimitGreaterThanLength() {

        doReturn(listMock).when(apiResult).getResults();
        doReturn(apiResult).when(dao).getPackages(anyObject());

        FilterParams filter = new FilterParams();
        filter.setLimit(minLimit + 1);
        filter.setLang(defaultLang);

        ResultDTO data = service.getPackages(filter);
        assertEquals(minLimit, data.getCount());
        assertEquals(minLimit, data.getPackages().size());
    }


    /**
     * Metodo que prueba que se obtenga las sublista con el correcto orden
     */
    @Test
    public void testFindPackageCorrectOrder() {

        doReturn(listMock).when(apiResult).getResults();
        doReturn(apiResult).when(dao).getPackages(anyObject());

        FilterParams filter = new FilterParams();
        filter.setOffset(1);
        filter.setLimit(minLimit);
        filter.setLang(defaultLang);

        ResultDTO data = service.getPackages(filter);
        PackageDTO firtObject = data.getPackages().get(0);
        assertEquals(minLimit - 1, data.getCount());
        assertEquals(minLimit - 1, data.getPackages().size());
        assertEquals(listMock.get(1).getCode(), firtObject.getCode());
    }


    /**
     * Metodo que prueba que obtenga el valor adecuado del idioma seleccionado
     */
    @Test
    public void testFindPackageGetCorrectLang() {

        doReturn(listMock).when(apiResult).getResults();
        doReturn(apiResult).when(dao).getPackages(anyObject());

        FilterParams filter = new FilterParams();
        filter.setLang(defaultLang);

        ResultDTO data = service.getPackages(filter);

        PackageDTO firtObject = data.getPackages().get(0);
        assertEquals(listMock.get(0).getUrlTornada().get(defaultLang), firtObject.getUrl());
        assertEquals(listMock.get(0).getOrganization().getDescriptionTranslated().get(defaultLang),
                firtObject.getOrganization().getDescription());
    }
}
