package com.altran.searcher.utilities;

/**
 * Clase que representa los datos necesarios para la auditoria
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditControl {
    private String ip;
    private String userAgent;

    @Override
    public String toString() {
        return "AuditControl{" +
                "ip='" + ip + '\'' +
                ", UserAgent='" + userAgent + '\'' +
                '}';
    }
}
