

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
import br.ufrgs.inf.lab.nivel2.StatusTransferencia;
import br.ufrgs.inf.lab.nivel3.AnalisadorEmprestimo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AnalisadorEmprestimoTest {
  //copiado do servico transferencia test
    private static final double DELTA = 0.001;

    private ServicoTransferencia servico;
    private AnalisadorEmprestimo analisador;
    private Conta origem;
    private Conta destino;

    //valores necessários por mim.


        // copiado do resto
        // Roda ANTES de cada teste: garante contas "limpas" (testes independentes)
    @BeforeEach
    void preparar() {
        servico = new ServicoTransferencia();
        analisador = new AnalisadorEmprestimo();
        origem = new Conta("Origem", 10_000.0);
        destino = new Conta("Destino", 0.0);

    }


    @Test
    void entradasDadosInvalida() {
        //*  1. Entradas inválidas -> DADOS_INVALIDOS
        //*       score fora de 0..1000, renda <= 0, valor <= 0, parcelas < 1 ou > 360.


        //pegando cada um dos testes de forma inicial
        DecisaoEmprestimo sAnalise0 = analisador.analisar(1001, 20.3, 40.3, 200);

        DecisaoEmprestimo sAnalise1 = analisador.analisar(950, -5, 40.3, 200);

        DecisaoEmprestimo sAnalise2 = analisador.analisar(950, 20.3, -5, 200);

        DecisaoEmprestimo sAnalise3 = analisador.analisar(950, 20.3, 40.3, 365);

        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, sAnalise0);

        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, sAnalise1);

        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, sAnalise2);

        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, sAnalise3);
        
    }

    @Test
    void e280Negado() {
        //  2. score < 300 -> NEGADO (nem calcula parcela).

        DecisaoEmprestimo sAnalise = analisador.analisar(280, 20.3, 40.3, 200);
        assertEquals(DecisaoEmprestimo.NEGADO, sAnalise);
    }

    @Test
    void taxaJurosConformeEscore() {
        /*  3. Taxa de juros ANUAL conforme o score:
 *       score >= 800 -> 12%   |   score >= 600 -> 24%   |   demais -> 36%
        */
        double taxa1 = analisador.taxaAnual(800);

        double taxa2 = analisador.taxaAnual(600);
        
        double taxa3 = analisador.taxaAnual(300);
        
        assertEquals(0.12, taxa1);

        assertEquals(0.24, taxa2);

        assertEquals(0.36, taxa3);
    }

    @Test
    void parcelaMensalPrice() {
        /*  4. Parcela mensal (tabela Price) com taxa mensal = anual / 12:
 *       parcela = valor * i / (1 - (1 + i)^(-n))
        */

        double valorp = analisador.calcularParcela(5000, 0.02, 6);
        assertEquals(892.6290616760119, valorp);
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
