package Sistema_PRW;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VisitaTest {

    private Cliente alex;
    private LocalDate data;

    @BeforeEach
    public void setUp() {
        alex = new Cliente("Alex", "alex@email.com");
        data = LocalDate.of(2026, 11, 18);
    }

    @Test
    public void testGetters() {
        Visita v = new Visita(data, alex, "Ótimo prato");
        assertEquals(data, v.getData());
        assertSame(alex, v.getCliente());
        assertEquals("Ótimo prato", v.getComentario());
    }

    @Test
    public void testListarInfosComComentario() {
        Visita v = new Visita(data, alex, "Ótimo prato");
        assertEquals("Cliente: Alex | Data: 2026-11-18 | Comentário: Ótimo prato", v.listarInfos());
    }

    @Test
    public void testListarInfosSemComentario() {
        Visita v = new Visita(data, alex, null);
        assertEquals("Cliente: Alex | Data: 2026-11-18", v.listarInfos());
    }

    @Test
    public void testListarInfosComentarioVazio() {
        Visita v = new Visita(data, alex, "");
        assertEquals("Cliente: Alex | Data: 2026-11-18", v.listarInfos());
    }

    @Test
    public void testListarInfosComentarioEmBranco() {
        Visita v = new Visita(data, alex, "   ");
        assertEquals("Cliente: Alex | Data: 2026-11-18", v.listarInfos());
    }
}