package com.altran.searcher.business;

import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.utilities.FilterParams;

/**
 * Created by guzmle on 17/8/18.
 */
public interface PackageService {

    /**
     * Metodo que retorna la lista de paquetes
     * @return lista de paquetes segun el idioma
     */
    ResultDTO getPackages(FilterParams filter);

    void updatePackageCached();
}
