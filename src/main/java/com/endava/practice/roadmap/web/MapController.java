package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.service.coinmarket.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/map")
public class MapController {

    private final MarketService marketService;

    @GetMapping("/coins")
    public List<Coin> getCoinMapping() {
        return marketService.getListing();
    }

    @GetMapping("/currencies")
    public Map<Currency, String> getCurrencyMapping() {
        return marketService.getCurrencies();
    }
}
