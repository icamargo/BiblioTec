/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro
 */
public class ControleEmprestimoTest {
    
    private Emprestimo emprestimo;
    private ControleEmprestimo controle;
    private LivroPrototype livro, livro2;
    private Reserva reserva;
    private UsuarioPrototype usuario;
    
    public ControleEmprestimoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        emprestimo = new Emprestimo();
        controle = new ControleEmprestimo();
        livro = new LivroPrototype();
        livro2 = new LivroPrototype();
        reserva = new Reserva();
        usuario = new UsuarioPrototype();
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of devolucao method, of class ControleEmprestimo.
     * @throws java.lang.Exception
     */
    @Test
    public void testDevolucao() throws Exception {

    }

    /**
     * Test of multa method, of class ControleEmprestimo.
     */
    @Test
    public void testMulta() {
        livro.setValorMultaDiaAtraso(2);
        livro2.setValorMultaDiaAtraso(3);
        emprestimo.setLivro(livro);
        emprestimo.setLivro2(livro2);
        emprestimo.setLivro3(null);
        
        assertEquals(5, controle.multa(emprestimo), 0);
    }

    /**
     * Test of verificaReservaDoLivro method, of class ControleEmprestimo.
     */
    @Test
    public void testVerificaReservaDoLivro() {

    }

    /**
     * Test of renovar method, of class ControleEmprestimo.
     * @throws java.lang.Exception
     */
    @Test
    public void testRenovar() throws Exception {
    }

    /**
     * Test of criarEmprestimo method, of class ControleEmprestimo.
     * @throws java.lang.Exception
     */
    @Test
    public void testCriarEmprestimo() throws Exception {
       controle.setCodigoUsuario(6);
       controle.setNumeroCatalogo(2);
       controle.setNumeroCatalogo2(3);
       controle.setNumeroCatalogo3(10);
    }

    /**
     * Test of verificaReservaDoUsuario method, of class ControleEmprestimo.
     */
    @Test
    public void testVerificaReservaDoUsuario() {
        
    }

    /**
     * Test of buscaUltimoEmprestimo method, of class ControleEmprestimo.
     */
    @Test
    public void testBuscaUltimoEmprestimo() {

    }  
}
