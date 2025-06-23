package br.com.Alvaro.AluraONE.challenge.conversorMoedas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static double converterMoeda(Moeda moeda, double valor){
        double taxaConversao = moeda.getTaxaConversao();
        return valor * taxaConversao;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String moedaIN = "", moedaOUT = "", uriMoeda, jsonExchangeRate;
        int opt = -1;
        double valor, valorConvertido;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while (opt != 0){
            System.out.println("""
                    \nBem vindo(a) ao Conversor de Moedas
                    01- Dólar (USD) para Real (BRL)
                    02- Real (BRL) para Dólar (USD)
                    03- Euro (EUR) para Real (BRL)
                    04- Real (BRL) para Euro (EUR)
                    05- Peso argentino (ARS) para Real (BRL)
                    06- Real (BRL) para Peso argentino (ARS)
                    07- Iene (JPY) para Real (BRL)
                    08- Real (BRL) para Iene (JPY)
                    09- Yuan chinês (CNY) para Real (BRL)
                    10- Real (BRL) para Yuan chinês (CNY)
                    0- Sair
                    """);
            System.out.print("Digite o número da opção desejada: ");
            opt = scanner.nextInt();
            switch (opt){
                case 1: {moedaIN = "USD"; moedaOUT = "BRL"; break;}
                case 2: {moedaIN = "BRL"; moedaOUT = "USD"; break;}
                case 3: {moedaIN = "EUR"; moedaOUT = "BRL"; break;}
                case 4: {moedaIN = "BRL"; moedaOUT = "EUR"; break;}
                case 5: {moedaIN = "ARS"; moedaOUT = "BRL"; break;}
                case 6: {moedaIN = "BRL"; moedaOUT = "ARS"; break;}
                case 7: {moedaIN = "JPY"; moedaOUT = "BRL"; break;}
                case 8: {moedaIN = "BRL"; moedaOUT = "JPY"; break;}
                case 9: {moedaIN = "CNY"; moedaOUT = "BRL"; break;}
                case 10: {moedaIN = "BRL"; moedaOUT = "CNY"; break;}
                case 0: {System.out.println("Saindo..."); break;}
                default: {System.out.println("Opção Inválida! Tente novamente."); opt = -1; break;}
            }
            if (opt == 0 || opt == -1) {continue;}
            System.out.print("Digite o valor que deseja converter: ");
            valor = scanner.nextDouble();
            uriMoeda = "https://v6.exchangerate-api.com/v6/4c2316dd63d4a8c815e9f760/latest/"+moedaIN;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriMoeda))
                    .build();
            try {
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                jsonExchangeRate = response.body();
                //System.out.println(jsonExchangeRate);

                MoedaExchangeRate moedaExR = gson.fromJson(jsonExchangeRate, MoedaExchangeRate.class);
                //System.out.println(moedaExR);

                Moeda moedaOBJ = new Moeda(moedaExR, moedaOUT);
                System.out.println(moedaOBJ);

                valorConvertido = converterMoeda(moedaOBJ, valor);
                System.out.printf("\nValor %.2f (%s) corresponde ao valor final de: %.2f (%s)\n", valor, moedaIN, valorConvertido, moedaOUT);
            } catch (IOException | InterruptedException e) {
                System.out.printf("Ocorreu um erro do HTTPRequest:\n%s\n", e.getMessage());
            } catch (Exception e) {
                System.out.printf("Ocorreu um erro:\n%s\n", e.getMessage());
            }
        }
    }
}
