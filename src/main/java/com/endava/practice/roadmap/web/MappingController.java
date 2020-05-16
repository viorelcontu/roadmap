package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map")
public interface MappingController {

    @GetMapping("/coins")
    List<Coin> getCoinListing();

    @GetMapping("/currencies")
    Map<Currency, String> getCurrencies();
}
