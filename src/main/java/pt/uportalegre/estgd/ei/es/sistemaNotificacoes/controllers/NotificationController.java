package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.controllers;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.EmailNotificationForm;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.NotificationDTO;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("notifications", notificationService.findAll());
        return "notifications/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("channels", ChannelType.values());
        return "notifications/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        NotificationDTO notification = notificationService.findById(id);

        if (notification == null) {
            return "redirect:/notifications";
        }

        model.addAttribute("notification", notification);
        return "notifications/detail";
    }

    @PostMapping("/emailSend")
    public String create(@ModelAttribute EmailNotificationForm form) {
        NotificationDTO dto = form.toDTO();
        NotificationDTO created = notificationService.processNotification(dto);

        return "redirect:/notifications/" + created.getId();
    }
}
