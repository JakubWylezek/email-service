package com.taskservice.emailservice.service;

import com.taskservice.emailservice.exception.custom.DuplicateMailException;
import com.taskservice.emailservice.exception.custom.EmailNotFoundException;
import com.taskservice.emailservice.model.Email;
import com.taskservice.emailservice.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class EmailService {

    private EmailRepository emailRepository;
    private JavaMailSender emailSender;


    public Email updateEmailIfExist(Email email) {
        if(email.getId() == null || emailRepository.findById(email.getId()).isEmpty()) {
            throw new EmailNotFoundException(email);
        } else if (checkIfEmailsExist(Set.of(email.getName()))) {
            throw new DuplicateMailException(email.getName());
        }
        return emailRepository.save(email);
    }

    public void addNewEmailIfNotExist(Email email) {
        if(!checkIfEmailsExist(Set.of(email.getName()))) {
            emailRepository.save(email);
        }
    }

    public boolean checkIfEmailsExist(Set<String> emailSet) {
        return emailSet.size() == emailRepository.getByNameIn(emailSet).size();
    }

    public List<Email> getAllEmails() {
        return (List<Email>) emailRepository.findAll();
    }

    public void deleteEmail(Email email) {
        emailRepository.delete(email);
    }

    public void sendSimpleMessage(List<String> to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to.toArray(String[]::new));
        message.setSubject(subject);
        message.setText(text);
        message.setText(text);
        emailSender.send(message);
    }
}
