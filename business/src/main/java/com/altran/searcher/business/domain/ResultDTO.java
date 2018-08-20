package com.altran.searcher.business.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultDTO {
    private int totalCount;
    private int count;
    private int offset;
    private String lang;

    List<PackageDTO> packages;
}
