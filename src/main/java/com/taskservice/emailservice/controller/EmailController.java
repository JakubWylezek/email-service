package com.taskservice.emailservice.controller;

import com.taskservice.emailservice.api.model.Task;
import com.taskservice.emailservice.exception.custom.EmailNotFoundException;
import com.taskservice.emailservice.model.Email;
import com.taskservice.emailservice.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @GetMapping(value = "/emails/check-if-exist")
    public ResponseEntity<Boolean> checkIfEmailsExist(@RequestBody Set<String> emailsSet) {
            return new ResponseEntity<>(emailService.checkIfEmailsExist(emailsSet), HttpStatus.OK);
    }

    @GetMapping(value = "/emails")
    public ResponseEntity<List<Email>> getAllEmails() {
        return new ResponseEntity<>(emailService.getAllEmails(), HttpStatus.OK);
    }

    @PostMapping(value = "/emails")
    public ResponseEntity<Email> addNewEmail(@RequestBody Email email) {
        emailService.addNewEmailIfNotExist(email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/emails/send")
    public ResponseEntity<String> sendMail(@RequestBody @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new EmailNotFoundException("Emails not found");
        }

        emailService.sendSimpleMessage(
                task.getEmailList(),
                task.getName(),
                task.getDescription()
        );

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/emails")
    public ResponseEntity<Email> updateEmailIfExist(@RequestBody Email email) {
        return new ResponseEntity<>(emailService.updateEmailIfExist(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/emails")
    public ResponseEntity deleteEmail(@RequestBody Email email) {
        emailService.deleteEmail(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
