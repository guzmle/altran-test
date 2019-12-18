package com.altran.searcher.business;

import com.altran.searcher.business.domain.OrganizationDTO;
import com.altran.searcher.business.domain.PackageDTO;
import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.dataaccess.PackageDao;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.exceptions.InternalErrorException;
import com.altran.searcher.exceptions.NotFoundException;
import com.altran.searcher.utilities.FilterParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guzmle on 17/8/18.
 */
@Service
public class PackageServiceImpl implements PackageService {

    private static final Logger logger = LogManager.getLogger(PackageServiceImpl.class);
    private static final String SEARCH_SUCCESS = "Search Success";
    private final PackageDao dao;

    @Value("${config.minLimit}")
    private String minLimit;

    @Autowired
    public PackageServiceImpl(PackageDao dao) {
        this.dao = dao;
    }

    public Mono<ResultDTO> getPackages(FilterParams filter) {
        return Mono.create(emitter -> {
            try {
                ResultAPI<List<Package>> response = dao.getPackages(filter);

                int start = filter.getOffset() + filter.getLimit() > filter.getMaxItemsCache() ? 0 : filter.getOffset();
                int limit = filter.getLimit() == 0 ? Integer.parseInt(minLimit) : filter.getLimit();
                int end = Math.min((start + limit), response.getResults().size());
                List<Package> data = response.getResults().subList(start, end);

                List<PackageDTO> list = data.stream()
                        .map(aPackage ->
                                new PackageDTO(
                                        aPackage.getCode(),
                                        new OrganizationDTO(aPackage.getOrganization().getDescriptionTranslated().get(filter.getLang())),
                                        aPackage.getUrlTornada().get(filter.getLang())
                                ))
                        .collect(Collectors.toList());

                if (!list.isEmpty()) {
                    ResultDTO result = new ResultDTO();
                    result.setCount(data.size());
                    result.setOffset(filter.getOffset());
                    result.setTotalCount(response.getCount());
                    result.setPackages(list);
                    logger.info(SEARCH_SUCCESS);
                    emitter.success(result);
                } else {
                    emitter.error(new NotFoundException());
                }
            } catch (Exception err) {
                emitter.error(new InternalErrorException(err.getMessage(), err, filter));
            }
        });
    }
}
