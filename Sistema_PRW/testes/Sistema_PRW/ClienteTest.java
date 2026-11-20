package Sistema_PRW;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("Alex", "alex@email.com");
    }

    @Test
    public void testNomeEEmail() {
        assertEquals("Alex", cliente.getNome());
        assertEquals("alex@email.com", cliente.getEmail());
    }

    @Test
    public void testGastosIniciais() {
        assertEquals(0.0, cliente.getGastos(), 0.001);
    }

    @Test
    public void testGastosAposVisitas() {
        cliente.registrarVisita();
        cliente.registrarVisita();
        assertEquals(179.8, cliente.getGastos(), 0.001);
    }

    @Test
    public void testPratoFavInicial() {
        assertEquals("Prato preferido não definido", cliente.getPratoFav());
        assertNull(cliente.getRestauranteFavorito());
    }

    @Test
    public void testDefinirRestauranteFavorito() {
        Restaurante r = new Restaurante("Tio João", "Sinfonia Marítima");
        cliente.setRestauranteFavorito(r);
        assertSame(r, cliente.getRestauranteFavorito());
        assertEquals("Sinfonia Marítima", cliente.getPratoFav());
    }

    @Test
    public void testEqualsPorEmail() {
        Cliente outro = new Cliente("Alex Silva", "alex@email.com");
        assertEquals(cliente, outro);
        assertEquals(cliente.hashCode(), outro.hashCode());
    }

    @Test
    public void testNaoEqualsComEmailDiferente() {
        Cliente outro = new Cliente("Alex", "outro@email.com");
        assertNotEquals(cliente, outro);
    }

    @Test
    public void testEqualsComNullEOutroTipo() {
        assertNotEquals(cliente, null);
        assertNotEquals(cliente, "alex@email.com");
        assertEquals(cliente, cliente);
    }
}