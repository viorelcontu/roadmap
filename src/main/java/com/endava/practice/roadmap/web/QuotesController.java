package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.service.coinmarket.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/quotes")
public class QuotesController {

    private final MarketService marketService;

    //TODO
    // 1. quotes in different currencies
    // 2. multiple quotes retrieval in a single request

    @GetMapping ("/{symbol}")
    Coin getQuotesBySymbol(@PathVariable String symbol) {
        return marketService.getQuotes(symbol);
    }
}
