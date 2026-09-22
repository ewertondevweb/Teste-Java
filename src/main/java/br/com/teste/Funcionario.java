package br.com.teste;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private final String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void aplicarAumento(BigDecimal percentual) {
        salario = salario.multiply(BigDecimal.ONE.add(percentual));
    }

    public String getFuncao() {
        return funcao;
    }
}
