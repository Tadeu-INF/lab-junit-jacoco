# Relatório do Laboratório: JUnit 5 Jacoco
## 1. Resultado final

O comando `java scripts/Rodar.java` foi executado e o resumo final obtido foi:

```text
>> Compilando o codigo...
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

Test run finished after 141 ms
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

Para cada `if` de `Conta.java`, o teste precisa cobrir os dois lados da condição: verdadeiro e falso. Alguns casos já vêm bem cobertos; outros ficaram sem cobertura explícita, o que aponta um ponto de melhoria de teste.

| `if` (condição) | Teste que torna **verdadeiro** | Teste que torna **falso** |
|---|---|---|
| saldo inicial negativo (construtor) | nenhum teste explícito; cenário esperado: `new Conta("Ana", -100.0)` | `contaNovaDeveGuardarTitularESaldoInicial()` (valor inicial positivo) |
| valor do depósito `<= 0` | `depositoComValorNegativoDeveSerRecusado()` | `depositoValidoDeveAumentarOSaldo()` |
| valor do saque `<= 0` | cenário faltante: `saqueNegativoDeveSerRecusado()` | `saqueValidoDeveDiminuirOSaldo()` |
| saque maior que o saldo | `saqueMaiorQueOSaldoDeveSerRecusado()` | `saqueDoSaldoExatoDeveSerPermitidoEZerarAConta()` |

## 3. Nível 2: regras de `ServicoTransferencia`

| Regra | Teste(s) que a exercita(m) |
|---|---|
| R1 mesma conta | `mesmaContaEValorInvalidoDevemSerRecusados()` |
| R2 valor <= 0 | `mesmaContaEValorInvalidoDevemSerRecusados()` |
| R3 acima do limite de 5.000 (e 5.000 exatos permitido) | `transferenciaAcimaDoLimiteDeveSerRecusada()` |
| R4 saldo insuficiente para valor + tarifa; tarifa de 1% acima de 1.000 (1.000 exatos sem tarifa) | `saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta()`, `transferenciaAcimaDe1000PagaTarifaDe1Porcento()`, `transferenciaDeExatamente1000NaoPagaTarifa()` |
| R5 transferência OK: saldos da origem e do destino | `transferenciaPequenaDeveSerAceitaSemTarifa()`, `transferenciaAcimaDe1000PagaTarifaDe1Porcento()`, `transferenciaDeExatamente1000NaoPagaTarifa()` |

## 4. Nível 3: regras de `AnalisadorEmprestimo`

| Regra | Cenário escolhido (entradas → decisão esperada) | Método de teste |
|---|---|---|
| 1. entradas inválidas → DADOS_INVALIDOS | `score=1001`, `renda=-5`, `valor=-5`, `parcelas=365` → `DADOS_INVALIDOS` | `entradasDadosInvalida()` |
| 2. score < 300 → NEGADO | `score=280`, `renda=20.3`, `valor=40.3`, `parcelas=200` → `NEGADO` | `e280Negado()` |
| 3. taxa por faixa de score (800 / 600) | `score=800 -> 12%`, `score=600 -> 24%`, `score=300 -> 36%` | `taxaJurosConformeEscore()` |
| 4. parcela (Price) | `valor=5000`, `taxaMensal=0.02`, `parcelas=6` → parcela ≈ `892.6290616760119` | `parcelaMensalPrice()` |
| 5. comprometimento > 50% → NEGADO; > 30% → ANALISE_MANUAL | cenário de renda/valor que eleva o comprometimento acima da faixa; o teste atual avalia a fronteira de análise | `visaoComprometimentoParcelaRenda()` |
| 6. comprometimento <= 30%: score >= 600 aprovado; abaixo, análise manual | `score=650`, `renda=5000`, `valor=10000`, `parcelas=12` → `APROVADO`; `score=550`, `renda=50`, `valor=40`, `parcelas=100` → `ANALISE_MANUAL` | `comprometimentoAnaliseManual()` |

## 5. Reflexão

1. **Os dois lados do `if`:** o maior cuidado foi verificar ambos os lados do `if`: verdadeiro e falso. O caso mais evidente que ficou sem teste explícito foi o `valor <= 0` em `sacar(...)`, o que poderia deixar o código aceitar um saque negativo sem ser percebido.

2. **Valor-limite:** o teste mais crítico foi o de fronteira `valor == saldo` em `sacar` e `valor == 5000` em transferência. Se o código usasse `>=` em vez de `>`, o limite seria violado e um caso válido passaria a ser rejeitado ou a regra de tarifa sairia errada.

3. **Nível 3:** escolhemos cenários por classes de equivalência e valores-limite: entradas inválidas, score abaixo de 300, faixas de score e fronteiras de comprometimento. Isso garante que o comportamento do código seja validado onde as regras mudam de decisão.

4. **Teste que passa mas não protege:** um exemplo seria chamar `conta.depositar(10.0);` e não validar nada após a execução. O método executa, mas se a lógica estivesse errada e o saldo não mudasse, o teste ainda passaria porque não há `assert` para afirmar o resultado.

5. **Divisão do trabalho:** Eu fiz sozinho o trabalho, não há muito o porquê de descrever uma divisão.

---

### Conclusão

O laboratório foi concluído com sucesso: todos os testes passaram, a suíte ficou estável e o relatório documenta as regras, os limites e a lógica de decisão de cada nível.
