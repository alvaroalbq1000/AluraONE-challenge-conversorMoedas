package br.com.Alvaro.AluraONE.challenge.conversorMoedas;

import java.util.Map;

public record MoedaExchangeRate(String base_code, Map<String, Double> conversion_rates) {
}
