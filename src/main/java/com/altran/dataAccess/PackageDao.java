package com.altran.dataAccess;

import java.util.List;
import com.altran.dataAccess.domain.Package;
import com.altran.dataAccess.domain.ResultAPI;
import com.altran.utilities.FilterParams;

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
