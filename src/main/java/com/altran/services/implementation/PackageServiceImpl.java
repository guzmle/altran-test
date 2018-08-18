package com.altran.services.implementation;

import com.altran.dataAccess.PackageDao;
import com.altran.dataAccess.domain.Package;
import com.altran.dataAccess.domain.ResultAPI;
import com.altran.services.PackageService;
import com.altran.services.domain.OrganizationDTO;
import com.altran.services.domain.PackageDTO;
import com.altran.services.domain.ResultDTO;
import com.altran.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by guzmle on 17/8/18.
 */
@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao dao;

    @Autowired
    private CacheManager cacheManager;

    public ResultDTO getPackages(FilterParams filter) {
        ResultAPI<List<Package>> response = dao.getPackages(filter);

        int start = filter.getOffset();
        int limit = filter.getLimit() == 0 ? 10 : filter.getLimit();
        int end = (start + limit) > response.getResults().size() ? response.getResults().size() : (start + limit);
        List<Package> data = response.getResults().subList(start, end);

        List<PackageDTO> list = new ArrayList<>();
        for (Package paquete : data) {

            PackageDTO obj = new PackageDTO();
            obj.setCode(paquete.getCode());
            OrganizationDTO organization = new OrganizationDTO();

            organization.setDescription(paquete.getOrganization().getDescriptionTranslated().get(filter.getLang()));
            obj.setOrganization(organization);

            obj.setUrl(paquete.getUrlTornada().get(filter.getLang()));
            list.add(obj);
        }

        ResultDTO result = new ResultDTO();
        result.setCount(data.size());
        result.setOffset(filter.getOffset());
        result.setTotalCount(response.getCount());
        result.setPackages(list);
        return result;
    }

    public void updatePackageCached() {
        Cache cache = cacheManager.getCache("packages");
        Object nativeCache = cache.getNativeCache();
        Object[] langs = ((ConcurrentHashMap) nativeCache).keySet().toArray();
        cache.clear();

        for (Object lang : langs) {
            FilterParams filter = new FilterParams();
            filter.setLang((String) lang);
            dao.getPackages(filter);
        }

        System.out.println("Cache");

    }
}
