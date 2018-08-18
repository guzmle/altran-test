package com.altran.searcher.dataaccess.implementation;

import com.altran.searcher.dataaccess.PackageDao;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResponseAPI;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.dataaccess.exceptions.APIException;
import com.altran.searcher.utilities.FilterParams;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "packages", key = "#filter.lang", condition = "#filter.limit + #filter.offset <= 50")
    public ResultAPI<List<Package>> getPackages(FilterParams filter) {

        try {
            int start = filter.getOffset() + filter.getLimit() <= 50 ? 0 : filter.getOffset();
            int rows = filter.getOffset() + filter.getLimit() <= 50 ? 50 : filter.getLimit();
            StringBuilder url = new StringBuilder("http://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search?");
            url.append("rows=").append(rows).append("&start=").append(start);
            RestTemplate restTemplate = new RestTemplate();

            ResponseAPI<List<Package>> data = restTemplate.exchange(
                    url.toString(),
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
            return data.getResult();
        } catch (Exception err) {
            throw new APIException(err.getMessage());
        }
    }
}
