package com.endava.practice.roadmap.domain.model.external.responses.quotes;

import lombok.Data;

@Data
public class Platform {

    private Integer id;

    private String name;

    private String symbol;

    private String slug;

    private String token_address;
}
