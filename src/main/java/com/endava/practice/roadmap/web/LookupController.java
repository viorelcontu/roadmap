package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.Coin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lookup")
public interface LookupController {

    //TODO
    // 1. quotes in different currencies
    // 2. multiple quotes retrieval in a single request

    @GetMapping("/{symbol}")
    Coin getQuote(@PathVariable String symbol);
}
