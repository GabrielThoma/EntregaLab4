package Sistema_PRW;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LivroDeVisitasTest {

    private LivroDeVisitas livro;
    private Cliente alex;
    private Cliente maria;
    private LocalDate data;

    @BeforeEach
    public void setUp() {
        livro = new LivroDeVisitas();
        alex = new Cliente("Alex", "alex@email.com");
        maria = new Cliente("Maria", "maria@email.com");
        data = LocalDate.of(2026, 11, 18);
    }

    @Test
    public void testLivroVazio() {
        assertEquals(0, livro.getTotalVisitas());
        assertEquals(0, livro.listar().length);
    }

    @Test
    public void testAddVisita() {
        livro.addVisita(data, alex, "Ótimo");
        assertEquals(1, livro.getTotalVisitas());
    }

    @Test
    public void testVisitasRepetidasContam() {
        livro.addVisita(data, alex, null);
        livro.addVisita(data, alex, null);
        assertEquals(2, livro.getTotalVisitas());
    }

    @Test
    public void testListarTodasNaOrdemDeInsercao() {
        livro.addVisita(data, alex, "Ótimo");
        livro.addVisita(data, maria, null);
        String[] lista = livro.listar();
        assertEquals(2, lista.length);
        assertEquals("Cliente: Alex | Data: 2026-11-18 | Comentário: Ótimo", lista[0]);
        assertEquals("Cliente: Maria | Data: 2026-11-18", lista[1]);
    }

    @Test
    public void testListarRecentes() {
        livro.addVisita(data, alex, "Primeira");
        livro.addVisita(data, maria, "Segunda");
        livro.addVisita(data, alex, "Terceira");
        String[] lista = livro.listar(2);
        assertEquals(2, lista.length);
        assertTrue(lista[0].contains("Segunda"));
        assertTrue(lista[1].contains("Terceira"));
    }

    @Test
    public void testListarRecentesMaiorQueTotal() {
        livro.addVisita(data, alex, null);
        assertEquals(1, livro.listar(10).length);
    }

    @Test
    public void testListarRecentesZeroENegativo() {
        livro.addVisita(data, alex, null);
        assertEquals(0, livro.listar(0).length);
        assertEquals(0, livro.listar(-3).length);
    }

    @Test
    public void testContarVisitas() {
        livro.addVisita(data, alex, null);
        livro.addVisita(data, maria, null);
        livro.addVisita(data, alex, null);
        assertEquals(2, livro.contarVisitas(alex));
        assertEquals(1, livro.contarVisitas(maria));
    }

    @Test
    public void testContarVisitasClienteSemVisita() {
        livro.addVisita(data, alex, null);
        assertEquals(0, livro.contarVisitas(maria));
    }
}