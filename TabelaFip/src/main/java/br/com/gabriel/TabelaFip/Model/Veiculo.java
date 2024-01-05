package br.com.gabriel.TabelaFip.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
        @JsonAlias("Marca")String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") String anoModelo,
        @JsonAlias("Valor") String valor)
         {
}
