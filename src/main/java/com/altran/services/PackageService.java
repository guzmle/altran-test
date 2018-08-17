package com.altran.services;

import com.altran.services.domain.PackageDTO;

import java.util.List;

/**
 * Created by guzmle on 17/8/18.
 */
public interface PackageService {

    /**
     * Metodo que retorna la lista de paquetes
     * @param lang idioma
     * @return lista de paquetes segun el idioma
     */
    List<PackageDTO> getPackages(String lang);
}
