package com.airbnb.dto;

import lombok.Data;

import java.util.List;
@Data
public class PropSortDto<P> {
    private List<PropertyDto> dto;
    private int pageNo;
    private int pageSize;
//    private String sortBy;
//    private String sortOrder;
}
