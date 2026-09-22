# TESTE PRÁTICO PROGRAMAÇÃO

Projeto Java para o teste prático de desenvolvimento full stack.

## Requisitos

- Java 17 ou superior
- Maven 3.8 ou superior (opcional, para compilar pelo Maven)

## Como executar

No terminal, na pasta do projeto:

```bash
mvn compile exec:java -Dexec.mainClass="br.com.teste.Principal"
```

Ou compile e execute apenas com o JDK:

```bash
javac -encoding UTF-8 -d out src/main/java/br/com/teste/*.java
java -cp out br.com.teste.Principal
```

O programa insere os funcionários da tabela, remove João, aplica o aumento de 10% e executa todas as operações solicitadas.
