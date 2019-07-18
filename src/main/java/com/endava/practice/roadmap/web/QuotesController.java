package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.responses.quotes.QuotesResponse;
import com.endava.practice.roadmap.domain.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/quotes")
public class QuotesController {

    private final MarketService marketService;

    @GetMapping
    QuotesResponse getQuotesById (@RequestParam int id) {

        return marketService.getQuotes(id);
    }


    @GetMapping ("/{symbol}")
    QuotesResponse getQuotesBySymbol(@PathVariable String symbol) {

        return marketService.getQuotes(symbol);
    }
}
