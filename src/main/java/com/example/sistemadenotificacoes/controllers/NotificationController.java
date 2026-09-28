package com.example.sistemadenotificacoes.controllers;

import com.example.sistemadenotificacoes.domain.ChannelType;
import com.example.sistemadenotificacoes.domain.Notification;
import com.example.sistemadenotificacoes.domain.Recipient;
import com.example.sistemadenotificacoes.domain.RecipientType;
import com.example.sistemadenotificacoes.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

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

    @PostMapping
    public String create(@RequestParam("to") String to,
                         @RequestParam(value = "cc", required = false) String cc,
                         @RequestParam(value = "bcc", required = false) String bcc,
                         @RequestParam("subject") String subject,
                         @RequestParam("content") String content,
                         @RequestParam(value = "channelType", required = false, defaultValue = "EMAIL") ChannelType channelType) {
        List<Recipient> recipients = buildRecipients(to, cc, bcc);
        Notification notification = notificationService.processNotification(recipients, subject, content, channelType);
        return "redirect:/notifications/" + notification.getId();
    }

    private List<Recipient> buildRecipients(String to, String cc, String bcc) {
        List<Recipient> recipients = new ArrayList<>();
        addRecipients(recipients, to, RecipientType.TO);
        addRecipients(recipients, cc, RecipientType.CC);
        addRecipients(recipients, bcc, RecipientType.BCC);
        return recipients;
    }

    private void addRecipients(List<Recipient> recipients, String destinations, RecipientType type) {
        if (destinations == null || destinations.trim().isEmpty()) {
            return;
        }

        String[] destinationList = destinations.split(",");
        for (String destination : destinationList) {
            String cleanDestination = destination.trim();
            if (!cleanDestination.isEmpty()) {
                recipients.add(new Recipient(cleanDestination, type));
            }
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Notification notification = notificationService.findById(id);

        if (notification == null) {
            return "redirect:/notifications";
        }

        model.addAttribute("notification", notification);
        return "notifications/detail";
    }
}
