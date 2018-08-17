package com.altran.services.implementation;

import com.altran.dataAccess.PackageDao;
import com.altran.dataAccess.domain.Package;
import com.altran.services.PackageService;
import com.altran.services.domain.OrganizationDTO;
import com.altran.services.domain.PackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzmle on 17/8/18.
 */
@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao dao;

    @Override
    public List<PackageDTO> getPackages(String lang) {
        List<Package> response = dao.getPackages();

        List<PackageDTO> result = new ArrayList<>();
        for (Package paquete : response) {

            PackageDTO obj = new PackageDTO();
            obj.setCode(paquete.getCode());
            OrganizationDTO organization = new OrganizationDTO();

            organization.setDescription(paquete.getOrganization().getDescriptionTranslated().get(lang));
            obj.setOrganization(organization);

            obj.setUrl(paquete.getUrlTornada().get(lang));
            result.add(obj);
        }

        return result;
    }
}
