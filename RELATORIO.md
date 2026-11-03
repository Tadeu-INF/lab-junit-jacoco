# Relatório do Laboratório: JUnit 5

**Dupla:** _Nome 1_ e _Nome 2_
**Link do fork:** _https://github.com/..._

## 1. Resultado final

Cole aqui o resumo que aparece no final de `java scripts/Rodar.java`:

```
 Compilando o codigo...
>> Compilando os testes...
>> Rodando os testes...

.
+-- JUnit Platform Suite [OK]
+-- JUnit Jupiter [OK]
| +-- ContaTest [OK]
| | +-- saqueValidoDeveDiminuirOSaldo() [OK]
| | +-- contaNovaDeveGuardarTitularESaldoInicial() [OK]
| | +-- saqueMaiorQueOSaldoDeveSerRecusado() [OK]
| | +-- depositoValidoDeveAumentarOSaldo() [OK]
| | +-- depositoComValorNegativoDeveSerRecusado() [OK]
| | '-- saqueDoSaldoExatoDeveSerPermitidoEZerarAConta() [OK]
| +-- ServicoTransferenciaTest [OK]
| | +-- saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() [OK]
| | +-- transferenciaAcimaDe1000PagaTarifaDe1Porcento() [OK]
| | +-- transferenciaPequenaDeveSerAceitaSemTarifa() [OK]
| | +-- mesmaContaEValorInvalidoDevemSerRecusados() [OK]
| | +-- transferenciaAcimaDoLimiteDeveSerRecusada() [OK]
| | '-- transferenciaDeExatamente1000NaoPagaTarifa() [OK]
| '-- AnalisadorEmprestimoTest [OK]
|   +-- e280Negado() [OK]
|   +-- entradasDadosInvalida() [OK]
|   +-- visaoComprometimentoParcelaRenda() [OK]
|   +-- parcelaMensalPrice() [OK]
|   +-- comprometimentoAnaliseManual() [OK]
|   '-- taxaJurosConformeEscore() [OK]
'-- JUnit Vintage [OK]

Test run finished after 79 ms
[         6 containers found      ]
[         0 containers skipped    ]
[         6 containers started    ]
[         0 containers aborted    ]
[         6 containers successful ]
[         0 containers failed     ]
[        18 tests found           ]
[         0 tests skipped         ]
[        18 tests started         ]
[         0 tests aborted         ]
[        18 tests successful      ]
[         0 tests failed          ]
```

## 2. Nível 1: `if`s de `Conta`

Para cada `if` de `Conta.java`, diga qual teste faz o `if` ser **verdadeiro** e qual faz ser **falso**
(escreva o nome do método de teste).

| `if` (condição) | Teste que torna **verdadeiro** | Teste que torna **falso** |
|---|---|---|
| saldo inicial negativo (construtor) |         Conta conta = new Conta("Ana", -100.0);
|         Conta conta = new Conta("Ana", 100.0);
|
| valor do depósito `<= 0` |         boolean inserindo = conta.depositar(10.0);
|         boolean negativado = conta.depositar(-10.0);
|
| valor do saque `<= 0` | sacando = conta.sacar(30.0) | sacando = conta.sacar(-30.0) |
| saque maior que o saldo |         boolean recusavel = conta.sacar(100.01);
 |         boolean recusavel = conta.sacar(100.00);
|

## 3. Nível 2: regras de `ServicoTransferencia`

| Regra | Teste(s) que a exercita(m) |
|---|---|
| R1 mesma conta | mesmaContaEValorInvalidoDevemSerRecusados()|
| R2 valor <= 0 |  mesmaContaEValorInvalidoDevemSerRecusados()|
| R3 acima do limite de 5.000 (e 5.000 exatos permitido) | transferenciaAcimaDoLimiteDeveSerRecusada()
|
| R4 saldo insuficiente para valor + tarifa (nada é alterado); tarifa de 1% acima de 1.000 (1.000 exatos sem tarifa) | transferenciaAcimaDe1000PagaTarifaDe1Porcento ()
transferenciaDeExatamente1000NaoPagaTarifa()|
| R5 transferência OK: saldos da origem e do destino | transferenciaPequenaDeveSerAceitaSemTarifa(), transferenciaAcimaDe1000PagaTarifaDe1Porcento(), transferenciaDeExatamente1000NaoPagaTarifa()|

## 4. Nível 3: regras de `AnalisadorEmprestimo`

| Regra | Cenário escolhido (entradas → decisão esperada) | Método de teste |
|---|---|---|
| 1. entradas inválidas → DADOS_INVALIDOS (cada uma das 6 condições) | | |
| 2. score < 300 → NEGADO | | |
| 3. taxa por faixa de score (800 / 600) | | |
| 4. parcela (Price) | | |
| 5. comprometimento > 50% → NEGADO; > 30% → ANÁLISE MANUAL | | |
| 6. comprometimento <= 30%: score >= 600 aprovado; abaixo, análise manual | | |

## 5. Reflexão (2 a 4 linhas cada)

1. **Os dois lados do `if`:** qual `if` ficou sem teste de um dos lados no começo? Que bug passaria
   despercebido por isso?
2. **Valor-limite:** qual teste de fronteira (ex.: 1.000,00 / 5.000,00 / score 600) vocês acham
   mais importante? Que bug ele pegaria se o código usasse `>=` no lugar de `>`?
3. **Nível 3:** como vocês decidiram quais cenários testar? (classes de equivalência, fronteiras,
   leitura das regras...)
4. **Teste que passa mas não protege:** descrevam um teste que executa o código e passa, mas não
   verificaria um erro (dica: um teste sem `assert`).
5. **Divisão do trabalho:** quem escreveu o quê? (uma linha)
