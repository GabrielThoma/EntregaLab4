package Sistema_PRW;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Sistema_PRWTest {

    private Sistema_PRW sistema;

    @BeforeEach
    public void setUp() {
        sistema = new Sistema_PRW();
        sistema.adicionarRestaurante("Tio João", "Sinfonia Marítima");
        sistema.adicionarRestaurante("Sol e Mar", "Moqueca");
        sistema.adicionarCliente("Alex", "alex@email.com");
        sistema.adicionarCliente("Maria Eduarda", "maria@email.com");
    }

    @Test
    public void testAdicionarRestauranteValido() {
        assertTrue(sistema.adicionarRestaurante("Novo", "Prato Novo"));
    }

    @Test
    public void testAdicionarRestauranteRepetido() {
        assertFalse(sistema.adicionarRestaurante("Tio João", "Outro Prato"));
    }

    @Test
    public void testAdicionarRestauranteInvalido() {
        assertFalse(sistema.adicionarRestaurante(null, "Prato"));
        assertFalse(sistema.adicionarRestaurante("", "Prato"));
        assertFalse(sistema.adicionarRestaurante("Nome", null));
        assertFalse(sistema.adicionarRestaurante("Nome", "   "));
    }

    @Test
    public void testLimiteDeRestaurantes() {
        for (int i = 3; i <= 50; i++) {
            assertTrue(sistema.adicionarRestaurante("Rest" + i, "Prato" + i));
        }
        assertFalse(sistema.adicionarRestaurante("Rest51", "Prato51"));
    }

    @Test
    public void testAdicionarClienteValido() {
        assertTrue(sistema.adicionarCliente("Novo", "novo@email.com"));
    }

    @Test
    public void testAdicionarClienteEmailRepetido() {
        assertFalse(sistema.adicionarCliente("Outro Alex", "alex@email.com"));
    }

    @Test
    public void testAdicionarClienteInvalido() {
        assertFalse(sistema.adicionarCliente(null, "a@email.com"));
        assertFalse(sistema.adicionarCliente("", "a@email.com"));
        assertFalse(sistema.adicionarCliente("Nome", null));
        assertFalse(sistema.adicionarCliente("Nome", " "));
    }

    @Test
    public void testLimiteDeClientes() {
        for (int i = 3; i <= 100; i++) {
            assertTrue(sistema.adicionarCliente("Cliente" + i, "c" + i + "@email.com"));
        }
        assertFalse(sistema.adicionarCliente("Cliente101", "c101@email.com"));
    }

    @Test
    public void testGastoSemVisitas() {
        assertEquals(0.0, sistema.obtemGastoCliente("alex@email.com"), 0.001);
    }

    @Test
    public void testGastoComVisitasRepetidas() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Tio João", "Ótimo");
        assertEquals(179.8, sistema.obtemGastoCliente("alex@email.com"), 0.001);
    }

    @Test
    public void testGastoClienteInexistente() {
        assertEquals(0.0, sistema.obtemGastoCliente("naoexiste@email.com"), 0.001);
    }

    @Test
    public void testVisitaComDadosInexistentesNaoQuebra() {
        sistema.visitarRestaurante("naoexiste@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Naoexiste");
        assertEquals(0, sistema.listarClientesRestaurante("Tio João").length);
        assertEquals(0.0, sistema.obtemGastoCliente("alex@email.com"), 0.001);
    }

    @Test
    public void testListarClientesSemFavorito() {
        sistema.visitarRestaurante("maria@email.com", "Tio João");
        String[] lista = sistema.listarClientesRestaurante("Tio João");
        assertEquals(1, lista.length);
        assertEquals("Cliente: Maria Eduarda", lista[0]);
    }

    @Test
    public void testListarClientesNaoDuplicaClienteQueVisitaDuasVezes() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        assertEquals(1, sistema.listarClientesRestaurante("Tio João").length);
    }

    @Test
    public void testListarClientesComPratoPreferidoDaCasa() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("maria@email.com", "Tio João");
        sistema.votarPratoPreferido("alex@email.com", "Tio João");
        String[] lista = sistema.listarClientesRestaurante("Tio João");
        assertEquals("Cliente: Alex, Prato preferido da casa", lista[0]);
        assertEquals("Cliente: Maria Eduarda", lista[1]);
    }

    @Test
    public void testListarClientesRestauranteInexistente() {
        assertEquals(0, sistema.listarClientesRestaurante("Naoexiste").length);
    }

    @Test
    public void testVotoSemTerVisitado() {
        assertEquals("Voto não registrado", sistema.votarPratoPreferido("alex@email.com", "Tio João"));
    }

    @Test
    public void testVotoComDadosInexistentes() {
        assertEquals("Voto não registrado", sistema.votarPratoPreferido("naoexiste@email.com", "Tio João"));
        assertEquals("Voto não registrado", sistema.votarPratoPreferido("alex@email.com", "Naoexiste"));
    }

    @Test
    public void testVotoComSucesso() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        assertEquals("Voto registrado com sucesso!", sistema.votarPratoPreferido("alex@email.com", "Tio João"));
    }

    @Test
    public void testTrocaDeVotoRemoveVotoAnterior() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Sol e Mar");
        sistema.votarPratoPreferido("alex@email.com", "Tio João");
        sistema.votarPratoPreferido("alex@email.com", "Sol e Mar");
        assertEquals("Melhor prato: Moqueca Restaurante: Sol e Mar", sistema.melhorPrato());
        String[] lista = sistema.listarClientesRestaurante("Tio João");
        assertEquals("Cliente: Alex", lista[0]);
    }

    @Test
    public void testMesmoClienteVotandoDuasVezesNoMesmoRestaurante() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("maria@email.com", "Sol e Mar");
        sistema.votarPratoPreferido("maria@email.com", "Sol e Mar");
        sistema.votarPratoPreferido("alex@email.com", "Tio João");
        sistema.votarPratoPreferido("alex@email.com", "Tio João");
        assertEquals("Melhor prato: Moqueca Restaurante: Sol e Mar", sistema.melhorPrato());
    }

    @Test
    public void testMelhorPratoComVotos() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("maria@email.com", "Tio João");
        sistema.visitarRestaurante("maria@email.com", "Sol e Mar");
        sistema.votarPratoPreferido("alex@email.com", "Tio João");
        sistema.votarPratoPreferido("maria@email.com", "Tio João");
        assertEquals("Melhor prato: Sinfonia Marítima Restaurante: Tio João", sistema.melhorPrato());
    }

    @Test
    public void testMelhorPratoSemVotosRetornaPrimeiro() {
        assertEquals("Melhor prato: Sinfonia Marítima Restaurante: Tio João", sistema.melhorPrato());
    }

    @Test
    public void testMelhorPratoSemRestaurantes() {
        Sistema_PRW vazio = new Sistema_PRW();
        assertEquals("", vazio.melhorPrato());
    }

    @Test
    public void testListarLivroVisitas() {
        sistema.visitarRestaurante("alex@email.com", "Tio João", "Ótimo");
        sistema.visitarRestaurante("maria@email.com", "Tio João");
        String[] livro = sistema.listarLivroVisitas("Tio João");
        assertEquals(2, livro.length);
        assertTrue(livro[0].contains("Alex"));
        assertTrue(livro[0].contains("Ótimo"));
        assertTrue(livro[1].contains("Maria Eduarda"));
    }

    @Test
    public void testListarLivroVisitasRecentes() {
        sistema.visitarRestaurante("alex@email.com", "Tio João", "Primeira");
        sistema.visitarRestaurante("maria@email.com", "Tio João", "Segunda");
        sistema.visitarRestaurante("alex@email.com", "Tio João", "Terceira");
        String[] livro = sistema.listarLivroVisitas("Tio João", 2);
        assertEquals(2, livro.length);
        assertTrue(livro[0].contains("Segunda"));
        assertTrue(livro[1].contains("Terceira"));
    }

    @Test
    public void testListarLivroVisitasRestauranteInexistente() {
        assertEquals(0, sistema.listarLivroVisitas("Naoexiste").length);
        assertEquals(0, sistema.listarLivroVisitas("Naoexiste", 3).length);
    }

    private void prepararCenarioRelatorios() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Sol e Mar");
        sistema.visitarRestaurante("maria@email.com", "Tio João");
    }

    @Test
    public void testRelatorioGeral() {
        prepararCenarioRelatorios();
        String r = sistema.gerarRelatorioGeral();
        assertTrue(r.contains("(1) Restaurantes participantes : 2"));
        assertTrue(r.contains("(2) Clientes participantes : 2"));
        assertTrue(r.contains("(5) Restaurante mais visitado: Tio João"));
        assertTrue(r.contains("(3) Número médio de visitantes dos restaurantes: " + String.format("%.2f", 1.5)));
        assertTrue(r.contains("(4) Número médio de visitas nos restaurantes: " + String.format("%.2f", 2.0)));
        assertTrue(r.contains("(6) Participação de Visitas (Tio João): " + String.format("%.2f", 75.0) + "%"));
        assertTrue(r.contains("(6) Participação de Visitas (Sol e Mar): " + String.format("%.2f", 25.0) + "%"));
        assertTrue(r.contains("(7) Participação de Visitantes (Tio João): " + String.format("%.2f", 200.0 / 3) + "%"));
        assertTrue(r.contains("(7) Participação de Visitantes (Sol e Mar): " + String.format("%.2f", 100.0 / 3) + "%"));
    }

    @Test
    public void testRelatorioGeralComEmpateNoMaisVisitado() {
        sistema.visitarRestaurante("alex@email.com", "Tio João");
        sistema.visitarRestaurante("alex@email.com", "Sol e Mar");
        String r = sistema.gerarRelatorioGeral();
        assertTrue(r.contains("(5) Restaurante mais visitado: Tio João, Sol e Mar"));
    }

    @Test
    public void testRelatorioGeralSemVisitas() {
        String r = sistema.gerarRelatorioGeral();
        assertTrue(r.contains("nenhum restaurante foi visitado"));
        assertTrue(r.contains("(6) Participação de Visitas (Tio João): " + String.format("%.2f", 0.0) + "%"));
    }

    @Test
    public void testRelatorioGeralSemRestaurantes() {
        Sistema_PRW vazio = new Sistema_PRW();
        String r = vazio.gerarRelatorioGeral();
        assertTrue(r.contains("(1) Restaurantes participantes : 0"));
        assertTrue(r.contains("(3) Número médio de visitantes dos restaurantes: " + String.format("%.2f", 0.0)));
    }

    @Test
    public void testRelatorioFinanceiro() {
        prepararCenarioRelatorios();
        String r = sistema.gerarRelatorioFinanceiro();
        assertTrue(r.contains("Receita total do evento: R$ " + String.format("%.2f", 359.6)));
        assertTrue(r.contains("Média de receita por restaurante: R$ " + String.format("%.2f", 179.8)));
        assertTrue(r.contains("Média de gastos por cliente: R$ " + String.format("%.2f", 179.8)));
    }

    @Test
    public void testRelatorioFinanceiroSemDados() {
        Sistema_PRW vazio = new Sistema_PRW();
        String r = vazio.gerarRelatorioFinanceiro();
        assertTrue(r.contains("Receita total do evento: R$ " + String.format("%.2f", 0.0)));
        assertTrue(r.contains("Média de receita por restaurante: R$ " + String.format("%.2f", 0.0)));
        assertTrue(r.contains("Média de gastos por cliente: R$ " + String.format("%.2f", 0.0)));
    }

    @Test
    public void testRelatorioEngajamento() {
        prepararCenarioRelatorios();
        String r = sistema.gerarRelatorioEngajamento();
        assertTrue(r.contains("Cliente: Alex | Restaurante: Tio João | Visitas: 2"));
        assertTrue(r.contains("Cliente: Alex | Restaurante: Sol e Mar | Visitas: 1"));
        assertTrue(r.contains("Cliente: Maria Eduarda | Restaurante: Tio João | Visitas: 1"));
    }

    @Test
    public void testRelatorioEngajamentoSemVisitas() {
        String r = sistema.gerarRelatorioEngajamento();
        assertFalse(r.contains("Cliente:"));
    }
}