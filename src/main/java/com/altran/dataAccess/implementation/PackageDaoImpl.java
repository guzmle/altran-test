package com.altran.dataAccess.implementation;

import com.altran.dataAccess.PackageDao;
import com.altran.dataAccess.domain.Package;
import com.altran.dataAccess.domain.ResponseAPI;
import com.altran.dataAccess.exceptions.APIException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by guzmle on 17/8/18.
 */
@Component
public class PackageDaoImpl implements PackageDao {

    @Override
    public List<Package> getPackages() {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseAPI<List<Package>> data = restTemplate.exchange(
                    "http://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseAPI<List<Package>>>() {
                    })
                    .getBody();
            if (data == null) {
                throw new APIException("El objeto de recepcion esta vacio");
            }
            if (!data.isSuccess()) {
                throw new APIException(data.getError().getMessage());
            }
            return data.getResult().getResults();
        } catch (Exception err) {
            throw new APIException(err.getMessage());
        }
    }
}
