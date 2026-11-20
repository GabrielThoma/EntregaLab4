package Sistema_PRW;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestauranteTest {

    private Restaurante restaurante;
    private Cliente alex;
    private Cliente maria;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante("Tio João", "Sinfonia Marítima");
        alex = new Cliente("Alex", "alex@email.com");
        maria = new Cliente("Maria Eduarda", "maria@email.com");
    }

    @Test
    public void testNomeEPrato() {
        assertEquals("Tio João", restaurante.getNome());
        assertEquals("Sinfonia Marítima", restaurante.getPrato());
    }

    @Test
    public void testVotosIniciais() {
        assertEquals(0, restaurante.getQtdVotos());
    }

    @Test
    public void testAddVoto() {
        restaurante.addVoto();
        restaurante.addVoto();
        assertEquals(2, restaurante.getQtdVotos());
    }

    @Test
    public void testRemoverVoto() {
        restaurante.addVoto();
        restaurante.removerVoto();
        assertEquals(0, restaurante.getQtdVotos());
    }

    @Test
    public void testRemoverVotoNaoFicaNegativo() {
        restaurante.removerVoto();
        assertEquals(0, restaurante.getQtdVotos());
    }

    @Test
    public void testAddClienteNaoDuplica() {
        restaurante.addCliente(alex);
        restaurante.addCliente(alex);
        assertEquals(1, restaurante.getQtdClientes());
    }

    @Test
    public void testPodeVotar() {
        restaurante.addCliente(alex);
        assertTrue(restaurante.podeVotar(alex));
        assertFalse(restaurante.podeVotar(maria));
    }

    @Test
    public void testGetClientesSemFavorito() {
        restaurante.addCliente(maria);
        String[] lista = restaurante.getClientes();
        assertEquals(1, lista.length);
        assertEquals("Cliente: Maria Eduarda", lista[0]);
    }

    @Test
    public void testGetClientesComFavorito() {
        restaurante.addCliente(alex);
        restaurante.addCliente(maria);
        alex.setRestauranteFavorito(restaurante);
        String[] lista = restaurante.getClientes();
        assertEquals(2, lista.length);
        assertEquals("Cliente: Alex, Prato preferido da casa", lista[0]);
        assertEquals("Cliente: Maria Eduarda", lista[1]);
    }

    @Test
    public void testGetClientesFavoritoDeOutroRestaurante() {
        Restaurante outro = new Restaurante("Sol e Mar", "Sinfonia Marítima");
        restaurante.addCliente(alex);
        alex.setRestauranteFavorito(outro);
        assertEquals("Cliente: Alex", restaurante.getClientes()[0]);
    }

    @Test
    public void testGetClientesVazio() {
        assertEquals(0, restaurante.getClientes().length);
    }

    @Test
    public void testListaClientes() {
        restaurante.addCliente(alex);
        assertTrue(restaurante.getListaClientes().contains(alex));
        assertEquals(1, restaurante.getListaClientes().size());
    }

    @Test
    public void testTotalVisitasEReceita() {
        restaurante.getLivro().addVisita(LocalDate.now(), alex, null);
        restaurante.getLivro().addVisita(LocalDate.now(), alex, "Bom");
        restaurante.getLivro().addVisita(LocalDate.now(), maria, null);
        assertEquals(3, restaurante.getTotalVisitas());
        assertEquals(269.7, restaurante.getReceita(), 0.001);
    }

    @Test
    public void testReceitaSemVisitas() {
        assertEquals(0.0, restaurante.getReceita(), 0.001);
    }

    @Test
    public void testLivroNaoNulo() {
        assertNotNull(restaurante.getLivro());
    }
}