package br.ufrgs.inf.lab.nivel1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * NÍVEL 1 (guiado): Conta.
 *
 * Os dois primeiros testes já estão prontos: leia e rode.
 * Os demais estão com @Disabled: complete cada um seguindo os PASSOS,
 * REMOVA a linha @Disabled e a linha fail(...) e rode de novo.
 *
 * Padrão usado em todos os testes (AAA):
 *   Arrange (preparar)  ->  Act (agir)  ->  Assert (verificar)
 */
class ContaTest {

    // Tolerância para comparar números decimais (double) com assertEquals
    private static final double DELTA = 0.001;

    // ---------------------------------------------------------------
    // PRONTOS (exemplos para você imitar)
    // ---------------------------------------------------------------

    @Test
    void contaNovaDeveGuardarTitularESaldoInicial() {
        // Arrange + Act
        Conta conta = new Conta("Ana", 100.0);

        // Assert: assertEquals(esperado, obtido)
        assertEquals("Ana", conta.getTitular());
        assertEquals(100.0, conta.getSaldo(), DELTA);
    }

    @Test
    void depositoValidoDeveAumentarOSaldo() {
        Conta conta = new Conta("Ana", 100.0);   // Arrange

        boolean depositou = conta.depositar(50.0); // Act

        assertTrue(depositou);                          // Assert: o método devolveu true
        assertEquals(150.0, conta.getSaldo(), DELTA);   // Assert: o saldo mudou
    }

    // ---------------------------------------------------------------
    // PARA VOCÊ COMPLETAR (remova @Disabled quando terminar cada um)
    // ---------------------------------------------------------------

    @Test
//    @Disabled("N1-A: complete e remova esta linha")
    void depositoComValorNegativoDeveSerRecusado() {
        Conta conta = new Conta("Ana", 100.0);
        
        // PASSO 1: chame conta.depositar(-10.0) e guarde o resultado numa variável boolean.
        boolean negativado = conta.depositar(-10.0);


        // PASSO 2: verifique com assertFalse(...) que o depósito foi recusado.
        assertFalse(negativado);

        // PASSO 3: verifique com assertEquals(esperado, obtido, DELTA) que o saldo NÃO mudou
        //          (continua 100.0).
        assertEquals(100.0, conta.getSaldo(), DELTA);

    }

    @Test
    //@Disabled("N1-B: complete e remova esta linha")
    void saqueValidoDeveDiminuirOSaldo() {
        Conta conta = new Conta("Ana", 100.0);

        // PASSO 1: faça um saque de 30.0 (conta.sacar(...)) e guarde o resultado.
        boolean sacando = conta.sacar(30.0);        
        // PASSO 2: verifique com assertTrue(...) que o saque foi aceito.
        assertTrue(sacando);
        // PASSO 3: verifique com assertEquals que o saldo agora é 70.0.
        assertEquals(70.0, conta.getSaldo(), DELTA);
    }

    @Test
//    @Disabled("N1-C: complete e remova esta linha")
    void saqueMaiorQueOSaldoDeveSerRecusado() {
        Conta conta = new Conta("Ana", 100.0);

        // PASSO 1: tente sacar 100.01 e guarde o resultado.
        boolean recusavel = conta.sacar(100.01);
        // PASSO 2: verifique com assertFalse(...) que o saque foi recusado.
        assertFalse(recusavel);
        // PASSO 3: verifique que o saldo continua 100.0.
        assertEquals(100.0, conta.getSaldo(), DELTA);
    }

    @Test
//    @Disabled("N1-D: complete e remova esta linha")
    void saqueDoSaldoExatoDeveSerPermitidoEZerarAConta() {
        Conta conta = new Conta("Ana", 100.0);

        // Este é um TESTE DE VALOR-LIMITE (fronteira): sacar exatamente o saldo.
        // PASSO 1: saque 100.0 e guarde o resultado.
        boolean exacto = conta.sacar(100.0);
        // PASSO 2: verifique com assertTrue(...) que o saque foi aceito.
        assertTrue(exacto);
        // PASSO 3: verifique com assertEquals que o saldo é 0.0.
        assertEquals(0.0, conta.getSaldo(), DELTA);
        // PASSO 4 (reflexão): por que o código usa "valor > saldo" e não "valor >= saldo"?
        
        // Porquê o saque do valor total é permitido.
    }
}
