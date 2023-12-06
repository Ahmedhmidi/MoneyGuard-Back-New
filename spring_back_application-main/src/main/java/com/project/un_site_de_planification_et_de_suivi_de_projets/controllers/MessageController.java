package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Message;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.User;
import com.project.un_site_de_planification_et_de_suivi_de_projets.payload.response.MessageDTO;
import com.project.un_site_de_planification_et_de_suivi_de_projets.repos.MessageRepository;
import com.project.un_site_de_planification_et_de_suivi_de_projets.repos.UserRepository;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.MessageService;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    final
    MessageService messageService ;

    @Autowired
    UserRepository userService;
    @Autowired
    MessageRepository messageRepository;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Message addNewMessage(@RequestBody MessageDTO messageDTO) {
        User sender = userService.findById(messageDTO.getSender_id()).orElse(null);
        User recipient = userService.findById(messageDTO.getRecipient_id()).orElse(null);
        Message message = new Message(messageDTO.getObject(), messageDTO.getDate(), sender, recipient);
        return messageService.addMessage(message);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Message> getAllMessages(){
        return messageService.findAllMessages();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Message getMessageById(@PathVariable("id") long id){
        return messageService.findMesageById(id);
    }
    @GetMapping("/getmessage/{username}")
    public List<Message> getMessageById(@PathVariable("username") String username) {
        return userService.findByUsername(username)
                .map(user -> messageRepository.findAll().stream()
                        .filter(message -> message.getSender().equals(user) || message.getRecipient().equals(user))
                        .peek(message -> {
                            // Mettre à jour les expéditeurs et destinataires des messages avec les utilisateurs complets
                            message.setSender(userService.findById(message.getSender().getId()).orElse(null));
                            message.setRecipient(userService.findById(message.getRecipient().getId()).orElse(null));
                        })
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }
    @GetMapping("/username/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username).orElse(null);
    }

    @GetMapping("/username_sender/{username}")
    @ResponseBody
    public List<MessageDTO> getMessagesByUsernameSender(@PathVariable String username){
        User user =  userService.findByUsername(username).orElse(null);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messageService.getMessagesBySenderId(user.getId())){
            messageDTOS.add(new MessageDTO(message.getMsg_id(), message.getObject(), message.getDate(), message.getRecipient().getId(), message.getSender().getId(), message.getSender().getUsername(), message.getRecipient().getUsername()));
        }
        return messageDTOS;
    }

    @GetMapping("/username_recipient/{username}")
    @ResponseBody
    public List<MessageDTO> getMessagesByUsernameProvider(@PathVariable String username){
        User user =  userService.findByUsername(username).orElse(null);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messageService.getMessagesByRecipientId(user.getId())){
            messageDTOS.add(new MessageDTO(message.getMsg_id(), message.getObject(), message.getDate(), message.getRecipient().getId(), message.getSender().getId(), message.getSender().getUsername(), message.getRecipient().getUsername()));
        }
        return messageDTOS;
    }



    @PutMapping("/update")
    @ResponseBody
    public void updateMessage(@RequestBody Message message){messageService.updateMessage(message); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteMessage(@PathVariable("id") long id){messageService.deleteMessage(id); }


    @GetMapping("/combined")
    @ResponseBody
    public List<MessageDTO> getCombinedMessages(
            @RequestParam("senderId") Long senderId,
            @RequestParam("recipientId") Long recipientId
    ) {
        List<MessageDTO> messageDTOS = new ArrayList<>();

        // Fetch messages where the current user is the sender or recipient
        List<Message> senderMessages = messageService.getMessagesBySenderId(senderId);
        List<Message> recipientMessages = messageService.getMessagesByRecipientId(senderId);

        for (Message message : senderMessages) {
            messageDTOS.add(new MessageDTO(
                    message.getMsg_id(),
                    message.getObject(),
                    message.getDate(),
                    message.getRecipient().getId(),
                    message.getSender().getId(),
                    message.getSender().getUsername(),
                    message.getRecipient().getUsername()
            ));
        }

        for (Message message : recipientMessages) {
            messageDTOS.add(new MessageDTO(
                    message.getMsg_id(),
                    message.getObject(),
                    message.getDate(),
                    message.getRecipient().getId(),
                    message.getSender().getId(),
                    message.getSender().getUsername(),
                    message.getRecipient().getUsername()
            ));
        }

        // Remove messages that don't match the specified sender and recipient
        messageDTOS.removeIf(message ->
                !(message.getRecipient_id()==(recipientId) && message.getSender_id()==(senderId)) &&
                        !(message.getRecipient_id()==(senderId) && message.getSender_id()==(recipientId)));

        System.out.println("hello from debugging");
        return messageDTOS;
    }




}

