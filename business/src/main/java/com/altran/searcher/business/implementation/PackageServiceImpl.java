package com.altran.searcher.business.implementation;

import com.altran.searcher.business.PackageService;
import com.altran.searcher.business.domain.OrganizationDTO;
import com.altran.searcher.business.domain.PackageDTO;
import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.dataaccess.PackageDao;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzmle on 17/8/18.
 */
@Service
public class PackageServiceImpl implements PackageService {

    private final PackageDao dao;

    @Value("${config.minLimit}")
    private String minLimit;

    @Autowired
    public PackageServiceImpl(PackageDao dao) {
        this.dao = dao;
    }

    public ResultDTO getPackages(FilterParams filter) {
        ResultAPI<List<Package>> response = dao.getPackages(filter);

        int start = filter.getOffset();
        int limit = filter.getLimit() == 0 ? Integer.parseInt(minLimit) : filter.getLimit();
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
}
