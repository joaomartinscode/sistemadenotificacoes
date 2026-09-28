package com.example.sistemadenotificacoes.controllers;

import com.example.sistemadenotificacoes.domain.ChannelType;
import com.example.sistemadenotificacoes.domain.Notification;
import com.example.sistemadenotificacoes.services.NotificationService;
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
    public String list(Model model){
        model.addAttribute("notifications", notificationService.findAll());
        return "notifications/list";
    }

    @GetMapping("/new")
    public String showForm(){
        return "notifications/form";
    }

    @PostMapping
    public String create(@RequestParam("to") String to,
                         @RequestParam(value = "cc", required = false) String cc,
                         @RequestParam(value = "bcc", required = false) String bcc,
                         @RequestParam("subject") String subject,
                         @RequestParam("content") String content)
    {
        Notification notification = notificationService.processNotification(to, cc, bcc, subject, content, ChannelType.EMAIL);
        return "redirect:/notifications/" + notification.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
        Notification notification = notificationService.findById(id);

        if(notification == null){
            return "redirect:/notifications";
        }

        model.addAttribute("notification", notification);
        return "notifications/detail";
    }
}
