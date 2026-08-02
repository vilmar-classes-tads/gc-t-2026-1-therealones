package test.java.com.the_real_ones.unit;

import com.the_real_ones.model.Servidor;
import com.the_real_ones.repository.ServidorRepository;
import com.the_real_ones.service.ServidorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServidorServiceTest {

    @Mock
    private ServidorRepository servidorRepository;

    @InjectMocks
    private ServidorService servidorService;

    private Servidor servidorValido;

    @BeforeEach
    public void setup() {
        servidorValido = new Servidor(
            "Matheus Soares",
            "111.111.111-11",
            "matheus@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Mth"
        );
    }

    @Test
    @DisplayName("CT-01: Tentativa de cadastro de servidor com dados válidos")
    public void testCadastroServidorSucesso() {
        // Arrange
        when(servidorRepository.existsByCpf("111.111.111-11")).thenReturn(false);
        when(servidorRepository.existsByEmail("matheus@gmail.com")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> servidorService.cadastrarServidor(servidorValido));
        verify(servidorRepository, times(1)).create(servidorValido);
    }

    @Test
    @DisplayName("CT-02: Tentativa de cadastro de servidor com CPF Errado")
    public void testCadastroServidorCpfInvalido() {
        // Arrange
        Servidor servidorCpfInvalido = new Servidor(
            "Igor Rodrigues",
            "111.111.111-12",
            "igor@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Igr"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorCpfInvalido);
        });

        // Assert
        assertTrue(ex.getMessage().contains("CPF inválido"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-03: Tentativa de cadastro de servidor com CPF vazio")
    public void testCadastroServidorCpfVazio() {
        // Arrange
        Servidor servidorCpfVazio = new Servidor(
            "Rafael Beltrão",
            "",
            "rafael@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Rfa"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorCpfVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("CPF é obrigatório"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-04: Tentativa de cadastro de servidor com Nome vazio")
    public void testCadastroServidorNomeVazio() {
        // Arrange
        Servidor servidorNomeVazio = new Servidor(
            "",
            "111.111.111-11",
            "lucas@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Vsn"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorNomeVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Nome é obrigatório"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-05: Tentativa de cadastro de servidor com CPF já existente")
    public void testCadastroServidorCpfExistente() {
        // Arrange
        Servidor servidorCpfDuplicado = new Servidor(
            "Bruno Costa",
            "222.222.222-22",
            "bruno@gmail.com",
            "Engenharia de Software",
            "Recife",
            "Mestrado",
            "1234@Bno"
        );
        when(servidorRepository.existsByCpf("222.222.222-22")).thenReturn(true);

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorCpfDuplicado);
        });

        // Assert
        assertTrue(ex.getMessage().contains("CPF já cadastrado"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-06: Tentativa de cadastro de servidor com E-mail já existente")
    public void testCadastroServidorEmailExistente() {
        // Arrange
        Servidor servidorEmailDuplicado = new Servidor(
            "Gabriel Santos",
            "333.333.333-33",
            "gabriel@gmail.com",
            "Sistemas de Informação",
            "Recife",
            "Especialização",
            "1234@Gbl"
        );
        when(servidorRepository.existsByCpf("333.333.333-33")).thenReturn(false);
        when(servidorRepository.existsByEmail("gabriel@gmail.com")).thenReturn(true);

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorEmailDuplicado);
        });

        // Assert
        assertTrue(ex.getMessage().contains("E-mail já cadastrado"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-07: Tentativa de cadastro de servidor com o e-mail vazio")
    public void testCadastroServidorEmailVazio() {
        // Arrange
        Servidor servidorEmailVazio = new Servidor(
            "Rafael Beltrão",
            "111.111.111-11",
            "",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Rfa"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorEmailVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("e-mail é obrigatório"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-08: Tentativa de cadastro de servidor com a Senha vazia")
    public void testCadastroServidorSenhaVazia() {
        // Arrange
        Servidor servidorSenhaVazia = new Servidor(
            "Rafael Beltrão",
            "111.111.111-11",
            "rafael@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            ""
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorSenhaVazia);
        });

        // Assert
        assertTrue(ex.getMessage().contains("senha é obrigatória"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-09: Tentativa de cadastro de servidor com campus vazio")
    public void testCadastroServidorCampusVazio() {
        // Arrange
        Servidor servidorCampusVazio = new Servidor(
            "Rafael Beltrão",
            "111.111.111-11",
            "rafael@gmail.com",
            "Ciência da Computação",
            "",
            "Doutorado",
            "1234@Rfa"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorCampusVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("campus é obrigatório"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-10: Tentativa de cadastro de servidor com a Área de Formação vazia")
    public void testCadastroServidorAreaFormacaoVazia() {
        // Arrange
        Servidor servidorAreaVazia = new Servidor(
            "Rafael Beltrão",
            "111.111.111-11",
            "rafael@gmail.com",
            "",
            "Recife",
            "Doutorado",
            "1234@Rfa"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorAreaVazia);
        });

        // Assert
        assertTrue(ex.getMessage().contains("área de Formação é obrigatória"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-11: Tentativa de cadastro de servidor com a Titulação vazia")
    public void testCadastroServidorTitulacaoVazia() {
        // Arrange
        Servidor servidorTitulacaoVazia = new Servidor(
            "Rafael Beltrão",
            "111.111.111-11",
            "rafael@gmail.com",
            "Ciência da Computação",
            "Recife",
            "",
            "1234@Rfa"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorTitulacaoVazia);
        });

        // Assert
        assertTrue(ex.getMessage().contains("titulação é obrigatória"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-12: Tentativa de cadastro de servidor com senha menor que 6 caracteres")
    public void testCadastroServidorSenhaCurta() {
        // Arrange
        Servidor servidorSenhaCurta = new Servidor(
            "Felipe Melo",
            "444.444.444-44",
            "felipe@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "123@F"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            servidorService.cadastrarServidor(servidorSenhaCurta);
        });

        // Assert
        assertTrue(ex.getMessage().contains("mínimo 6 caracteres"));
        verify(servidorRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-13: Cadastro de servidor com sucesso preenchendo campos opcionais")
    public void testCadastroServidorCamposOpcionaisSucesso() {
        // Arrange
        Servidor servidorCompleto = new Servidor(
            "Thiago Oliveira",
            "555.555.555-55",
            "thiago@gmail.com",
            "Ciência da Computação",
            "Recife",
            "Doutorado",
            "1234@Tgo"
        );
        servidorCompleto.setNomeSocial("Thiago Lins");
        servidorCompleto.setSexo(Servidor.Sexo.HOMEM_CIS);
        servidorCompleto.setURLLattes("http://lattes.cnpq.br/555555555555");
        servidorCompleto.setTelefone("(81) 99999-9999");

        when(servidorRepository.existsByCpf("555.555.555-55")).thenReturn(false);
        when(servidorRepository.existsByEmail("thiago@gmail.com")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> servidorService.cadastrarServidor(servidorCompleto));
        verify(servidorRepository, times(1)).create(servidorCompleto);
    }
}
