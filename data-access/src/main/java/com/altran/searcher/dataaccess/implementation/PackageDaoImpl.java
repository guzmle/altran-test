package com.altran.searcher.dataaccess.implementation;

import com.altran.searcher.dataaccess.PackageDao;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResponseAPI;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.dataaccess.exceptions.APIException;
import com.altran.searcher.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Formatter;
import java.util.List;

/**
 * Created by guzmle on 17/8/18.
 */
@Component
public class PackageDaoImpl implements PackageDao {

    private final RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${config.maxItemsCache}")
    private String maxItemsCache;

    @Autowired
    public PackageDaoImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(cacheNames = "packages", key = "#filter.lang", condition = "#filter.limit + #filter.offset <= 50")
    public ResultAPI<List<Package>> getPackages(FilterParams filter) {

        try {
            int maxItems = Integer.parseInt(maxItemsCache);
            int start = filter.getOffset() + filter.getLimit() <= maxItems ? 0 : filter.getOffset();
            int rows = filter.getOffset() + filter.getLimit() <= maxItems ? maxItems : filter.getLimit();

            StringBuilder url = new StringBuilder();
            Formatter fmt = new Formatter(url);
            fmt.format(apiUrl, rows, start);


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
