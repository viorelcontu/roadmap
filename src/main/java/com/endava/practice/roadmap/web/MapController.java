package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/map")
public class MapController {

    private final CurrencyService currencyService;

    @GetMapping ("/cryptocurrency")
    Set<Currency> getCryptoMapping () {
        return currencyService.getCryptoSet();
    }

    @GetMapping("/fiatcurrency")
    Set<Currency> getFiatMapping () {
        return currencyService.getFiatSet();
    }
}
