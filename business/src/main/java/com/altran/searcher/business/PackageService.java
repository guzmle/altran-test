package com.altran.searcher.business;

import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.utilities.FilterParams;
import reactor.core.publisher.Mono;

/**
 * Created by guzmle on 17/8/18.
 */
public interface PackageService {

    /**
     * Metodo que retorna la lista de paquetes
     * @return lista de paquetes segun los parametros establecidos
     */
    Mono<ResultDTO> getPackages(FilterParams filter);
}
