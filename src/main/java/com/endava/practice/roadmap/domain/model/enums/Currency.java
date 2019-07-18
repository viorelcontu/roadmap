package com.endava.practice.roadmap.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.endava.practice.roadmap.domain.model.enums.Currency.Type.CRYPTO;
import static com.endava.practice.roadmap.domain.model.enums.Currency.Type.FIAT;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonPropertyOrder ({"code", "id", "name"})
@JsonIgnoreProperties({"externalId", "slug", "type", "crypto", "fiat"})
public enum Currency {

    BTC(1, 1, CRYPTO, "Bitcoin", "bitcoin"),

    ETH(2, 1027, CRYPTO, "Ethereum", "ethereum"),

    XRP(3, 52, CRYPTO, "XRP", "ripple"),

    BCH(4, 1831, CRYPTO, "Bitcoin Cash", "bitcoin-cash"),

    EOS(5, 1765, CRYPTO, "EOS", "eos"),

    LTC(6, 2, CRYPTO, "Litecoin", "litecoin"),

    BNB(7, 1839, CRYPTO, "Binance Coin", "binance-coin"),

    USDT(8, 825, CRYPTO, "Tether", "tether"),

    XLM(9, 512, CRYPTO, "Stellar", "stellar"),

    ADA(10, 2010, CRYPTO, "Cardano", "cardano"),

    TRX(11, 1958, CRYPTO, "Tron", "tron"),

    DASH(12, 131, CRYPTO, "Dash", "dash"),

    XMR(13, 328, CRYPTO, "Monero", "monero"),

    MIOTA(14, 1720, CRYPTO, "Iota", "iota"),

    USD(101, 2781, FIAT, "United States Dollar", "dollar"),

    EUR(102, 2790, FIAT, "Euro", "euro"),

    MDL(103, 3555, FIAT, "Moldovan Leu", "moldavian-leu"),

    RON(104, 2817, FIAT, "Romanian Leu", "romanian-leu"),

    RUB(105, 2806, FIAT, "Russian Ruble", "rubble"),

    CZK(106, 2788, FIAT, "Czech Koruna", "czech-crown");

    private final int id;

    private final int externalId;

    private final Type type;

    private final String name;

    private final String slug; //not used for now

    public String getCode() { return this.name(); }

    public boolean isCrypto() { return type == CRYPTO;}

    public boolean isFiat() { return type == FIAT;}

    public enum Type {
        CRYPTO, FIAT
    }
}
