package br.com.teste;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase("João"));
        imprimirTitulo("Funcionários após remover João");
        imprimirFuncionarios(funcionarios);

        funcionarios.forEach(funcionario -> funcionario.aplicarAumento(new BigDecimal("0.10")));

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        imprimirTitulo("Funcionários agrupados por função (após aumento de 10%)");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            imprimirFuncionarios(lista);
        });

        imprimirTitulo("Aniversariantes dos meses 10 e 12");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10
                        || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(Principal::imprimirFuncionario);

        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow();
        imprimirTitulo("Funcionário com maior idade");
        System.out.printf("%s - %d anos%n", maisVelho.getNome(), calcularIdade(maisVelho));

        imprimirTitulo("Funcionários em ordem alfabética");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .forEach(Principal::imprimirFuncionario);

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        imprimirTitulo("Total dos salários");
        System.out.println(formatarMoeda(totalSalarios));

        imprimirTitulo("Quantidade de salários mínimos por funcionário");
        funcionarios.forEach(funcionario -> System.out.printf("%s: %.2f salários mínimos%n",
                funcionario.getNome(), funcionario.getSalario().divide(SALARIO_MINIMO, 2, java.math.RoundingMode.HALF_UP)));
    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(novoFuncionario("Maria", "18/10/2000", "2009.44", "Operador"));
        funcionarios.add(novoFuncionario("João", "12/05/1990", "2284.38", "Operador"));
        funcionarios.add(novoFuncionario("Caio", "02/05/1961", "9836.14", "Coordenador"));
        funcionarios.add(novoFuncionario("Miguel", "14/10/1988", "19119.88", "Diretor"));
        funcionarios.add(novoFuncionario("Alice", "05/01/1995", "2234.68", "Recepcionista"));
        funcionarios.add(novoFuncionario("Heitor", "29/11/1999", "1582.72", "Operador"));
        funcionarios.add(novoFuncionario("Arthur", "31/03/1993", "4071.84", "Contador"));
        funcionarios.add(novoFuncionario("Laura", "08/07/1994", "3017.45", "Gerente"));
        funcionarios.add(novoFuncionario("Heloísa", "24/05/2003", "1606.85", "Eletricista"));
        funcionarios.add(novoFuncionario("Helena", "02/09/1996", "2799.93", "Gerente"));
        return funcionarios;
    }

    private static Funcionario novoFuncionario(String nome, String nascimento, String salario, String funcao) {
        return new Funcionario(nome, LocalDate.parse(nascimento, FORMATO_DATA), new BigDecimal(salario), funcao);
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.forEach(Principal::imprimirFuncionario);
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.printf("Nome: %s | Nascimento: %s | Salário: %s | Função: %s%n",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(FORMATO_DATA),
                formatarMoeda(funcionario.getSalario()),
                funcionario.getFuncao());
    }

    private static void imprimirTitulo(String titulo) {
        System.out.println("\n=== " + titulo + " ===");
    }

    private static String formatarMoeda(BigDecimal valor) {
        return String.format(new Locale("pt", "BR"), "%,.2f", valor);
    }

    private static int calcularIdade(Funcionario funcionario) {
        return Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears();
    }
}
