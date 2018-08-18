package com.altran.searcher.dataaccess;

import java.util.List;
import com.altran.searcher.dataaccess.domain.Package;
import com.altran.searcher.dataaccess.domain.ResultAPI;
import com.altran.searcher.utilities.FilterParams;

/**
 * Class for data access
 */
public interface PackageDao {

    /**
     * Method that query all package of System
     * @return list of package
     * @param filter
     */
    ResultAPI<List<Package>> getPackages(FilterParams filter);
}
