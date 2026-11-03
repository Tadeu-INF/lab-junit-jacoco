package br.ufrgs.inf.lab.nivel2;

import static org.junit.jupiter.api.Assertions.*;

import br.ufrgs.inf.lab.nivel1.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * NÍVEL 2 (semi-guiado): ServicoTransferencia.
 *
 * Aqui NÃO há os asserts: cada teste abaixo diz o CENÁRIO e o que deve
 * ser verificado. Você decide como escrever (assertEquals, assertTrue...).
 * Leia as regras R1..R5 em ServicoTransferencia.java.
 *
 * Remova o @Disabled e o fail(...) de cada teste à medida que o implementar.
 * Sinta-se livre para ACRESCENTAR testes (e usar @ParameterizedTest).
 */
class ServicoTransferenciaTest {

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

    // ---------------------------------------------------------------
    // PRONTO (exemplo)
    // ---------------------------------------------------------------

    @Test
    void transferenciaPequenaDeveSerAceitaSemTarifa() {
        StatusTransferencia status = servico.transferir(origem, destino, 200.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(9_800.0, origem.getSaldo(), DELTA);
        assertEquals(200.0, destino.getSaldo(), DELTA);
    }

    // ---------------------------------------------------------------
    // PARA VOCÊ ESCREVER
    // ---------------------------------------------------------------

    @Test
    void transferenciaAcimaDe1000PagaTarifaDe1Porcento() {
        // DADO   origem com 10.000 e destino com 0

        //Já existe.
        
        // QUANDO transfere 2.000
        StatusTransferencia status = servico.transferir(origem, destino, 2_000.0);

        // ENTÃO  status = ?  | calcularTarifa(2000) = ?  | saldo da origem = ?  | saldo do destino = ?
        assertEquals(StatusTransferencia.OK, status);
        assertEquals(20.0, servico.calcularTarifa(2_000.00), DELTA);
        assertEquals(7_980.00, origem.getSaldo(), DELTA);
        assertEquals(2_000.00, destino.getSaldo(), DELTA);
        //        (calcule os valores esperados na mão ANTES de rodar!)
    }

    @Test
    
    void transferenciaDeExatamente1000NaoPagaTarifa() {
        // Valor-limite da regra R4: "ACIMA de 1.000" ... e 1.000 exatos?

        StatusTransferencia status = servico.transferir(origem, destino, 1_000.0);

        // Verifique o status,
        assertEquals(StatusTransferencia.OK, status);

        //  a tarifa 
        assertEquals(0.0, servico.calcularTarifa(1_000.00), DELTA);

        // e os saldos.
        assertEquals(9_000.00, origem.getSaldo(), DELTA);
        assertEquals(1_000.00, destino.getSaldo(), DELTA);
        
    }

    @Test
    void transferenciaAcimaDoLimiteDeveSerRecusada() {
        // Qual status? Qual o valor mínimo que ultrapassa o limite (R3)?
    
           
        StatusTransferencia status1 = servico.transferir(origem, destino, 5_000.01);
        
        assertEquals(StatusTransferencia.ACIMA_DO_LIMITE, status1);
        // Verifique também que nenhuma conta foi alterada.
        assertEquals(10_000.00, origem.getSaldo(), DELTA);
        assertEquals(0.00, destino.getSaldo(), DELTA);
        
        StatusTransferencia status = servico.transferir(origem, destino, 5_000.0);
        // E um teste com EXATAMENTE o limite: o que deveria acontecer?
        //permitido
        assertEquals(StatusTransferencia.OK, status);
        assertEquals(4_950.00, origem.getSaldo(), DELTA);
        assertEquals(5_000.00, destino.getSaldo(), DELTA);
        }

    @Test

    void saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() {
    
        // DICA: crie uma conta de origem com saldo suficiente para o VALOR,
    Conta origem2 = new Conta("Origem2", 2_000.0);

        //       mas NÃO suficiente para VALOR + TARIFA (ex.: saldo 2.000, valor 2.000).
    StatusTransferencia status = servico.transferir(origem2, destino, 2_000.0);
    assertEquals(StatusTransferencia.SALDO_INSUFICIENTE, status);
        // Verifique o status e que origem E destino mantêm os saldos originais.
    assertEquals(2_000.00, origem2.getSaldo(), DELTA);
        assertEquals(0.00, destino.getSaldo(), DELTA);

    }

    @Test
        void mesmaContaEValorInvalidoDevemSerRecusados() {

        // Cubra R1 (mesma conta)
        StatusTransferencia status = servico.transferir(origem, origem, 2_000.0);
        assertEquals(StatusTransferencia.MESMA_CONTA, status);
        //  e R2 (valor zero e valor negativo).

        StatusTransferencia status2 = servico.transferir(destino, origem, 1_000.0);
        StatusTransferencia status3 = servico.transferir(destino, origem, -500.0);
        assertEquals(StatusTransferencia.SALDO_INSUFICIENTE, status2);
        assertEquals(StatusTransferencia.VALOR_INVALIDO, status3);
        // Quantos testes/asserts você precisa para exercitar os DOIS lados de cada "if"?
        // Em todos os casos, confira o status E que os saldos não mudaram.
        assertEquals(10_000.00, origem.getSaldo(), DELTA);
        assertEquals(0.00, destino.getSaldo(), DELTA);
     }
}
