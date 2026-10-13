package pt.uportalegre.estgd.ei.es.sistemaNotificacoes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.EmailNotificationRequestDTO;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.NotificationDTO;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.RecipientDTO;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.services.NotificationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SistemaDeNotificacoesApplicationTests {

    static {
        SistemaDeNotificacoesApplication.loadEnv();
    }

    @MockitoBean
    private JavaMailSender mailSender;

    @Autowired
    private NotificationService notificationService;

    @Test
    void contextLoads() {
        System.out.println("[TESTE 1] Contexto carregado com sucesso!");
    }

    @Test
    void testProcessarNotificacaoComPrintsEMock() {
        System.out.println("\n=================================================");
        System.out.println("[TESTE 2] Criacao e Persistencia com Mockito");

        List<RecipientDTO> destinatarios = new ArrayList<>();
        destinatarios.add(new RecipientDTO("25856@uportalegre.pt", RecipientType.TO));
        destinatarios.add(new RecipientDTO("25721@uportalegre.pt", RecipientType.CC));

        NotificationDTO request = new NotificationDTO(
                "Teste Automatizado com Mockito",
                "Mensagem de teste sem envio real pela internet.",
                ChannelType.EMAIL,
                destinatarios
        );

        NotificationDTO resultado = notificationService.processNotification(request);

        verify(mailSender).send(any(SimpleMailMessage.class));

        System.out.println("ID gerado na BD: " + resultado.id());
        System.out.println("Assunto (Payload JSON): " + resultado.subject());
        System.out.println("Canal: " + resultado.channelType());
        System.out.println("Estado final: " + resultado.status());
        System.out.println("Data de Criacao: " + resultado.createdAt());
        System.out.println("Destinatarios associados:");
        for (RecipientDTO r : resultado.recipients()) {
            System.out.println("  - Tipo: " + r.type() + " | Endereco: " + r.destination());
        }
        System.out.println("O envio foi validado com sucesso sem disparar emails reais.");
        System.out.println("=================================================");

        assertNotNull(resultado.id(), "A notificacao deve ter um ID gerado na BD");
    }

    @Test
    void testListarNotificacoesComPrints() {
        System.out.println("\n=================================================");
        System.out.println("[TESTE 3] Listagem de Notificacoes na BD");

        List<NotificationDTO> lista = notificationService.findAll();

        System.out.println("Total de notificacoes encontradas: " + lista.size());
        for (NotificationDTO n : lista) {
            System.out.println("  #ID: " + n.id() + " | Assunto: " + n.subject() + " | Estado: " + n.status() + " | Canal: " + n.channelType());
        }
        System.out.println("=================================================");

        assertFalse(lista.isEmpty(), "A base de dados deve ter notificacoes registadas");
    }

    @Test
    void testSeparacaoDestinatariosPorVirgula() {
        System.out.println("\n=================================================");
        System.out.println("[TESTE 4] Teste da separacao de emails por virgula");

        EmailNotificationRequestDTO form = new EmailNotificationRequestDTO(
                ChannelType.EMAIL,
                "25856@uportalegre.pt, outro@uportalegre.pt",
                "copia1@uportalegre.pt, copia2@uportalegre.pt",
                "",
                "Assunto Unitario",
                "Texto de teste"
        );

        NotificationDTO dto = form.toNotificationDTO();

        System.out.println("Total de destinatarios extraidos pelo DTO: " + dto.recipients().size());
        for (RecipientDTO r : dto.recipients()) {
            System.out.println("  -> " + r.type() + ": " + r.destination());
        }
        System.out.println("=================================================");

        assertEquals(4, dto.recipients().size(), "Devem ser extraidos 4 destinatarios a partir do texto");
    }
}
