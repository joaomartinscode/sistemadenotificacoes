package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.controllers;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.*;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("notifications", notificationService.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("channels", ChannelType.values());
        return "form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            NotificationDTO notification = notificationService.findById(id);
            model.addAttribute("notification", notification);
            return "detail";
        } catch (NoSuchElementException e) {
            return "redirect:/notifications";
        }
    }

    @PostMapping("/emailSend")
    public String create(@ModelAttribute EmailNotificationRequestDTO form) {
        NotificationDTO dto = form.toNotificationDTO();
        NotificationDTO created = notificationService.processNotification(dto);

        return "redirect:/notifications/" + created.id();
    }
}
