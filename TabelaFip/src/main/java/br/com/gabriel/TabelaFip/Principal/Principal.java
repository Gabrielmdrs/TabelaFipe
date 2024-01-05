package br.com.gabriel.TabelaFip.Principal;

import br.com.gabriel.TabelaFip.Model.Dados;
import br.com.gabriel.TabelaFip.Model.Modelos;
import br.com.gabriel.TabelaFip.Model.Veiculo;
import br.com.gabriel.TabelaFip.service.ConsumoAPI;
import br.com.gabriel.TabelaFip.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    public void exibeMenu() {
        var menu = """
                --Bem vindo ao ConsultaFip--
                
                Qual opção deseja pesquisar?
                
                _________ Opções ___________
                
                Carro
                Moto
                Caminhão\n
                """;
        System.out.println(menu);
        var opcao = input.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }
        String json = consumoAPI.consomeAPI(endereco);
        //System.out.println(json);
        var marcas = converteDados.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\n---Qual codigo da marca gostaria?---");
        var codigoMarca = input.nextLine();
        endereco = endereco + "/"+ codigoMarca + "/modelos";

        json = consumoAPI.consomeAPI(endereco);
        System.out.println(json);
        var listaModelos = converteDados.obterDados(json, Modelos.class);
        System.out.println("\nLista de modelos dessa marca: ");
        listaModelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite um techo do modelo que deseja pesquisar:");
        var modeloDesejado = input.nextLine();
        List<Dados> listaModelosDessejados = listaModelos.modelos().stream()
                .filter(d -> d.nome().toLowerCase().contains(modeloDesejado.toLowerCase()))
                .collect(Collectors.toList());

        listaModelosDessejados.forEach(System.out::println);
        System.out.println("\nDigite o codigo do modelo para consulta: ");
        var codigoModelo = input.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoAPI.consomeAPI(endereco);
        List<Dados> listaAnos = converteDados.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < listaAnos.size(); i++) {
            var anoEndereco = endereco + "/" + listaAnos.get(i).codigo();

            json = consumoAPI.consomeAPI(anoEndereco);
            Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        veiculos.forEach(System.out::println);

















    }


}
