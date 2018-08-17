package com.altran.dataAccess;

import java.util.List;
import com.altran.dataAccess.domain.Package;

/**
 * Class for data access
 */
public interface PackageDao {

    /**
     * Method that query all package of System
     * @return list of package
     */
    List<Package> getPackages();
}
