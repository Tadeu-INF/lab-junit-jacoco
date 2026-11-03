

/* NIVEL 3 (desafio): crie aqui a classe AnalisadorEmprestimoTest.java
(mesmo pacote: br.ufrgs.inf.lab.nivel3). Nao ha nenhum teste pronto.
Meta: pelo menos um teste para cada regra (1 a 6) do comentario de AnalisadorEmprestimo,
incluindo os valores de fronteira. Rode com:  java scripts/Rodar.java
*/

// DO ANALISADOR EMPRESTIMO

package br.ufrgs.inf.lab.nivel3;

import static org.junit.jupiter.api.Assertions.*;

import br.ufrgs.inf.lab.nivel1.Conta;
import br.ufrgs.inf.lab.nivel2.ServicoTransferencia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AnalisadorEmprestimoTest {
  //copiado do servico transferencia test
    private static final double DELTA = 0.001;

    private ServicoTransferencia servico;
    private Conta origem;
    private Conta destino;

        // Roda ANTES de cada teste: garante contas "limpas" (testes independentes)
    @BeforeEach
    void preparar() {
        servico = new ServicoTransferencia();
        origem = new Conta("Origem", 10_000.0);
        destino = new Conta("Destino", 0.0);
    }


    @Test
    @Disabled("N3-A")
    void entradasDadosInvalida() {
        //*  1. Entradas inválidas -> DADOS_INVALIDOS
        //*       score fora de 0..1000, renda <= 0, valor <= 0, parcelas < 1 ou > 360.
        fail("Escreva o teste N3-A");
    }

    @Test
    @Disabled("N3-B")
    void escoreAbaixoNegado() {
        //  2. score < 300 -> NEGADO (nem calcula parcela).
        fail("Escreva o teste N3-B");
    }

    @Test
    @Disabled("N3-C")
    void taxaJurosConformeEscore() {
        /*  3. Taxa de juros ANUAL conforme o score:
 *       score >= 800 -> 12%   |   score >= 600 -> 24%   |   demais -> 36%
        */
        fail("Escreva o teste N3-C");
    }

    @Test
    @Disabled("N3-D")
    void parcelaMensalPrice() {
        /*  4. Parcela mensal (tabela Price) com taxa mensal = anual / 12:
 *       parcela = valor * i / (1 - (1 + i)^(-n))
        */
        fail("Escreva o teste N3-D");
    }

    @Test
    @Disabled("N3-E")
    void comprometimentoParcelaRenda() {
        /*
         * 5. comprometimento = parcela / renda
         * comprometimento > 0,50 -> NEGADO
         * comprometimento > 0,30 -> ANALISE_MANUAL
         */
         fail("Escreva o teste N3-E");
    }

    @Test
    @Disabled("N3-F")
    void comprometimentoAnaliseManual() {
        /* 6. Caso contrário (comprometimento <= 0,30):
         * /
         * / * score >= 600 -> APROVADO
         * score < 600 -> ANALISE_MANUAL
         */
        fail("Escreva o teste N3-F");
    }



}
