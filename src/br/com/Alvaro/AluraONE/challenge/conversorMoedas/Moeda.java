package br.com.Alvaro.AluraONE.challenge.conversorMoedas;

public class Moeda {
    private String codMoeda, codMoedaOUT;
    private double taxaConversao;

    public Moeda(MoedaExchangeRate moedaAPI, String moedaOUT) {
        codMoeda = moedaAPI.base_code();
        codMoedaOUT = moedaOUT;
        taxaConversao = moedaAPI.conversion_rates().get(codMoedaOUT);
    }

    public double getTaxaConversao() {
        return taxaConversao;
    }

    @Override
    public String toString() {
        return "(Moeda: %s, Taxa de conversão para %s: %f)".formatted(codMoeda, codMoedaOUT, taxaConversao);
    }
}
